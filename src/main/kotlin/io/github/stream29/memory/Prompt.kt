package ai.dify.stream

import io.github.stream29.jsonschemagenerator.Description
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Description("A message between the chat of the user and the LLM.")
data class TextChatMessage(
    val role: String,
    val text: String
)

@Serializable
@Description("A memory block that stores information about the conversation on specific topics.")
data class MemoryAbstract(
    @Description("The unique name of the memory block, used to identify it.")
    val name: String,
    @Description("Tells what the memory block is about.")
    val abstract: String
)

@Serializable
@Description("A memory block that stores information about the conversation on specific topics.")
data class Memory(
    @Description("The unique name of the memory block, used to identify it.")
    val name: String,
    @Description("Tells what the memory block is about.")
    val abstract: String,
    @Description("The content of the memory block.")
    @SerialName("memory_block")
    val memoryBlock: String
)

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
data class VisibleMemory(
    val shortTermMemory: String,
    val visibleBacklog: String,
    val visibleLongTermMemory: String,
    val instruction: String
)

@Serializable
data class UpdateBacklogRequest(
    val chatMessages: List<TextChatMessage>,
    val shortTermMemory: String,
    val update: String,
)

@Serializable
data class UpdateLongTermMemoryRequest(
    val chatMessages: List<TextChatMessage>,
    val shortTermMemory: String,
    val update: String,
)

val systemPrompt = """
Both your input and output should be in JSON format.

! Below is the schema for input content !
${schemaOf<UpdateMemoryRequest>()}
! Above is the schema for input content !

! Below is the schema for output content !
${schemaOf<UpdateMemoryResponse>()}
! Above is the schema for output content !

Your output must strictly follow the schema format, do not output any content outside of the JSON body.
"""