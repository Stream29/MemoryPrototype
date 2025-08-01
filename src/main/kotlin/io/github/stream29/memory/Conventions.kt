package io.github.stream29.memory

interface MemorySession {
    suspend fun update(chatMessages: List<TextChatMessage>)
    suspend fun retrieve(): MemoryRetrieveResult
}

