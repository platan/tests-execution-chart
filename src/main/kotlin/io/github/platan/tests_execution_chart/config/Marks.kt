package io.github.platan.tests_execution_chart.config

import org.gradle.api.Action
import org.gradle.api.tasks.Nested

abstract class Marks {

    @Nested
    abstract fun getTotalTimeOfAllTests(): Mark

    open fun config(action: Action<in Mark>) {
        action.execute(getTotalTimeOfAllTests())
    }
}
