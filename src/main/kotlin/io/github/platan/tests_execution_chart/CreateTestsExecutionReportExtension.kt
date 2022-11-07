package io.github.platan.tests_execution_chart

import io.github.platan.tests_execution_chart.config.Formats
import org.gradle.api.Action
import org.gradle.api.tasks.Nested

abstract class CreateTestsExecutionReportExtension {

    @Nested
    abstract fun getFormats(): Formats

    open fun formats(action: Action<in Formats>) {
        action.execute(getFormats())
    }
}
