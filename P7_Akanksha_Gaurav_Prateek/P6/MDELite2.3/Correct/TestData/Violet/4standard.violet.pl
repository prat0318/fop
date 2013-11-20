:- style_check(-discontiguous).

table(violetClass,[id,"name","fields","methods",superid,x,y]).
violetClass(classnode0,'Top','','',null,574,77).
violetClass(classnode1,'Middle','','',null,607,257).
violetClass(classnode2,'Lower','','',null,621,420).

table(violetInterface,[id,"name","methods",x,y]).
:- dynamic violetInterface/5.

table(violetAssociation,[cid1,"role1",arrow1,type1,cid2,"role2",arrow2,type2,lineStyle]).
violetAssociation(classnode1,'','','classnode',classnode0,'','TRIANGLE','classnode','').
violetAssociation(classnode2,'','','classnode',classnode1,'','V','classnode','').

table(violetInterfaceExtends,[id,idx]).
:- dynamic violetInterfaceExtends/2.

table(violetClassImplements,[cid,iid]).
:- dynamic violetClassImplements/2.
