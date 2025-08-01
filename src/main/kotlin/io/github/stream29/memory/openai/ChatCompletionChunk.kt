package io.github.stream29.memory.openai

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class ChatCompletionChunk(
    public val choices: List<ChatChunk>
)

@Serializable
public data class ChatChunk(
    public val delta: ChatDelta? = null
)

@Serializable
public data class ChatDelta(
    public val role: String? = null,
    public val content: String? = null,
    @SerialName("reasoning_content")
    public val reasoningContent: String? = null
)