package com.github.platan.tests_execution_chart

import org.gradle.api.Action
import org.gradle.api.tasks.Nested
import com.github.platan.tests_execution_chart.config.Formats


abstract class CreateTestsExecutionReportExtension {

    @Nested
    abstract fun getFormats(): Formats

    open fun formats(action: Action<in Formats>) {
        action.execute(getFormats())
    }
}
