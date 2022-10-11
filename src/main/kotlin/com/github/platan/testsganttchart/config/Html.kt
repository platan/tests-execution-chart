package com.github.platan.testsganttchart.config

import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.Nested
import javax.inject.Inject

abstract class Html @Inject constructor(objectFactory: ObjectFactory) : Format(objectFactory, "html") {
    @Nested
    abstract fun getScript(): Script

    open fun script(action: Action<in Script>) {
        action.execute(getScript())
    }
}