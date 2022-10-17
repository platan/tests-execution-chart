package com.github.platan.tests_execution_chart.reporters.mermaid


import spock.lang.Specification
import spock.lang.TempDir

class MermaidGanttDiagramFormatterSpec extends Specification {

    @TempDir
    File tempDir
    def inputFile
    def inputPath
    def outputPath
    def processOutput = new StringBuilder()
    def processError = new StringBuilder()

    def setup() {
        inputFile = new File(tempDir, "input.mmd")
        inputPath = inputFile.getAbsolutePath()
        outputPath = new File(tempDir, "output.svg").getAbsolutePath()
    }

    def "should produce result parsable by mmdc (mermaid CLI)"() {
        given:
        def diagramBuilder = new MermaidGanttDiagram.MermaidGanttDiagramBuilder()
        diagramBuilder.add('test-section', 'test-row', 'active', 1, 2)
        def diagram = diagramBuilder.build("YYYY-MM-DD\\THH\\:mm\\:ss\\.SSSZ", "%H:%M:%S.%L")
        def mermaid = new MermaidGanttDiagramFormatter().format(diagram, "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        inputFile.text = mermaid

        when:
        def process = "npx -p @mermaid-js/mermaid-cli mmdc -i $inputPath -o $outputPath".execute()

        then:
        process.consumeProcessOutput(processOutput, processError)
        process.waitForOrKill(60000)
        assert process.exitValue() == 0, "Process finished with exit code `${process.exitValue()}, output: $processOutput\nerror: $processError"
    }

}