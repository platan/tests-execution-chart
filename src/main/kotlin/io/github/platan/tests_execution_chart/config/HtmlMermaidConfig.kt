package io.github.platan.tests_execution_chart.config

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import javax.inject.Inject

private const val MAX_TEXT_SIZE = 100_000

abstract class HtmlMermaidConfig @Inject constructor(objectFactory: ObjectFactory) {

    @get:Input
    val maxTextSize: Property<Int> = objectFactory.property(Int::class.java).convention(MAX_TEXT_SIZE)
}
