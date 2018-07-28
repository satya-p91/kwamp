package co.nz.arm.app

import co.nz.arm.wamp.Router
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.cio.websocket.CloseReason
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import io.ktor.response.respondText
import io.ktor.routing.*
import io.ktor.websocket.WebSockets
import io.ktor.websocket.webSocket
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch
import java.time.Duration

private val router = Router()

fun Application.main() {
    install(DefaultHeaders)
    install(CallLogging)
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(30)
    }

    routing {
        webSocket("/register") {
            log.info("Websocket connection established")
            val wampIncoming = Channel<String>()
            val wampOutgoing= Channel<String>()

            router.newConnection(wampIncoming, wampOutgoing, {message -> close(CloseReason(CloseReason.Codes.NORMAL, message))})

            launch {
                wampOutgoing.consumeEach { message ->
                    send(Frame.Text(message))
                }
                log.info("Websocket no longer forwarding messages")
            }

            incoming.consumeEach { frame ->
                if (frame is Frame.Text) {
                    wampIncoming.send(frame.readText())
                }
            }

            log.info("Websocket connection ended")
        }

        get("/") {
            call.respondText("Hello World")
        }
    }
}

data class SocketSession(val id: String)