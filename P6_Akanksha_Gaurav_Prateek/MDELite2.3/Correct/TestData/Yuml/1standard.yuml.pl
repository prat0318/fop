table(yumlClass,[id,"name","fields","methods"]).
yumlClass(c1,'Customer','','').
yumlClass(c4,'Address','','').
yumlClass(c3,'Billing Address','','').
yumlClass(c2,'Order','','').
yumlClass(c6,'Location','','').
yumlClass(c0,'Batory','','').
yumlClass(c5,'Company','','').

table(yumlInterface,[id,"name","methods"]).
:- dynamic yumlInterface/3.

table(yumlAssociation,["name1","role1",end1,"name2","role2",end2]).
yumlAssociation('Customer','1','<>','Order','*','>').
yumlAssociation('Customer','','','Billing Address','','>').
yumlAssociation('Customer','1','','Address','0..*','').
yumlAssociation('Order','','','Address','billing ','>').
yumlAssociation('Order','','','Address','shipping ','>').
yumlAssociation('Company','','<>','Location','1','>').

