#!/bin/bash
#printDbase(families2) prints tables of families2 (f2) i.e. family2 and member2
swipl -s families.pl -g "writeln('**PRINTING f1 (DB before transformation)**'), printDbase(families), writeln('\n*****PRINTING f2 (DB after transformation)*****'),printDbase(families2)." -t halt --quiet
