package io.github.platan.tests_execution_chart.gradle.config

import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

abstract class TotalTimeOfAllTestsMark @Inject constructor(objectFactory: ObjectFactory) :
    Mark(objectFactory, "total time of all tests")
