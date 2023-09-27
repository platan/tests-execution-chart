package io.github.platan.tests_execution_chart.gradle.config.formats

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import javax.inject.Inject

abstract class Suites @Inject constructor(objectFactory: ObjectFactory) {

    @get:Input
    val enabled: Property<Boolean> = objectFactory.property(Boolean::class.java).convention(false)
}
