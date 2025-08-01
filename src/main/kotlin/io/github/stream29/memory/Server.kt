package io.github.stream29.memory

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory
import org.slf4j.event.Level

val session: MemorySession = MemorySessionImpl()

fun Application.configureServer() {
    install(ContentNegotiation) {
        json(globalJson)
    }
    install(CallLogging) {
        level = Level.INFO
        logger = LoggerFactory.getLogger("Server")
    }
    routing {
        put("/update") {
            session.update(call.receive())
            call.respond(HttpStatusCode.OK)
        }
        get("/retrieve") {
            call.respond(session.retrieve())
        }
        post("/generate") {
            val input = call.receive<GenerateRequest>()
            call.respond(generate(input.chatMessages, input.thinking))
        }
    }
}