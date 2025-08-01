package io.github.stream29.memory.openai

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline


@Serializable
public data class ChatMessage(
    public val role: String,
    public val content: Content? = null,
)

@Serializable(with = ContentSerializer::class)
public sealed interface Content

@JvmInline
@Serializable
public value class TextContent(public val content: String) : Content
