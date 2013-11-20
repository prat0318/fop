table(yumlClass,[id,"name","fields","methods"]).
yumlClass(c0,'I','','').

table(yumlInterface,[id,"name","methods"]).
:- dynamic yumlInterface/3.

table(yumlAssociation,["name1","role1",end1,"name2","role2",end2]).
:- dynamic yumlAssociation/6.

