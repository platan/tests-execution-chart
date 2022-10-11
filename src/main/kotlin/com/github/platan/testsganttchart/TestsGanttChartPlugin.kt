package com.github.platan.testsganttchart

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.testing.Test


class TestsGanttChartPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val serviceProvider: Provider<TestExecutionResultsRegisterService> =
            project.gradle.sharedServices.registerIfAbsent(
                "testExecutionResultsRegisterService", TestExecutionResultsRegisterService::class.java
            ) {}

        val createTestGanttChartExtension =
            project.extensions.create("createTestGanttChart", com.github.platan.testsganttchart.CreateTestGanttChartExtension::class.java)
        project.tasks.register("createTestGanttChart", com.github.platan.testsganttchart.CreateTestGanttChartTask::class.java) { task ->
            configure(task, createTestGanttChartExtension)
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
        task: com.github.platan.testsganttchart.CreateTestGanttChartTask,
        createTestGanttChartExtension: com.github.platan.testsganttchart.CreateTestGanttChartExtension
    ) {
        task.getFormats().getHtml().outputLocation
            .set(createTestGanttChartExtension.getFormats().getHtml().outputLocation)
        task.getFormats().getHtml().getScript().src
            .set(createTestGanttChartExtension.getFormats().getHtml().getScript().src)
        task.getFormats().getHtml().getScript().embed
            .set(createTestGanttChartExtension.getFormats().getHtml().getScript().embed)
        task.getFormats().getJson().outputLocation
            .set(createTestGanttChartExtension.getFormats().getJson().outputLocation)
        task.getFormats().getJson().enabled
            .set(createTestGanttChartExtension.getFormats().getJson().enabled)
        task.getFormats().getMermaid().enabled
            .set(createTestGanttChartExtension.getFormats().getMermaid().enabled)
        task.getFormats().getMermaid().outputLocation
            .set(createTestGanttChartExtension.getFormats().getMermaid().outputLocation)
    }
}
