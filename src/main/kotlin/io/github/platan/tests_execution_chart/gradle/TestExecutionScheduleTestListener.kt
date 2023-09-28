package io.github.platan.tests_execution_chart.gradle

import io.github.platan.tests_execution_chart.report.data.TimedTestResult
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestDescriptor
import org.gradle.api.tasks.testing.TestListener
import org.gradle.api.tasks.testing.TestResult

class TestExecutionScheduleTestListener(
    private val service: Provider<TestExecutionResultsRegisterService>,
    private val task: Test,
    private val suitesEnabled: Property<Boolean>
) :
    TestListener {

    override fun beforeSuite(suite: TestDescriptor) {
    }

    override fun afterSuite(suite: TestDescriptor, result: TestResult) {
        if (suitesEnabled.get()) {
            service.get().getRegister().add(
                task,
                suite.name,
                "suite",
                result.startTime,
                result.endTime,
                result.resultType.toString(),
                TimedTestResult.Type.SUITE,
                suite.parent?.name
            )
        }
    }

    override fun beforeTest(testDescriptor: TestDescriptor) {
    }

    override fun afterTest(testDescriptor: TestDescriptor, testResult: TestResult) {
        service.get().getRegister().add(
            task,
            testDescriptor.className,
            testDescriptor.name,
            testResult.startTime,
            testResult.endTime,
            testResult.resultType.toString(),
            TimedTestResult.Type.TEST,
            testDescriptor.parent?.name
        )
    }
}
