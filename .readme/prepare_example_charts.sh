#!/bin/sh

../node_modules/.bin/mmdc -i ../examples/spock-single-module/build/reports/tests-gantt/mermaid/test.txt -o example_dark.svg -t dark -b transparent
../node_modules/.bin/mmdc -i ../examples/spock-single-module/build/reports/tests-gantt/mermaid/test.txt -o example.svg -b transparent

