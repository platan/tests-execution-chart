package io.github.platan.tests_execution_chart.gradle.config

import io.github.platan.tests_execution_chart.reporters.config.MermaidConfig
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

abstract class Mermaid @Inject constructor(objectFactory: ObjectFactory) : Format(objectFactory, "mermaid") {
    fun toMermaidConfig(): MermaidConfig = MermaidConfig(MermaidConfig.Format(enabled.get(), outputLocation.get()))
}
