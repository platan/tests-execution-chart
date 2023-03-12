package io.github.platan.tests_execution_chart.config

import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Nested
import javax.inject.Inject

private const val DEFAULT_SRC = "https://cdn.jsdelivr.net/npm/mermaid@9.4.3/dist/mermaid.min.js"

abstract class Script @Inject constructor(objectFactory: ObjectFactory) {

    @get:Input
    val src: Property<String> = objectFactory.property(String::class.java).convention(DEFAULT_SRC)

    @get:Input
    val embed: Property<Boolean> = objectFactory.property(Boolean::class.java).convention(false)

    @Nested
    abstract fun getConfig(): ScriptConfig

    open fun config(action: Action<in ScriptConfig>) {
        action.execute(getConfig())
    }
}
