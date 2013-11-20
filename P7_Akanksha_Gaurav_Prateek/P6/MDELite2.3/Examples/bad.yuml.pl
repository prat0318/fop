table(yumlClass,[id,"name","fields","methods"]).
yumlClass(c0,'abc','','').

table(yumlInterface,[id,"name","methods"]).
:- dynamic yumlInterface/3.

table(yumlAssociation,["name1","role1",end1,"name2","role2",end2]).
yumlAssociation('abc','','^','abc','','^').

