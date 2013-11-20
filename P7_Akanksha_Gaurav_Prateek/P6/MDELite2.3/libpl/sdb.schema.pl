/* file sdb.schema.pl -- declares the SDB schema */

dbase(sdb,[class,association,interface,classImplements,interfaceExtends,position]).

table(class,[id,"name","fields","methods",superid]).
table(association,[cid1,"role1",arrow1,cid2,"role2",arrow2]).
table(interface,[id,"name","methods"]).
table(classImplements,[cid,iid]).
table(interfaceExtends,[id,idx]).
table(position,[id,x,y]).

tuple(class,L):-class(I,N,F,M,S),L=[I,N,F,M,S].
tuple(association,L):-association(C1,R1,A1,C2,R2,A2),L=[C1,R1,A1,C2,R2,A2].
tuple(interface,L):-interface(I,N,M),L=[I,N,M].
tuple(classImplements,L):-classImplements(C1,C2),L=[C1,C2].
tuple(interfaceExtends,L):-interfaceExtends(C1,C2),L=[C1,C2].
tuple(position,L):-position(I,X,Y),L=[I,X,Y].

