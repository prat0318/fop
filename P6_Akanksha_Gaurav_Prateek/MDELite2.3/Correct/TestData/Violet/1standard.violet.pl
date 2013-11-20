:- style_check(-discontiguous).

table(violetClass,[id,"name","fields","methods",superid,x,y]).
violetClass(classnode0,'Parent','','doP()',null,605,213).
violetClass(classnode1,'Child','celem;ccelem','doC()',null,606,483).

table(violetInterface,[id,"name","methods",x,y]).
:- dynamic violetInterface/5.

table(violetAssociation,[cid1,"role1",arrow1,type1,cid2,"role2",arrow2,type2,lineStyle]).
violetAssociation(classnode0,'','TRIANGLE','classnode',classnode1,'','','classnode','').

table(violetInterfaceExtends,[id,idx]).
:- dynamic violetInterfaceExtends/2.

table(violetClassImplements,[cid,iid]).
:- dynamic violetClassImplements/2.
