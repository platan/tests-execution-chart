package com.github.platan.tests_execution_chart

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.TaskAction
import com.github.platan.tests_execution_chart.config.Formats
import com.github.platan.tests_execution_chart.reporters.html.HtmlGanttDiagramReporter
import com.github.platan.tests_execution_chart.reporters.json.JsonReporter
import com.github.platan.tests_execution_chart.reporters.mermaid.MermaidTestsReporter


abstract class CreateTestsExecutionReportTask : DefaultTask() {

    @Internal
    abstract fun getRegisterService(): Property<TestExecutionResultsRegisterService>

    @Nested
    abstract fun getFormats(): Formats

    @TaskAction
    fun printReport() {
        getRegisterService().get().getResults(this.identityPath.parent).forEach { (task, results) ->
            logger.lifecycle("Tests execution schedule report for task '${task.name}'")
            if (getFormats().getMermaid().enabled.get()) {
                MermaidTestsReporter(getFormats().getMermaid(), logger).report(results, task)
            }
            if (getFormats().getJson().enabled.get()) {
                JsonReporter(getFormats().getJson(), logger).report(results, task)
            }
            if (getFormats().getHtml().enabled.get()) {
                HtmlGanttDiagramReporter(getFormats().getHtml(), logger).report(results, task)
            }
        }
    }

}
