package io.github.platan.tests_execution_chart.reporters

import io.github.platan.tests_execution_chart.report.ReportConfig
import io.github.platan.tests_execution_chart.report.data.TestExecutionScheduleReport
import io.github.platan.tests_execution_chart.reporters.html.NoopLogger
import java.io.File

abstract class GanttDiagramReporter<T : ReportConfig.Format> {

    protected lateinit var config: T
    var logger: Logger = NoopLogger

    fun setConfiguration(configuration: ReportConfig.Format) {
        @Suppress("UNCHECKED_CAST")
        this.config = configuration as T
    }

    abstract fun report(report: TestExecutionScheduleReport, baseDir: File, taskName: String)
    protected fun save(
        report: String,
        taskName: String,
        baseDir: File,
        location: String,
        extension: String
    ): File {
        val reportsDir = prepareReportsDir(baseDir, location)
        val reportFile = File(reportsDir, "$taskName.$extension")
        reportFile.createNewFile()
        reportFile.writeText(report)
        return reportFile
    }

    fun prepareReportsDir(baseDir: File, location: String): File {
        val reportsDir = File(baseDir, location)
        reportsDir.mkdirs()
        return reportsDir
    }

    abstract fun getConfigType(): Class<out ReportConfig.Format>
}
