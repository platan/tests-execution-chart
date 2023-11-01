package io.github.platan.tests_execution_chart.reporters.mermaid

import io.github.platan.tests_execution_chart.report.ReportConfig
import io.github.platan.tests_execution_chart.report.data.TestExecutionScheduleReport
import io.github.platan.tests_execution_chart.reporters.GanttDiagramReporter
import io.github.platan.tests_execution_chart.reporters.SingleFileReportWriter
import java.io.File

class MermaidTestsReporter :
    GanttDiagramReporter<MermaidConfig>() {


    override fun report(report: TestExecutionScheduleReport, baseDir: File, taskName: String) {
        val mermaidReport = TestExecutionMermaidDiagramFormatter().format(report)
        val reportFile = SingleFileReportWriter().save(mermaidReport, taskName, baseDir, config.outputLocation, "txt")
        logger.lifecycle("Tests execution schedule report saved to ${reportFile.absolutePath} file.")
    }

    override fun getConfigType(): Class<out ReportConfig.Format> = MermaidConfig::class.java
}
