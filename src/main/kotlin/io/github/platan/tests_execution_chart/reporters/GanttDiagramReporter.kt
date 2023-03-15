package io.github.platan.tests_execution_chart.reporters

import io.github.platan.tests_execution_chart.TestExecutionScheduleReport
import java.io.File

abstract class GanttDiagramReporter {
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
}
