package com.github.platan.tests_execution_chart.config

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import javax.inject.Inject

private const val DEFAULT_SRC = "https://cdn.jsdelivr.net/npm/mermaid/dist/mermaid.min.js"

abstract class Script @Inject constructor(objectFactory: ObjectFactory) {

    @get:Input
    val src: Property<String> = objectFactory.property(String::class.java).convention(DEFAULT_SRC)

    @get:Input
    val embed: Property<Boolean> = objectFactory.property(Boolean::class.java).convention(false)
}
