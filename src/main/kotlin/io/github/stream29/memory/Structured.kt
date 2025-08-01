package io.github.stream29.memory

import io.github.stream29.jsonschemagenerator.SchemaGenerator
import io.github.stream29.jsonschemagenerator.schemaOf
import io.github.stream29.memory.openai.generate

inline fun <reified T> schemaOf(): String =
    globalJson.encodeToString(SchemaGenerator.default.schemaOf<T>())

fun String.normalized(): String {
    check(contains("{") && contains("}")) { "invalid json: $this" }
    return substring(indexOfFirst { it == '{' }, indexOfLast { it == '}' } + 1)
}

suspend inline fun <reified T, reified R> generate(input: T): R {
    val serializedInput = globalJson.encodeToString(input)
    val rawOutput = generate(
        listOf(
            TextChatMessage("system", systemPromptOf<T, R>()),
            TextChatMessage("user", serializedInput)
        )
    )
    try {
        return globalJson.decodeFromString(rawOutput.normalized())
    } catch (e: Exception) {
        throw RuntimeException("Failed to parse output: $rawOutput", e)
    }
}

inline fun <reified T, reified R> systemPromptOf() = """
Both your input and output should be in JSON format.

! Below is the schema for input content !
${schemaOf<T>()}
! Above is the schema for input content !

! Below is the schema for output content !
${schemaOf<R>()}
! Above is the schema for output content !

Your output must strictly follow the schema format, do not output any content outside of the JSON body.
"""