package io.github.stream29.memory

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.serialization.json.Json

val globalJson: Json = Json {
    prettyPrint = true
    isLenient = true
    encodeDefaults = true
    explicitNulls = false
    ignoreUnknownKeys = true
}
val apiKey = System.getenv("ALIBABA_QWEN_API_KEY")!!
val httpClient = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(globalJson)
    }
    expectSuccess = true
}
val consoleMutex = Mutex()
val loggingScope = CoroutineScope(CoroutineName("print log") + Dispatchers.IO)