package io.github.platan.tests_execution_chart.reporters.html

import io.github.platan.tests_execution_chart.reporters.Logger

object NoopLogger : Logger {
    override fun lifecycle(message: String) {
        // nothing to do
    }

}
