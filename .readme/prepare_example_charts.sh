#!/bin/sh

npx -p @mermaid-js/mermaid-cli mmdc -i ../test-projects/spock-single-module/build/reports/tests-execution/mermaid/test.txt -o example_dark.svg -t dark -b transparent
npx -p @mermaid-js/mermaid-cli mmdc -i ../test-projects/spock-single-module/build/reports/tests-execution/mermaid/test.txt -o example.svg -b transparent
