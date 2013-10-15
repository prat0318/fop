:-style_check(-discontiguous).

table(umlfSimpleClass,[id,"name",x,y]).
umlfSimpleClass(cdl_5,'c5',476,335).
umlfSimpleClass(cdl_1,'c1',173,54).
umlfSimpleClass(cdl_2,'c2',473,53).
umlfSimpleClass(cdl_3,'c3',472,153).
umlfSimpleClass(cdl_4,'c4',474,250).

table(umlfClass,[id,"name","members",x,y]).
:- dynamic umlfClass/5.

table(umlfInterface,[id,"name","members",x,y]).
:- dynamic umlfInterface/5.

table(umlfAssociation,[to,target,dashed,anchor,source,from,type]).
umlfAssociation(cdl_1,none,false,false,none,cdl_2,standard).
umlfAssociation(cdl_1,none,false,false,aggregation,cdl_3,standard).
umlfAssociation(cdl_1,none,false,false,composition,cdl_4,standard).
umlfAssociation(cdl_1,gen,false,true,none,cdl_5,standard).

