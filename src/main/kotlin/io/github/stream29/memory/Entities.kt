package io.github.stream29.memory

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TextChatMessage(
    val role: String,
    val text: String
)

@Serializable
data class MemoryRetrieveResult(
    @SerialName("short_term_memory")
    val shortTermMemory: String,
    @SerialName("visible_long_term_memory")
    val visibleLongTermMemory: String,
    val instruction: String
)