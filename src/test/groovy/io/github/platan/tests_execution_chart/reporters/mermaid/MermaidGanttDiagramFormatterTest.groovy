package io.github.platan.tests_execution_chart.reporters.mermaid


import spock.lang.Specification

import java.time.ZoneOffset

class MermaidGanttDiagramFormatterTest extends Specification {

    def "should format report"() {
        given:
        def getDefault = TimeZone.getDefault()
        TimeZone.setDefault(SimpleTimeZone.getTimeZone(ZoneOffset.ofHours(2)))
        def diagramBuilder = new MermaidGanttDiagram.MermaidGanttDiagramBuilder()
        diagramBuilder.addSection('Test1')
        diagramBuilder.addTask('test1 - 100 ms', 'active', 1681402397000, 1681402397100)
        diagramBuilder.addTask('test2 - 200 ms', 'active', 1681402397100, 1681402397300)
        diagramBuilder.addMilestone('milestone1', 1681402397100)
        diagramBuilder.addSection('Test2')
        diagramBuilder.addTask('test1 - 100 ms', 'active', 1681402397000, 1681402397100)
        diagramBuilder.addMilestone('milestone2', 1681402397400)

        MermaidGanttDiagram diagram = diagramBuilder.build("YYYY-MM-DDTHH:mm:ss.SSSZZ", "%H:%M:%S.%L")

        when:
        def mermaidReport = new MermaidGanttDiagramFormatter().format(diagram, "yyyy-MM-dd'T'HH:mm:ss.SSSZ")


        def indent = """gantt
dateFormat YYYY-MM-DDTHH:mm:ss.SSSZZ
axisFormat %H:%M:%S.%L
section Test1
test1 - 100 ms :active, 2023-04-13T18:13:17.000+0200, 2023-04-13T18:13:17.100+0200
test2 - 200 ms :active, 2023-04-13T18:13:17.100+0200, 2023-04-13T18:13:17.300+0200
milestone1 : milestone, 2023-04-13T18:13:17.100+0200, 0
section Test2
test1 - 100 ms :active, 2023-04-13T18:13:17.000+0200, 2023-04-13T18:13:17.100+0200
milestone2 : milestone, 2023-04-13T18:13:17.400+0200, 0
"""
        then:
        mermaidReport == indent

        cleanup:
        TimeZone.setDefault(getDefault)
    }

    def "should format report with special characters"() {
        given:
        def getDefault = TimeZone.getDefault()
        TimeZone.setDefault(SimpleTimeZone.getTimeZone(ZoneOffset.ofHours(2)))
        def diagramBuilder = new MermaidGanttDiagram.MermaidGanttDiagramBuilder()
        diagramBuilder.addSection('Test # ; : 1')
        diagramBuilder.addTask('test # ; : 1', 'active', 1681402397000, 1681402397100)
        diagramBuilder.addMilestone('milestone # ; : 1', 1681402397100)

        MermaidGanttDiagram diagram = diagramBuilder.build("YYYY-MM-DDTHH:mm:ss.SSSZZ", "%H:%M:%S.%L")

        when:
        def mermaidReport = new MermaidGanttDiagramFormatter().format(diagram, "yyyy-MM-dd'T'HH:mm:ss.SSSZ")


        def indent = """gantt
dateFormat YYYY-MM-DDTHH:mm:ss.SSSZZ
axisFormat %H:%M:%S.%L
section Test #35; #semi; #colon; 1
test #35; #semi; #colon; 1 :active, 2023-04-13T18:13:17.000+0200, 2023-04-13T18:13:17.100+0200
milestone #35; #semi; #colon; 1 : milestone, 2023-04-13T18:13:17.100+0200, 0
"""
        then:
        mermaidReport == indent

        cleanup:
        TimeZone.setDefault(getDefault)
    }
}
