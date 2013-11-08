/* file: sdb2yuml.pl */

/* below are the joins that simplify writing VM2T code */

% left arrow types (lat)
lat(arrow,'<').
lat(agg,'<>').
lat(none,'').
lat(comp,'++').
lat(gen,'^').
lat('','').

%right arrow types (rat)
rat(arrow,'>').
rat(agg,'<>').
rat(none,'').
rat(comp,'++').
rat(gen,'^').
rat('','').

yumlClass(I,N,F,M):-class(I,N,F,M,_).
yumlInterface(I,N,M):-interface(I,N,M).

yumlAssociation(N1,R1,A1,N2,R2,A2):-association(I1,R1,AA1,I2,R2,AA2),not(AA1=='inherit'),not(AA2=='inherit'),
        class(I1,N1,_,_,_),class(I2,N2,_,_,_),lat(AA1,A1),rat(AA2,A2).

yumlAssociation(IN,'','^',CN,'',''):-classImplements(C,I),class(C,CN,_,_,_),interface(I,IN,_).
yumlAssociation(N1,'','',N2,'','^'):-interfaceExtends(I1,I2),interface(I1,N1,_),interface(I2,N2,_).
yumlAssociation(N2,'','^',N1,'',''):-class(_,N1,_,_,S),class(S,N2,_,_,_).