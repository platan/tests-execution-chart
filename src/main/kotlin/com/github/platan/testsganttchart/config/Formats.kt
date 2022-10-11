package com.github.platan.testsganttchart.config

import org.gradle.api.Action
import org.gradle.api.tasks.Nested

abstract class Formats {
    @Nested
    abstract fun getHtml(): Html

    @Nested
    abstract fun getJson(): Json

    @Nested
    abstract fun getMermaid(): Mermaid

    open fun html(action: Action<in Html>) {
        action.execute(getHtml())
    }

    open fun json(action: Action<in Json>) {
        action.execute(getJson())
    }

    open fun mermaid(action: Action<Mermaid>) {
        action.execute(getMermaid())
    }
}