package com.github.platan.testsganttchart

import org.gradle.api.Action
import org.gradle.api.tasks.Nested
import com.github.platan.testsganttchart.config.Formats


abstract class CreateTestsExecutionReportExtension {

    @Nested
    abstract fun getFormats(): Formats

    open fun formats(action: Action<in Formats>) {
        action.execute(getFormats())
    }
}
