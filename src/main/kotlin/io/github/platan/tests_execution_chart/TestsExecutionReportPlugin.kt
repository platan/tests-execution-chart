package io.github.platan.tests_execution_chart

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.testing.Test

class TestsExecutionReportPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val serviceProvider: Provider<TestExecutionResultsRegisterService> =
            project.gradle.sharedServices.registerIfAbsent(
                "testExecutionResultsRegisterService",
                TestExecutionResultsRegisterService::class.java
            ) {}

        val createTestsExecutionReportExtension =
            project.extensions.create("createTestsExecutionReport", CreateTestsExecutionReportExtension::class.java)
        project.tasks.register("createTestsExecutionReport", CreateTestsExecutionReportTask::class.java) { task ->
            configure(task, createTestsExecutionReportExtension)
            task.getRegisterService().set(serviceProvider)
            task.usesService(serviceProvider)
            task.outputs.upToDateWhen { false }
            task.setMustRunAfter(project.tasks.withType(Test::class.java))
        }
        project.tasks.withType(Test::class.java) { task ->
            task.addTestListener(TestExecutionScheduleTestListener(serviceProvider, task))
        }
    }

    private fun configure(
        task: CreateTestsExecutionReportTask,
        createTestsExecutionReportExtension: CreateTestsExecutionReportExtension
    ) {
        task.getFormats().getHtml().outputLocation
            .set(createTestsExecutionReportExtension.getFormats().getHtml().outputLocation)
        task.getFormats().getHtml().getScript().getConfig()
            .maxTextSize.set(
                createTestsExecutionReportExtension.getFormats().getHtml().getScript().getConfig().maxTextSize
            )
        task.getFormats().getHtml().getScript().src
            .set(createTestsExecutionReportExtension.getFormats().getHtml().getScript().src)
        task.getFormats().getHtml().getScript().embed
            .set(createTestsExecutionReportExtension.getFormats().getHtml().getScript().embed)
        task.getFormats().getJson().outputLocation
            .set(createTestsExecutionReportExtension.getFormats().getJson().outputLocation)
        task.getFormats().getJson().enabled
            .set(createTestsExecutionReportExtension.getFormats().getJson().enabled)
        task.getFormats().getMermaid().enabled
            .set(createTestsExecutionReportExtension.getFormats().getMermaid().enabled)
        task.getFormats().getMermaid().outputLocation
            .set(createTestsExecutionReportExtension.getFormats().getMermaid().outputLocation)
    }
}
