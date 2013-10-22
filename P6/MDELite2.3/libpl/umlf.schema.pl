/*  file umlf.schema.pl -- UMLF Schema Declaration */

dbase(umlf,[umlfSimpleClass,umlfClass,umlfInterface,umlfAssociation]).

table(umlfSimpleClass,[id,"name",x,y]).
table(umlfClass,[id,"name","members",x,y]).
table(umlfInterface,[id,"name","members",x,y]).
table(umlfAssociation,[to,target,dashed,anchor,source,from,type]).

tuple(umlfSimpleClass,L):-umlfSimpleClass(I,N,X,Y),L=[I,N,X,Y].
tuple(umlfClass,L):-umlfClass(I,N,M,X,Y),L=[I,N,M,X,Y].
tuple(umlfInterface,L):-umlfInterface(I,N,M,X,Y),L=[I,N,M,X,Y].
tuple(umlfAssociation,L):-umlfAssociation(To,Tar,Das,Anc,Src,Frm,Typ),
                          L=[To,Tar,Das,Anc,Src,Frm,Typ].

