package io.github.platan.tests_execution_chart.gradle

import io.github.platan.tests_execution_chart.report.TestExecutionScheduleReport
import io.github.platan.tests_execution_chart.report.TestExecutionScheduleReportBuilder
import org.gradle.api.services.BuildService
import org.gradle.api.services.BuildServiceParameters
import org.gradle.api.tasks.testing.Test
import org.gradle.util.Path

abstract class TestExecutionResultsRegisterService : BuildService<BuildServiceParameters.None> {

    private val register = RegisterClass()

    class RegisterClass {
        var results: MutableMap<Test, TestExecutionScheduleReportBuilder> = mutableMapOf()
        fun add(
            task: Test,
            className: String?,
            testName: String,
            startTime: Long,
            endTime: Long,
            resultType: String
        ) {
            val taskResults = results.getOrPut(task) { TestExecutionScheduleReportBuilder() }
            taskResults.add(className, testName, startTime, endTime, resultType)
        }
    }

    fun getRegister(): RegisterClass {
        return register
    }

    fun getResults(module: Path?): Map<Test, TestExecutionScheduleReport> {
        val reportBuilders =
            if (module == null) register.results else register.results.filterKeys { it.identityPath.parent == module }
        return reportBuilders.mapValues { it.value.getResults() }
    }
}
