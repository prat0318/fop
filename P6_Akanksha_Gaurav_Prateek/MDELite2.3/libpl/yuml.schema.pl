/* file: yuml.schema.pl */

dbase(yuml,[yumlClass,yumlInterface,yumlAssociation]).

table(yumlClass,[id,"name","fields","methods"]).
table(yumlInterface,[id,"name","methods"]).
table(yumlAssociation,["name1","role1","end1","name2","role2","end2"]).

tuple(yumlClass,L):-yumlClass(I,N,F,M),L=[I,N,F,M].
tuple(yumlInterface,L):-yumlInterface(I,N,M),L=[I,N,M].
tuple(yumlAssociation,L):-yumlAssociation(N1,R1,E1,N2,R2,E2),L=[N1,R1,E1,N2,R2,E2].

