import io.kotest.core.annotation.Isolate
import io.kotest.core.spec.style.StringSpec
import kotlinx.coroutines.delay

private const val sleepDurationMs = 50L

@Isolate
class TestIsolated : StringSpec({

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