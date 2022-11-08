import io.kotest.common.ExperimentalKotest
import io.kotest.core.config.AbstractProjectConfig

object KotestProjectConfig : AbstractProjectConfig() {
    @ExperimentalKotest
    override val concurrentSpecs = 4

    @ExperimentalKotest
    override val concurrentTests = 4
}
