:- style_check(-discontiguous).

table(violetClass,[id,"name","fields","methods",superid,x,y]).
violetClass(unconnected0,'Eric','','',null,432,184).

table(violetInterface,[id,"name","methods",x,y]).
violetInterface(unconnected1,'Don','',1004,301).

table(violetAssociation,[cid1,"role1",arrow1,type1,cid2,"role2",arrow2,type2,lineStyle]).
:- dynamic violetAssociation/9.

table(violetInterfaceExtends,[id,idx]).
:- dynamic violetInterfaceExtends/2.

table(violetClassImplements,[cid,iid]).
:- dynamic violetClassImplements/2.
