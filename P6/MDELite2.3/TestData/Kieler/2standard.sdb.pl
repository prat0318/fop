table(class,[id,"name","fields","methods",superid]).
class(cdl_5,'c5','','',cdl_1).
class(cdl_1,'c1','','',null).
class(cdl_2,'c2','','',null).
class(cdl_3,'c3','','',null).
class(cdl_4,'c4','','',null).

table(association,[cid1,"role1",arrow1,cid2,"role2",arrow2]).
association(cdl_1,'',none,cdl_2,'',none).
association(cdl_1,'',none,cdl_3,'',agg).
association(cdl_1,'',none,cdl_4,'',comp).

table(interface,[id,"name","methods"]).
:- dynamic interface/3.

table(classImplements,[cid,iid]).
:- dynamic classImplements/2.

table(interfaceExtends,[id,idx]).
:- dynamic interfaceExtends/2.

table(position,[id,x,y]).
position(cdl_1,173,54).
position(cdl_2,473,53).
position(cdl_3,472,153).
position(cdl_4,474,250).
position(cdl_5,476,335).

