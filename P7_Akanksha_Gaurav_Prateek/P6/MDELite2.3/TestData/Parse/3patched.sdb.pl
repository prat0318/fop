%table(class,[id,"name","fields","methods",superid]).
class(cds_1,'c1','','+attribute;+operation',cds_2).
class(cds_4,'c3','','+attribute;+operation',cds_2).
class(cds_2,'c2','','+attribute;+operation',null).

%table(association,[cid1,"role1",arrow1,cid2,"role2",arrow2]).
association(cds_1,'',none,cds_4,'',none).

%table(interface,[id,"name","methods"]).
interface(cdf_3,'i1','+operation(param)').
interface(cdf_5,'i2','+operation(param)').

%table(classImplements,[cid,iid]).
classImplements(cds_1,cdf_3).
classImplements(cds_4,cdf_5).

%table(interfaceExtends,[id,idx]).
:- dynamic interfaceExtends/2.

%table(position,[id,px,py]).
position(cds_1,140,350).
position(cds_4,350,140).
position(cds_2,350,350).
position(cdf_3,980,140).
position(cdf_5,140,980).

