table(yumlClass,[id,"name","fields","methods"]).
yumlClass(c7,'NightlyBillingTask','','').
yumlClass(c4,'Salaried','','').
yumlClass(c5,'Contractor','','').
yumlClass(c3,'Wages','','').
yumlClass(c0,'Location','','').
yumlClass(c2,'Company','','').
yumlClass(c1,'Point','','').

table(yumlInterface,[id,"name","methods"]).
yumlInterface(c8,'Session','').
yumlInterface(c6,'ITask','').

table(yumlAssociation,["name1","role1",end1,"name2","role2",end2]).
yumlAssociation('Location','','<>','Point','','>').
yumlAssociation('Company','','++','Location','1','>').
yumlAssociation('Wages','','^','Salaried','','').
yumlAssociation('Wages','','^','Contractor','','').
yumlAssociation('ITask','','^','NightlyBillingTask','','').

