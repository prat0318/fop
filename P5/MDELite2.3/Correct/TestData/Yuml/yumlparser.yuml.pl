table(yumlClass,[id,"name","fields","methods"]).
yumlClass(c2,'Main','','_Main();_checkNameStructure(String);_err(String);_main(String#);_printArray(String String#);').
yumlClass(c0,'connection','end1;end2;name1;name2;role1;role2;comma;quote;connectionS;','_connection(String String String String String String);_dump();').
yumlClass(c1,'klass','fields;id;methods;name;','_klass(String String String);_klass(String#);_toId(String);_dump();').

table(yumlInterface,[id,"name","methods"]).
:- dynamic yumlInterface/3.

table(yumlAssociation,["name1","role1",end1,"name2","role2",end2]).
:- dynamic yumlAssociation/6.

