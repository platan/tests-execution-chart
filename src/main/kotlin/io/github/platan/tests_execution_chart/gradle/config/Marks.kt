package io.github.platan.tests_execution_chart.gradle.config

import io.github.platan.tests_execution_chart.report.ReportConfig
import org.gradle.api.Action
import org.gradle.api.tasks.Nested

abstract class Marks {

    @Nested
    abstract fun getTotalTimeOfAllTests(): TotalTimeOfAllTestsMark

    open fun totalTimeOfAllTests(action: Action<in Mark>) {
        action.execute(getTotalTimeOfAllTests())
    }

    fun toMarks(): ReportConfig.Marks =
        ReportConfig.Marks(getTotalTimeOfAllTests().toMark())
}
