package io.github.platan.tests_execution_chart.config

import io.github.platan.tests_execution_chart.reporters.config.HtmlConfig
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

    fun toHtmlConfig(): HtmlConfig = HtmlConfig(
        HtmlConfig.Format(enabled.get(), outputLocation.get()),
        HtmlConfig.Script(
            getScript().src.get(),
            getScript().embed.get(),
            HtmlConfig.Script.Options(getScript().getConfig().maxTextSize.get())
        )
    )
}
