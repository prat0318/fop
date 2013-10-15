table(class,[id,"name","fields","methods",superid]).
class(c1,'Customer','','',null).
class(c4,'Address','','',null).
class(c3,'Billing Address','','',null).
class(c2,'Order','','',null).
class(c6,'Location','','',null).
class(c0,'Batory','','',null).
class(c5,'Company','','',null).

table(association,[cid1,"role1",arrow1,cid2,"role2",arrow2]).
association(c1,'1<>',none,c2,'*',arrow).
association(c1,'',none,c3,'',arrow).
association(c1,'1',none,c4,'0..*',none).
association(c2,'',none,c4,'billing ',arrow).
association(c2,'',none,c4,'shipping ',arrow).
association(c5,'',agg,c6,'1',arrow).

table(interface,[id,"name","methods"]).
:- dynamic interface/3.

table(classImplements,[cid,iid]).
:- dynamic classImplements/2.

table(interfaceExtends,[id,idx]).
:- dynamic interfaceExtends/2.

