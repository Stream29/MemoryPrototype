package io.github.stream29.memory.openai

import io.github.stream29.memory.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.withLock

suspend fun generate(history: List<TextChatMessage>, thinking: Boolean = true): String {
    val request = ChatCompletionRequest(
        model = "qwen-turbo-2025-07-15",
        messages = history.map {
            ChatMessage(
                it.role,
                TextContent(it.text)
            )
        },
        enableThinking = thinking,
    )
    val stream = generate(request)
    val logChannel = Channel<String?>(Channel.UNLIMITED)
    loggingScope.launch {
        consoleMutex.withLock {
            for (log in logChannel) {
                print(log)
                ensureActive()
            }
            println()
        }
    }
    return stream.transform {
        val choice = it.choices.firstOrNull() ?: return@transform
        val delta = choice.delta ?: return@transform
        val reasoningContent = delta.reasoningContent
        val text = delta.content
        if (reasoningContent != null) {
            logChannel.send(reasoningContent)
        }
        if (text != null) {
            logChannel.send(text)
            emit(text)
        }
    }.onCompletion { logChannel.close() }.toList().joinToString("")
}

suspend fun generate(request: ChatCompletionRequest): Flow<ChatCompletionChunk> {
    val statement = httpClient.preparePost("https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions") {
        bearerAuth(apiKey)
        contentType(ContentType.Application.Json)
        accept(ContentType.Text.EventStream)
        setBody(request)
    }
    val channel = runCatching { statement.body<ByteReadChannel>() }
        .getOrElse { return flow { throw it } }
    return flow {
        while (currentCoroutineContext().isActive && !channel.isClosedForRead) {
            val line = channel.readUTF8Line()
            val value: ChatCompletionChunk = when {
                line == null -> break
                line.startsWith(STREAM_END_TOKEN) -> break
                line.startsWith(STREAM_PREFIX) -> line.decodeChunkNoReflection()
                else -> continue
            }
            emit(value)
        }
    }.onCompletion { channel.cancel() }
}

private const val STREAM_PREFIX = "data:"
private const val STREAM_END_TOKEN = "$STREAM_PREFIX [DONE]"

private fun String.decodeChunkNoReflection(): ChatCompletionChunk {
    return globalJson.decodeFromString(
        ChatCompletionChunk.serializer(),
        removePrefix(STREAM_PREFIX)
    )
}