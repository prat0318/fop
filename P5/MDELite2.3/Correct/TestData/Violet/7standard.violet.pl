:- style_check(-discontiguous).

table(violetClass,[id,"name","fields","methods",superid,x,y]).
violetClass(classnode0,'','','',null,729,446).

table(violetInterface,[id,"name","methods",x,y]).
violetInterface(interfacenode0,'interface2','',674,260).
violetInterface(interfacenode1,'interface1','',615,85).

table(violetAssociation,[cid1,"role1",arrow1,type1,cid2,"role2",arrow2,type2,lineStyle]).
violetClassImplements(classnode0,interfacenode0).
violetAssociation(classnode0,'','','classnode',interfacenode0,'','TRIANGLE','interfacenode','DOTTED').
violetInterfaceExtends(interfacenode0,interfacenode1).
violetAssociation(interfacenode0,'','','interfacenode',interfacenode1,'','TRIANGLE','interfacenode','').

table(violetInterfaceExtends,[id,idx]).

table(violetClassImplements,[cid,iid]).
