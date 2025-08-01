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
    @SerialName("visible_long_term_memories")
    val visibleLongTermMemories: List<LongTermMemory>,
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
data class LongTermMemory(
    @Description("The unique name of the memory block, used to identify it.")
    val name: String,
    @Description("Tells what the memory block is about.")
    val abstract: String,
    @Description("The content of the memory block.")
    @SerialName("memory_block")
    val memoryBlock: String
)

@Serializable
data class UpdateLongTermMemoryRequest(
    @Description("Latest chat history.")
    @SerialName("chat_messages")
    val chatMessages: List<TextChatMessage>,
    @Description("Short term memory that contains recent actions, plans and ongoing processes.")
    @SerialName("short_term_memory")
    val shortTermMemory: String,
    @Description("Long term memory that store information about the conversation on specific topics.")
    @SerialName("long_term_memories")
    val longTermMemories: List<LongTermMemory>,
    @Description("List of memory names that regarded relevant to current context")
    val visibleList: List<String>,
    @Description("New information that needs to be recorded into long term memory.")
    val update: String,
)

@Serializable
data class UpdateLongTermMemoryResponse(
    @Description("Updated value of `visible_list`")
    @SerialName("visible_list")
    val visibleList: List<String>,
    @Description("Updated value of `long_term_memories`")
    @SerialName("updated_memories")
    val updatedMemories: List<LongTermMemory>
)