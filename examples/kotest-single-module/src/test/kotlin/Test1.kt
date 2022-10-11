import io.kotest.core.spec.style.StringSpec
import kotlinx.coroutines.delay

private const val sleepDurationMs = 200L

class Test1 : StringSpec({

    "test 1" {
        delay(sleepDurationMs)
    }

    "test 2".config(enabled = false) {
        delay(sleepDurationMs)
    }

    "test 3" {
        delay(sleepDurationMs)
    }

})