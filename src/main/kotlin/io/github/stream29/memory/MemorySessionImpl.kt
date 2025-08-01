package io.github.stream29.memory

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.concurrent.Volatile

class MemorySessionImpl : MemorySession {
    @Volatile
    var state = MemoryRetrieveResult(
        shortTermMemory = "",
        visibleLongTermMemory = emptyList(),
        instruction = ""
    )

    @Volatile
    var visibleList: List<String> = emptyList()

    @Volatile
    var longTermMemories: List<LongTermMemory> = emptyList()
    val memoryMutex = Mutex()

    override suspend fun update(chatMessages: List<TextChatMessage>) {
        memoryMutex.withLock {
            val updateMemoryRequest = UpdateMemoryRequest(
                chatMessages = chatMessages,
                shortTermMemory = state.shortTermMemory,
                visibleLongTermMemories = state.visibleLongTermMemory,
                instruction = state.instruction
            )
            val updateMemoryResponse = generate<UpdateMemoryRequest, UpdateMemoryResponse>(updateMemoryRequest)
            val updateLongTermMemoryRequest = UpdateLongTermMemoryRequest(
                chatMessages = chatMessages,
                shortTermMemory = state.shortTermMemory,
                longTermMemories = longTermMemories,
                visibleList = visibleList,
                update = updateMemoryResponse.updateLongTermMemory
            )
            val updateLongTermMemoryResponse =
                generate<UpdateLongTermMemoryRequest, UpdateLongTermMemoryResponse>(updateLongTermMemoryRequest)
            longTermMemories = updateLongTermMemoryResponse.updatedMemories
            visibleList = updateLongTermMemoryResponse.visibleList
            state = MemoryRetrieveResult(
                shortTermMemory = updateMemoryResponse.shortTermMemory,
                visibleLongTermMemory = visibleList.map { name -> longTermMemories.first { it.name == name } },
                instruction = updateMemoryResponse.instruction
            )
        }
    }

    override suspend fun retrieve(): MemoryRetrieveResult {
        memoryMutex.withLock {
            return state
        }
    }
}