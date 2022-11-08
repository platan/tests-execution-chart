import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import kotlinx.coroutines.delay

private const val sleepDurationMs = 500L

class Test2 : FunSpec({

    test("test 1") {
        delay(sleepDurationMs)
    }

    test("test 2") {
        delay(sleepDurationMs)
    }

    context("test 3") {
        withData(
            "a", "b"
        ) {
            delay(sleepDurationMs)
        }
    }
})