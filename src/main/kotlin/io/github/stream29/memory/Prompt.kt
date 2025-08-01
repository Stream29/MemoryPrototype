package io.github.stream29.memory

import io.github.stream29.jsonschemagenerator.Description
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Description("You need to update short term memory, long term memory and instruction based on `chat_messages`.")
data class UpdateMemoryRequest(
    @Description("Latest chat history.")
    @SerialName("chat_messages")
    val chatMessages: List<TextChatMessage>,
    @Description("Short term memory that contains recent actions, plans and ongoing processes.")
    @SerialName("short_term_memory")
    val shortTermMemory: String,
    @Description("Long term memory relevant to current context.")
    @SerialName("visible_long_term_memory")
    val visibleLongTermMemory: String,
    @Description("Immortal and important instruction from user.")
    val instruction: String,
)

@Serializable
data class UpdateMemoryResponse(
    @Description("Updated short term memory that contains latest information.")
    @SerialName("short_term_memory")
    val shortTermMemory: String,
    @Description("New information that needs to be recorded into long term memory.")
    @SerialName("update_long_term_memory")
    val updateLongTermMemory: String,
    @Description("Latest instruction from user. If not ordered to change, keep the original one.")
    @SerialName("update_instruction")
    val instruction: String,
)

@Serializable
data class UpdateLongTermMemoryRequest(
    val chatMessages: List<TextChatMessage>,
    val shortTermMemory: String,
    val update: String,
)