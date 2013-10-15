:-style_check(-discontiguous).

table(umlfSimpleClass,[id,"name",x,y]).
umlfSimpleClass(cdl_4,'SimpleQuestion',270,326).
umlfSimpleClass(cdl_5,'QuestionSet',606,324).

table(umlfClass,[id,"name","members",x,y]).
umlfClass(cds_1,'Questionaire','-name',79,127).
umlfClass(cds_2,'Question;','-text',424,124).
umlfClass(cds_3,'AnswerChoice','-choicevalue',733,123).

table(umlfInterface,[id,"name","members",x,y]).
:- dynamic umlfInterface/5.

table(umlfAssociation,[to,target,dashed,anchor,source,from,type]).
umlfAssociation(cds_2,none,false,false,composition,cds_1,standard).
umlfAssociation(cds_3,none,false,false,composition,cds_2,standard).
umlfAssociation(cdl_4,none,false,false,aggregation,cdl_5,standard).
umlfAssociation(cds_2,gen,false,true,none,cdl_4,standard).
umlfAssociation(cds_2,gen,false,true,none,cdl_5,standard).

