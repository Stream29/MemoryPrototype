package ai.dify.stream

import com.aallam.openai.api.chat.ChatCompletionRequestBuilder
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.LoggingConfig
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIHost
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

val openAiClient = OpenAI(
    token = System.getenv("ALIBABA_QWEN_API_KEY"),
    logging = LoggingConfig(LogLevel.None),
    host = OpenAIHost("https://dashscope.aliyuncs.com/compatible-mode/v1/v1")
)

suspend fun chat(buildAction: ChatCompletionRequestBuilder.() -> Unit): String =
    openAiClient.chatCompletions(
        ChatCompletionRequestBuilder().apply {
            model = ModelId("qwen-turbo")
            buildAction()
        }.build()
    ).map { it.choices.first().delta?.content ?: "" }.toList().joinToString("")