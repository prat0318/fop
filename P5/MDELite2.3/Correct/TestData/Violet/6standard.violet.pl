:- style_check(-discontiguous).

table(violetClass,[id,"name","fields","methods",superid,x,y]).
:- dynamic violetClass/7.

table(violetInterface,[id,"name","methods",x,y]).
violetInterface(interfacenode0,'interface2','',674,260).
violetInterface(interfacenode1,'','',615,85).

table(violetAssociation,[cid1,"role1",arrow1,type1,cid2,"role2",arrow2,type2,lineStyle]).
violetInterfaceExtends(interfacenode0,interfacenode1).
violetAssociation(interfacenode0,'','','interfacenode',interfacenode1,'','TRIANGLE','interfacenode','').

table(violetInterfaceExtends,[id,idx]).

table(violetClassImplements,[cid,iid]).
:- dynamic violetClassImplements/2.
