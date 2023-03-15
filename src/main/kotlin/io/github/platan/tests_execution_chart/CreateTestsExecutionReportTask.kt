package io.github.platan.tests_execution_chart

import io.github.platan.tests_execution_chart.config.Formats
import io.github.platan.tests_execution_chart.reporters.Logger
import io.github.platan.tests_execution_chart.reporters.html.HtmlGanttDiagramReporter
import io.github.platan.tests_execution_chart.reporters.json.JsonReporter
import io.github.platan.tests_execution_chart.reporters.mermaid.MermaidTestsReporter
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.TaskAction

private const val NO_REPORTS_MESSAGE =
    "Task was run but it hasn't created any reports. Possible reasons:\n" +
        "- 'test' task was not run together with 'createTestsExecutionReport' task\n" +
        "- there were no tests in module\n" +
        "- plugin 'tests-execution-chart' was not added to module with tests\n" +
        "- tests weren't run (were up-to-date) - force running tests by '--rerun-tasks' Gradle option"

abstract class CreateTestsExecutionReportTask : DefaultTask() {

    private val customLogger = object : Logger {
        override fun lifecycle(message: String) {
            logger.lifecycle(message)
        }
    }

    @Internal
    abstract fun getRegisterService(): Property<TestExecutionResultsRegisterService>

    @Nested
    abstract fun getFormats(): Formats

    @TaskAction
    fun printReport() {
        val resultsForAllModules = getRegisterService().get().getResults(this.identityPath.parent)
        if (resultsForAllModules.isEmpty()) {
            logger.lifecycle(NO_REPORTS_MESSAGE)
        } else {
            resultsForAllModules.forEach { (task, results) ->
                logger.lifecycle("Tests execution schedule report for task '${task.name}'")
                if (getFormats().getMermaid().enabled.get()) {
                    MermaidTestsReporter(getFormats().getMermaid(), customLogger).report(
                        results,
                        task.project.buildDir,
                        task.name
                    )
                }
                if (getFormats().getJson().enabled.get()) {
                    JsonReporter(getFormats().getJson(), customLogger).report(results, task.project.buildDir, task.name)
                }
                if (getFormats().getHtml().enabled.get()) {
                    HtmlGanttDiagramReporter(getFormats().getHtml().toHtmlConfig(), customLogger).report(
                        results,
                        task.project.buildDir,
                        task.name
                    )
                }
            }
        }
    }
}
