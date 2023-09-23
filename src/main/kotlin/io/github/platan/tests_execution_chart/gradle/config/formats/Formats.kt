package io.github.platan.tests_execution_chart.gradle.config.formats

import io.github.platan.tests_execution_chart.gradle.config.formats.json.Json
import io.github.platan.tests_execution_chart.gradle.config.formats.mermaid.Mermaid
import io.github.platan.tests_execution_chart.report.ReportConfig
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

    fun toFormatsList(): List<ReportConfig.Format> =
        listOf(getMermaid().toMermaidConfig(), getHtml().toHtmlConfig(), getJson().toJsonConfig())
}
