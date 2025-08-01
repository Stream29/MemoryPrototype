package io.github.stream29.memory.openai

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class ChatCompletionRequest(
    public val model: String,
    public val messages: List<ChatMessage>,
    @SerialName("enable_thinking")
    public val enableThinking: Boolean = true,
    public val stream: Boolean = true,
)