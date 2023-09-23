package io.github.platan.tests_execution_chart.reporters

object NoopLogger : Logger {
    override fun lifecycle(message: String) {
        // nothing to do
    }

}
