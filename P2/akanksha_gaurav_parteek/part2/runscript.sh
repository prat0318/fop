#!/bin/bash
#in order to check the validity of the data present in the graph specified in the .pl file we need to run the cmd checkDataValidity
swipl -s part2.pl -g "writeln('**Checking the validity of the graph as given in the script**'), checkDataValidity." -t halt --quiet
