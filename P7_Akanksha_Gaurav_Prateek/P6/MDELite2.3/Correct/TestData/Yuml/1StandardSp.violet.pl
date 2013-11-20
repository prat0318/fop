table(violetClass,[id,"name","fields","methods",superid,x,y]).
violetClass('c1','Customer','','','null',350,140).
violetClass('c4','Address','','','null',350,350).
violetClass('c3','Billing Address','','','null',140,140).
violetClass('c2','Order','','','null',840,343).
violetClass('c6','Location','','','null',350,980).
violetClass('c0','Batory','','','null',980,980).
violetClass('c5','Company','','','null',140,980).

table(violetInterface,[id,"name","methods",x,y]).
:- dynamic violetInterface/5.

table(violetAssociation,[cid1,"role1",arrow1,type1,cid2,"role2",arrow2,type2,lineStyle]).
violetAssociation(c1,'1<>','','classnode',c2,'*','V','classnode','').
violetAssociation(c1,'','','classnode',c3,'','V','classnode','').
violetAssociation(c1,'1','','classnode',c4,'0..*','','classnode','').
violetAssociation(c2,'','','classnode',c4,'billing ','V','classnode','').
violetAssociation(c2,'','','classnode',c4,'shipping ','V','classnode','').
violetAssociation(c5,'','DIAMOND','classnode',c6,'1','V','classnode','').

table(violetInterfaceExtends,[id,idx]).
:- dynamic violetInterfaceExtends/2.

table(violetClassImplements,[cid,iid]).
:- dynamic violetClassImplements/2.

