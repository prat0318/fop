:-style_check(-discontiguous).

table(umlfSimpleClass,[id,"name",x,y]).
umlfSimpleClass(cdl_3,'c1',138,221).
umlfSimpleClass(cdl_4,'c2',488,206).

table(umlfClass,[id,"name","members",x,y]).
:- dynamic umlfClass/5.

table(umlfInterface,[id,"name","members",x,y]).
umlfInterface(cdf_1,'i1','+a();+b()',112,56).
umlfInterface(cdf_2,'i2','+c(x,y)',495,55).

table(umlfAssociation,[to,target,dashed,anchor,source,from,type]).
umlfAssociation(cdf_1,gen,true,true,none,cdf_2,standard).
umlfAssociation(cdf_1,gen,true,true,none,cdl_3,standard).
umlfAssociation(cdf_2,gen,true,true,none,cdl_4,standard).

