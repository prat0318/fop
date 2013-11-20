:- style_check(-discontiguous).

table(violetClass,[id,"name","fields","methods",superid,x,y]).
violetClass(classnode0,'customer','','',null,433,168).
violetClass(classnode1,'receipt','','',null,824,168).
violetClass(classnode2,'contract','','',null,825,369).
violetClass(classnode3,'CarRentalCompany','','',null,420,363).
violetClass(classnode4,'RentalCar','','',null,425,509).

table(violetInterface,[id,"name","methods",x,y]).
violetInterface(unconnected0,'Don','',830,519).

table(violetAssociation,[cid1,"role1",arrow1,type1,cid2,"role2",arrow2,type2,lineStyle]).
violetAssociation(classnode0,'1','BLACK_DIAMOND','classnode',classnode2,'*','','classnode','').
violetAssociation(classnode0,'1','BLACK_DIAMOND','classnode',classnode1,'paid','','classnode','').
violetAssociation(classnode2,'1','BLACK_DIAMOND','classnode',classnode1,'0..1','','classnode','').
violetAssociation(classnode2,'1','','classnode',classnode4,'1','BLACK_DIAMOND','classnode','').
violetAssociation(classnode3,'1 belongs_to','','classnode',classnode4,'* owns','V','classnode','').
violetAssociation(classnode3,'*','','classnode',classnode0,'*','','classnode','').

table(violetInterfaceExtends,[id,idx]).
:- dynamic violetInterfaceExtends/2.

table(violetClassImplements,[cid,iid]).
:- dynamic violetClassImplements/2.
