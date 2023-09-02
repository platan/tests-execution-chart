package io.github.platan.tests_execution_chart.report

import io.github.platan.tests_execution_chart.report.data.TestExecutionScheduleReport
import java.time.ZoneId

class ReportConfigurator {
    fun configure(results: TestExecutionScheduleReport, config: ReportConfig): TestExecutionScheduleReport {
        var adjusted = results
        if (config.shiftTimestampsToStartOfDay) {
            adjusted = results.timestampsShiftedToStartOfDay(ZoneId.systemDefault())
        }
        if (config.marks.totalTimeOfAllTests.enabled) {
            adjusted = adjusted.addTotalTimeOfAllTestsMark(config.marks.totalTimeOfAllTests.name)
        }
        return adjusted
    }
}
