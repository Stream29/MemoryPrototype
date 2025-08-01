package io.github.stream29.memory

import io.github.stream29.jsonschemagenerator.SchemaGenerator
import io.github.stream29.jsonschemagenerator.schemaOf
import kotlinx.serialization.json.Json

inline fun <reified T> schemaOf(): String =
    globalJson.encodeToString(SchemaGenerator.default.schemaOf<T>())

fun String.normalized(): String {
    check(contains("{") && contains("}")) { "invalid json: $this" }
    return substring(indexOfFirst { it == '{' }, indexOfLast { it == '}' } + 1)
}