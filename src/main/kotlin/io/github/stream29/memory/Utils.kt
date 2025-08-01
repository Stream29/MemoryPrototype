package ai.dify.stream

import com.aallam.openai.api.chat.ChatMessagesBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.runBlocking

fun ChatMessagesBuilder.user(text: String) =
    user {
        content {
            text(text)
        }
    }

fun ChatMessagesBuilder.assistant(text: String) =
    assistant {
        content = text
    }

fun ChatMessagesBuilder.system(text: String) =
    system {
        content = text
    }

fun <T> Flow<T>.collectBlocking(collector: FlowCollector<T>) = runBlocking { collect(collector) }

fun String.normalized(): String {
    check(contains("{") && contains("}")) { "invalid json: $this" }
    return substring(indexOfFirst { it == '{' }, indexOfLast { it == '}' } + 1)
}