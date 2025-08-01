package io.github.stream29.memory.openai

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

internal class ContentSerializer : JsonContentPolymorphicSerializer<Content>(Content::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<Content> {
        return when (element) {
            is JsonPrimitive -> TextContent.serializer()
            else -> throw SerializationException("Unsupported JSON element: $element")
        }
    }
}