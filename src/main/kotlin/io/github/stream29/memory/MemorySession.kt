package io.github.stream29.memory

import io.github.stream29.memory.TextChatMessage

class MemorySessionImpl : MemorySession {
    override suspend fun update(chatMessages: List<TextChatMessage>) {

    }

    override suspend fun retrieve(): MemoryRetrieveResult {
        TODO()
    }
}