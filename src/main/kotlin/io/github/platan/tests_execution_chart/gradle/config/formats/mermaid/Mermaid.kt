package io.github.platan.tests_execution_chart.gradle.config.formats.mermaid

import io.github.platan.tests_execution_chart.gradle.config.formats.Format
import io.github.platan.tests_execution_chart.reporters.mermaid.MermaidConfig
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

abstract class Mermaid @Inject constructor(objectFactory: ObjectFactory) : Format(objectFactory, "mermaid") {
    fun toMermaidConfig(): MermaidConfig = MermaidConfig(enabled.get(), outputLocation.get())
}
