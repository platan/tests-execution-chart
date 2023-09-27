package io.github.platan.tests_execution_chart.gradle.config

import io.github.platan.tests_execution_chart.gradle.config.formats.Suites
import org.gradle.api.Action
import org.gradle.api.tasks.Nested

abstract class Components {

    @Nested
    abstract fun getSuites(): Suites

    open fun suites(action: Action<in Suites>) {
        action.execute(getSuites())
    }
}
