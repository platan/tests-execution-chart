package io.github.platan.tests_execution_chart.config

import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.Nested
import javax.inject.Inject

abstract class Marks @Inject constructor(objectFactory: ObjectFactory) {

    @Nested
    abstract fun getTotalTimeOfAllTests(): Mark

    open fun config(action: Action<in Mark>) {
        action.execute(getTotalTimeOfAllTests())
    }
}
