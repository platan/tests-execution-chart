package io.github.platan.tests_execution_chart.gradle.config.formats.json

import io.github.platan.tests_execution_chart.gradle.config.formats.Format
import io.github.platan.tests_execution_chart.reporters.json.JsonConfig
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

abstract class Json @Inject constructor(objectFactory: ObjectFactory) : Format(objectFactory, "json") {
    fun toJsonConfig(): JsonConfig = JsonConfig(enabled.get(), outputLocation.get())
}
