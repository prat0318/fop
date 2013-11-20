:- style_check(-discontiguous).

table(violetClass,[id,"name","fields","methods",superid,x,y]).
violetClass(classnode0,'ParentClass','parentElem','parentMeth();parentMeth2()',null,643,200).
violetClass(classnode1,'ChildClass','childElem','childMeth()',null,646,383).

table(violetInterface,[id,"name","methods",x,y]).
:- dynamic violetInterface/5.

table(violetAssociation,[cid1,"role1",arrow1,type1,cid2,"role2",arrow2,type2,lineStyle]).
violetAssociation(classnode1,'','','classnode',classnode0,'','TRIANGLE','classnode','').

table(violetInterfaceExtends,[id,idx]).
:- dynamic violetInterfaceExtends/2.

table(violetClassImplements,[cid,iid]).
:- dynamic violetClassImplements/2.
