package io.github.platan.tests_execution_chart.config

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import javax.inject.Inject

abstract class Mark @Inject constructor(objectFactory: ObjectFactory) {

    @get:Input
    val maxEndTimeOfPreviousRuns: Property<Boolean> = objectFactory.property(Boolean::class.java).convention(false)
}
