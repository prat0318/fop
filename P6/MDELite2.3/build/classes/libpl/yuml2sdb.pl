/* file: yuml2sdb.pl --- rules to translate a Yuml database to an SDB database */

% left arrow types (lat)
lat(arrow,'<').
lat(agg,'<>').
lat(none,'').
lat(comp,'++').

%right arrow types (rat)
rat(arrow,'>').
rat(agg,'<>').
rat(none,'').
rat(comp,'++').

%utility predicate classExtends
classExtends(C1,S):-yumlAssociation(C2,_,'^',C1,_,_),yumlClass(S,C2,_,_).
classExtends(C1,S):-yumlAssociation(C1,_,_,C2,_,'^'),yumlClass(S,C2,_,_).

class(I,N,F,M,S):-yumlClass(I,N,F,M),classExtends(N,S).
class(I,N,F,M,null):-yumlClass(I,N,F,M),not(classExtends(N,_)).

interface(I,N,M):-yumlInterface(I,N,M).

association(C1,R1,AA1,C2,R2,AA2):-yumlAssociation(N1,R1,A1,N2,R2,A2),not(A1=='^'),not(A2=='^'),
       yumlClass(C1,N1,_,_),yumlClass(C2,N2,_,_),lat(AA1,A1),rat(AA2,A2).

classImplements(C,I):-yumlAssociation(CN,_,'',IN,_,'^'),yumlClass(C,CN,_,_),yumlInterface(I,IN,_).
classImplements(C,I):-yumlAssociation(IN,_,'^',CN,_,''),yumlClass(C,CN,_,_),yumlInterface(I,IN,_).

interfaceExtends(I1,I2):-yumlAssociation(I1,_,'',I2,_,'^'),yumlInterface(I1,_,_),yumlInterface(I2,_,_).
interfaceExtends(I1,I2):-yumlAssociation(I2,_,'^',I1,_,''),yumlInterface(I1,_,_),yumlInterface(I2,_,_).

position(I,0,0):-yumlClass(I,_,_,_).
position(I,0,0):-yumlInterface(I,_,_).
