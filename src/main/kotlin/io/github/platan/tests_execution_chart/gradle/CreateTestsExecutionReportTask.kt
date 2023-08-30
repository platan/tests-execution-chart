package io.github.platan.tests_execution_chart.gradle

import io.github.platan.tests_execution_chart.gradle.config.Formats
import io.github.platan.tests_execution_chart.gradle.config.Marks
import io.github.platan.tests_execution_chart.report.TestExecutionScheduleReport
import io.github.platan.tests_execution_chart.reporters.Logger
import io.github.platan.tests_execution_chart.reporters.html.HtmlGanttDiagramReporter
import io.github.platan.tests_execution_chart.reporters.json.JsonReporter
import io.github.platan.tests_execution_chart.reporters.mermaid.MermaidTestsReporter
import org.gradle.api.DefaultTask
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.TaskAction
import java.time.ZoneId
import javax.inject.Inject

private const val NO_REPORTS_MESSAGE =
    "Task was run but it hasn't created any reports. Possible reasons:\n" +
        "- 'test' task was not run together with 'createTestsExecutionReport' task\n" +
        "- there were no tests in module\n" +
        "- plugin 'tests-execution-chart' was not added to module with tests\n" +
        "- tests weren't run (were up-to-date) - force running tests by '--rerun-tasks' Gradle option"

abstract class CreateTestsExecutionReportTask @Inject constructor(objectFactory: ObjectFactory) : DefaultTask() {

    private val customLogger = object : Logger {
        override fun lifecycle(message: String) {
            logger.lifecycle(message)
        }
    }

    @Internal
    abstract fun getRegisterService(): Property<TestExecutionResultsRegisterService>

    @Nested
    abstract fun getFormats(): Formats

    @Nested
    abstract fun getMarks(): Marks

    @get:Input
    val shiftTimestampsToStartOfDay: Property<Boolean> = objectFactory.property(Boolean::class.java).convention(false)

    @TaskAction
    fun printReport() {
        val resultsForAllModules = getRegisterService().get().getResults(this.identityPath.parent)
        if (resultsForAllModules.isEmpty()) {
            logger.lifecycle(NO_REPORTS_MESSAGE)
        } else {
            resultsForAllModules.forEach { (task, results) ->
                val adjustedResults = adjustResults(results)
                logger.lifecycle("Tests execution schedule report for task '${task.name}'")
                if (getFormats().getMermaid().enabled.get()) {
                    MermaidTestsReporter(getFormats().getMermaid(), customLogger).report(
                        adjustedResults,
                        task.project.buildDir,
                        task.name
                    )
                }
                if (getFormats().getJson().enabled.get()) {
                    JsonReporter(getFormats().getJson(), customLogger).report(
                        adjustedResults,
                        task.project.buildDir,
                        task.name
                    )
                }
                if (getFormats().getHtml().enabled.get()) {
                    HtmlGanttDiagramReporter(getFormats().getHtml().toHtmlConfig(), customLogger).report(
                        adjustedResults,
                        task.project.buildDir,
                        task.name
                    )
                }
            }
        }
    }

    private fun adjustResults(results: TestExecutionScheduleReport): TestExecutionScheduleReport {
        var adjusted = results
        if (shiftTimestampsToStartOfDay.get()) {
            adjusted = results.timestampsShiftedToStartOfDay(ZoneId.systemDefault())
        }
        if (getMarks().getTotalTimeOfAllTests().enabled.get()) {
            adjusted = adjusted.addTotalTimeOfAllTestsMark(getMarks().getTotalTimeOfAllTests().name.get())
        }
        return adjusted
    }
}
