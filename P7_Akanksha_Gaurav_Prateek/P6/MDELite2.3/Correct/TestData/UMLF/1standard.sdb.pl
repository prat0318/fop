:-style_check(-discontiguous).

table(class,[id,"name","fields","methods",superid]).
class(cdl_3,'c1','','',cdf_1).
class(cdl_4,'c2','','',cdf_2).

table(association,[cid1,"role1",arrow1,cid2,"role2",arrow2]).
:- dynamic association/6.

table(interface,[id,"name","methods"]).
interface(cdf_1,'i1','+a();+b()').
interface(cdf_2,'i2','+c(x,y)').

table(classImplements,[cid,iid]).
classImplements(cdl_3,cdf_1).
classImplements(cdl_4,cdf_2).

table(interfaceExtends,[id,idx]).
interfaceExtends(cdf_2,cdf_1).

table(position,[id,x,y]).
position(cdl_3,138,221).
position(cdl_4,488,206).
position(cdf_1,112,56).
position(cdf_2,495,55).

