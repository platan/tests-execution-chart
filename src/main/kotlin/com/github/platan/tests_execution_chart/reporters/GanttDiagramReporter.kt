package com.github.platan.tests_execution_chart.reporters

import com.github.platan.tests_execution_chart.TestExecutionScheduleReport
import org.gradle.api.tasks.testing.Test
import java.io.File

abstract class GanttDiagramReporter {
    abstract fun report(report: TestExecutionScheduleReport, task: Test)
    protected fun save(
        task: Test,
        report: String,
        location: String,
        extension: String
    ): File {
        val reportsDir = prepareReportsDir(task, location)
        val reportFile = File(reportsDir, "${task.name}.$extension")
        reportFile.createNewFile()
        reportFile.writeText(report)
        return reportFile
    }

    fun prepareReportsDir(task: Test, location: String): File {
        val reportsDir = File(task.project.buildDir, location)
        reportsDir.mkdirs()
        return reportsDir
    }
}
