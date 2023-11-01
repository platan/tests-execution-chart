package io.github.platan.tests_execution_chart.reporters

import java.io.File

class SingleFileReportWriter {

    fun save(
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

    private fun prepareReportsDir(baseDir: File, location: String): File {
        val reportsDir = File(baseDir, location)
        reportsDir.mkdirs()
        return reportsDir
    }


}
