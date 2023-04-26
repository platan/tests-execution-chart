package io.github.platan.tests_execution_chart.reporters.mermaid

import spock.lang.Retry
import spock.lang.Specification
import spock.lang.TempDir

class MermaidGanttDiagramFormatterToSvgConversionSpec extends Specification {

    @TempDir
    File tempDir
    def inputFile
    def inputPath
    def outputPath
    def processOutput = new StringBuilder()
    def processError = new StringBuilder()

    def final static SPECIAL_CHARS = '''!"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~'''

    def setup() {
        inputFile = new File(tempDir, "input.mmd")
        inputPath = inputFile.getAbsolutePath()
        outputPath = new File(tempDir, "output.svg").getAbsolutePath()
    }

    // Fails sometimes on Windows runner
    @Retry(mode = Retry.Mode.SETUP_FEATURE_CLEANUP)
    def "should produce result parsable by mmdc (mermaid CLI)"() {
        given:
        def diagramBuilder = new MermaidGanttDiagram.MermaidGanttDiagramBuilder()
        diagramBuilder.add("test-section $SPECIAL_CHARS", "test-row $SPECIAL_CHARS", 'active', 1, 2)
        def diagram = diagramBuilder.build("YYYY-MM-DD\\THH\\:mm\\:ss\\.SSSZ", "%H:%M:%S.%L")
        def mermaid = new MermaidGanttDiagramFormatter().format(diagram, "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        inputFile.text = mermaid

        when:
        def process = "${getCommand("npx")} -p @mermaid-js/mermaid-cli mmdc -i $inputPath -o $outputPath".execute()

        then:
        process.consumeProcessOutput(processOutput, processError)
        process.waitForOrKill(60000)
        assert process.exitValue() == 0, "Process finished with exit code ${process.exitValue()}\nOutput: $processOutput\nError: $processError"
    }

    String getCommand(String command) {
        if (System.properties['os.name'].toString().toLowerCase().contains('windows')) {
            return "${command}.cmd"
        } else {
            return command
        }
    }

}