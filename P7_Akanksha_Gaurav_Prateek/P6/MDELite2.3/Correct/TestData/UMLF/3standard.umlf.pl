:-style_check(-discontiguous).

table(umlfSimpleClass,[id,"name",x,y]).
:- dynamic umlfSimpleClass/4.

table(umlfClass,[id,"name","members",x,y]).
umlfClass(cds_1,'c1','+attribute;+operation',425,354).
umlfClass(cds_2,'c2','+attribute;+operation',503,169).
umlfClass(cds_4,'c3','+attribute;+operation',661,348).

table(umlfInterface,[id,"name","members",x,y]).
umlfInterface(cdf_3,'i1','+operation(param)',173,130).
umlfInterface(cdf_5,'i2','+operation(param)',835,191).

table(umlfAssociation,[to,target,dashed,anchor,source,from,type]).
umlfAssociation(cds_2,gen,false,true,none,cds_1,standard).
umlfAssociation(cdf_3,gen,true,true,none,cds_1,standard).
umlfAssociation(cds_2,gen,false,true,none,cds_4,standard).
umlfAssociation(cds_1,none,false,false,none,cds_4,standard).
umlfAssociation(cdf_5,gen,true,true,none,cds_4,standard).

