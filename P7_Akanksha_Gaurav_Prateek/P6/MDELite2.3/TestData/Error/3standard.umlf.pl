:- style_check(-discontiguous).

%table(umlfSimpleClass,[id,"name",x,y]).
umlfSimpleClass(c1,'Customer',0,0).
umlfSimpleClass(c4,'Root',0,0).
umlfSimpleClass(c2,'Order',0,0).
umlfSimpleClass(c3,'LineItem',0,0).

%table(umlfClass,[id,"name","members",x,y]).
umlfClass(c0,'User','+Forename+;Surname;+HashedPassword;-Salt;+Login();+Logout()',0,0).

%table(umlfInterface,[id,"name","members",x,y]).
:- dynamic umlfInterface/5.

%table(umlfAssociation,[to,target,dashed,anchor,source,from,type]).
umlfAssociation(c2,none,false,false,aggregation,c1,standard).
umlfAssociation(c3,none,false,false,composition,c2,standard).
umlfAssociation(c2,none,false,false,none,c4,standard).
umlfAssociation(c0,gen,false,true,none,c1,standard).
umlfAssociation(c1,gen,false,true,none,c0,standard).

