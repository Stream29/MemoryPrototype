package io.github.stream29.memory

import io.github.stream29.memory.openai.generate

suspend fun main() {
    generate(listOf(TextChatMessage("user", "What is the meaning of life?")))
}