package io.github.platan.tests_execution_chart.config

import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

abstract class Mermaid @Inject constructor(objectFactory: ObjectFactory) : Format(objectFactory, "mermaid")
