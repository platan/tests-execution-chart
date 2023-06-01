package io.github.platan.tests_execution_chart.reporters.mermaid.core

import spock.lang.Specification

class MermaidGanttDiagramBuilderTest extends Specification {

    def "should return error for blank mark name"() {
        when:
        new MermaidGanttDiagram.MermaidGanttDiagramBuilder().addMilestone(name, 0)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == 'Mark name cannot be blank'

        where:
        name << ['', ' ']
    }

    def "should return error for blank task name"() {
        when:
        new MermaidGanttDiagram.MermaidGanttDiagramBuilder().addTask(name, 'active', 0, 1)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == 'Task name cannot be blank'

        where:
        name << ['', ' ']
    }

    def "should return error for blank section name"() {
        when:
        new MermaidGanttDiagram.MermaidGanttDiagramBuilder().addSection(name)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == 'Section name cannot be blank'

        where:
        name << ['', ' ']
    }
}
