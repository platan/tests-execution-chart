package io.github.platan.tests_execution_chart.config

import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.Nested
import javax.inject.Inject

abstract class HtmlMermaid @Inject constructor(objectFactory: ObjectFactory) : Format(objectFactory, "mermaid") {
    @Nested
    abstract fun getConfig(): HtmlMermaidConfig

    open fun config(action: Action<in HtmlMermaidConfig>) {
        action.execute(getConfig())
    }
}
