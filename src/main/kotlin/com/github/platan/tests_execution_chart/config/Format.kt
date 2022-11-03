package com.github.platan.tests_execution_chart.config

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import javax.inject.Inject

abstract class Format @Inject constructor(objectFactory: ObjectFactory, name: String) {

    @get:Input
    val enabled: Property<Boolean> = objectFactory.property(Boolean::class.java).convention(true)

    @Internal
    val outputLocation: Property<String> =
        objectFactory.property(String::class.java).convention("reports/tests-execution/$name")
}
