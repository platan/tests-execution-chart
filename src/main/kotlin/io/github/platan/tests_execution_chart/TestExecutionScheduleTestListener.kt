package io.github.platan.tests_execution_chart

import org.gradle.api.provider.Provider
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestDescriptor
import org.gradle.api.tasks.testing.TestListener
import org.gradle.api.tasks.testing.TestResult

class TestExecutionScheduleTestListener(
    private val service: Provider<TestExecutionResultsRegisterService>,
    private val task: Test
) :
    TestListener {

    override fun beforeSuite(suite: TestDescriptor) {
    }

    override fun afterSuite(suite: TestDescriptor, result: TestResult) {
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
            testResult.resultType.toString()
        )
    }
}
