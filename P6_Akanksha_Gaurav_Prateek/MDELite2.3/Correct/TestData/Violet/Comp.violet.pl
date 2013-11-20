:- style_check(-discontiguous).

table(violetClass,[id,"name","fields","methods",superid,x,y]).
violetClass(classnode0,'Figure','position','draw()',null,322,234).
violetClass(classnode1,'Group','','draw()',null,237,392).
violetClass(classnode2,'Polygon','','draw()',null,405,387).

table(violetInterface,[id,"name","methods",x,y]).
:- dynamic violetInterface/5.

table(violetAssociation,[cid1,"role1",arrow1,type1,cid2,"role2",arrow2,type2,lineStyle]).
violetAssociation(classnode1,'partOf','DIAMOND','classnode',classnode0,'consistsOf','V','classnode','').
violetAssociation(classnode2,'','','classnode',classnode0,'','TRIANGLE','classnode','').
violetAssociation(classnode1,'','','classnode',classnode0,'','TRIANGLE','classnode','').

table(violetInterfaceExtends,[id,idx]).
:- dynamic violetInterfaceExtends/2.

table(violetClassImplements,[cid,iid]).
:- dynamic violetClassImplements/2.
