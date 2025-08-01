package ai.dify.stream

import io.github.stream29.jsonschemagenerator.SchemaGenerator
import io.github.stream29.jsonschemagenerator.schemaOf
import kotlinx.serialization.json.Json

val globalJson: Json = Json {
    prettyPrint = true
    isLenient = true
    encodeDefaults = true
    explicitNulls = false
}

public inline fun <reified T> schemaOf(): String =
    globalJson.encodeToString(SchemaGenerator.default.schemaOf<T>())