package io.github.stream29.memory

import io.ktor.server.cio.*
import io.ktor.server.engine.*

suspend fun main() {
    embeddedServer(
        factory = CIO,
        port = 8000,
        host = "0.0.0.0",
        module = { configureServer() }
    ).startSuspend(wait = true)
}