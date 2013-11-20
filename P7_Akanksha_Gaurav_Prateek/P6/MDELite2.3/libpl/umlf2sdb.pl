/* file: umlf2sdb.pl */

:-style_check(-discontiguous).

/*********** translation of umlfSimpleClass to class tuples *************/
class(I,N,'','',S):-umlfSimpleClass(I,N,_,_),umlfAssociation(S,gen,_,_,_,I,_).
class(I,N,'','',null):-umlfSimpleClass(I,N,_,_),not(umlfAssociation(_,gen,_,_,_,I,_)). 
position(I,X,Y):-umlfSimpleClass(I,_,X,Y).


/*********** translation of umlfClass to class tuples *************/
class(I,N,'',M,S):-umlfClass(I,N,M,_,_),umlfAssociation(S,gen,_,_,none,I,_),umlfClass(S,_,_,_,_).
class(I,N,'',M,null):-umlfClass(I,N,M,_,_),not(umlfAssociation(_,gen,_,_,_,I,_)).
position(I,X,Y):-umlfClass(I,_,_,X,Y).



/*********** translation of umlfInterface to interface tuples *************/
interface(I,N,M):-umlfInterface(I,N,M,_,_).
position(I,X,Y):-umlfInterface(I,_,_,X,Y).

/*********** translation to umlfAssociation to association, classImplements, and interfaceExtends tuples *************/
xlate('composition','comp').
xlate('aggregation','agg').
xlate('none','none').
association(I1,'',A1,I2,'',A2):-umlfAssociation(I1,Tar,_,_,Src,I2,_),xlate(Tar,A1),xlate(Src,A2).
classImplements(C,I):-umlfAssociation(I,gen,_,_,none,C,_),class(C,_,_,_,_),interface(I,_,_).
interfaceExtends(C,P):-umlfAssociation(P,gen,_,_,none,C,_),interface(C,_,_),interface(P,_,_).

