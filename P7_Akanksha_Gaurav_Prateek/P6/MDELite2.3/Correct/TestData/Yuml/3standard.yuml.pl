table(yumlClass,[id,"name","fields","methods"]).
yumlClass(c1,'Customer','','').
yumlClass(c0,'User','+Forename+;Surname;+HashedPassword;-Salt','+Login();+Logout()').
yumlClass(c4,'Root','','').
yumlClass(c2,'Order','','').
yumlClass(c3,'LineItem','','').

table(yumlInterface,[id,"name","methods"]).
:- dynamic yumlInterface/3.

table(yumlAssociation,["name1","role1",end1,"name2","role2",end2]).
yumlAssociation('User','','^','Customer','','').
yumlAssociation('Customer','','<>','Order','orders*','>').
yumlAssociation('Order','','++','LineItem','0..*','>').
yumlAssociation('Order','','','Root','','').

