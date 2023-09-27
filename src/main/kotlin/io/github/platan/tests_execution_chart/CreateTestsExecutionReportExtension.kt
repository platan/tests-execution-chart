package io.github.platan.tests_execution_chart

import io.github.platan.tests_execution_chart.gradle.config.Components
import io.github.platan.tests_execution_chart.gradle.config.Marks
import io.github.platan.tests_execution_chart.gradle.config.formats.Formats
import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Nested
import javax.inject.Inject

abstract class CreateTestsExecutionReportExtension @Inject constructor(objectFactory: ObjectFactory) {

    @Nested
    abstract fun getFormats(): Formats

    @Nested
    abstract fun getMarks(): Marks

    @Nested
    abstract fun getComponents(): Components

    @get:Input
    val shiftTimestampsToStartOfDay: Property<Boolean> = objectFactory.property(Boolean::class.java).convention(false)

    open fun formats(action: Action<in Formats>) {
        action.execute(getFormats())
    }

    open fun marks(action: Action<in Marks>) {
        action.execute(getMarks())
    }

    open fun components(action: Action<in Components>) {
        action.execute(getComponents())
    }
}
