package io.github.platan.tests_execution_chart.reporters

import io.github.platan.tests_execution_chart.report.ReportConfig
import io.github.platan.tests_execution_chart.report.data.TestExecutionScheduleReport
import java.io.File

abstract class GanttDiagramReporter<T : ReportConfig.Format> {

    protected lateinit var config: T
    var logger: Logger = NoopLogger

    fun setConfiguration(configuration: ReportConfig.Format) {
        @Suppress("UNCHECKED_CAST")
        this.config = configuration as T
    }

    abstract fun report(report: TestExecutionScheduleReport, baseDir: File, taskName: String)

    abstract fun getConfigType(): Class<out ReportConfig.Format>
}
