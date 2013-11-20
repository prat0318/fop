:- style_check(-discontiguous).

table(violetClass,[id,"name","fields","methods",superid,x,y]).
violetClass(classnode0,'ClassA','dang;dang2','doIa();do1b();myOwn()',null,587,326).

table(violetInterface,[id,"name","methods",x,y]).
violetInterface(interfacenode0,'AnInterface','doIa();doIb()',582,141).

table(violetAssociation,[cid1,"role1",arrow1,type1,cid2,"role2",arrow2,type2,lineStyle]).
violetClassImplements(classnode0,interfacenode0).
violetAssociation(classnode0,'','','classnode',interfacenode0,'','TRIANGLE','interfacenode','DOTTED').

table(violetInterfaceExtends,[id,idx]).
:- dynamic violetInterfaceExtends/2.

table(violetClassImplements,[cid,iid]).
