:-style_check(-discontiguous).

table(class,[id,"name","fields","methods",superid]).
class(cdl_4,'SimpleQuestion','','',cds_2).
class(cdl_5,'QuestionSet','','',cds_2).
class(cds_1,'Questionaire','','-name',null).
class(cds_2,'Question;','','-text',null).
class(cds_3,'AnswerChoice','','-choicevalue',null).

table(association,[cid1,"role1",arrow1,cid2,"role2",arrow2]).
association(cds_2,'',none,cds_1,'',comp).
association(cds_3,'',none,cds_2,'',comp).
association(cdl_4,'',none,cdl_5,'',agg).

table(interface,[id,"name","methods"]).
:- dynamic interface/3.

table(classImplements,[cid,iid]).
:- dynamic classImplements/2.

table(interfaceExtends,[id,idx]).
:- dynamic interfaceExtends/2.

table(position,[id,x,y]).
position(cdl_4,270,326).
position(cdl_5,606,324).
position(cds_1,79,127).
position(cds_2,424,124).
position(cds_3,733,123).

