:- style_check(-discontiguous).

table(violetClass,[id,"name","fields","methods",superid,x,y]).
violetClass(classnode0,'left','','',null,575,275).
violetClass(classnode1,'right','','',null,803,315).

table(violetInterface,[id,"name","methods",x,y]).
violetInterface(interfacenode0,'','',646,91).

table(violetAssociation,[cid1,"role1",arrow1,type1,cid2,"role2",arrow2,type2,lineStyle]).
violetClassImplements(classnode0,interfacenode0).
violetAssociation(classnode0,'','','classnode',interfacenode0,'','TRIANGLE','interfacenode','DOTTED').
violetClassImplements(classnode1,interfacenode0).
violetAssociation(classnode1,'','','classnode',interfacenode0,'','TRIANGLE','interfacenode','DOTTED').

table(violetInterfaceExtends,[id,idx]).
:- dynamic violetInterfaceExtends/2.

table(violetClassImplements,[cid,iid]).
