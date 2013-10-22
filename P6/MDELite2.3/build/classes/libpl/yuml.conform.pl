/* file yuml.conform.pl */



/* uniqueNames CONSTRAINT: Classes and Interfaces have unique names constraint */

findPair(I1,I2,N):-ename(I1,N),ename(I2,N),@I1 @< @I2.
uniqueNames:-forall(findPair(_,_,N),isError('unique names constraint violated: ',N)).


/* circular CONSTRAINT: Check circularity of class Hierarchy */

/*  utility predicates */
isClass(I,N):-yumlClass(I,N,_,_).
isIntf(I,N):-yumlInterface(I,N,_).
ename(I,N):-isIntf(I,N);isClass(I,N).

subclass(Sub,Sup):-yumlAssociation(Sp,_,'^',Sb,_,_),isClass(Sub,Sb),isClass(Sup,Sp).
subclass(Sub,Sup):-yumlAssociation(Sb,_,_,Sp,_,'^'),isClass(Sub,Sb),isClass(Sup,Sp).

subOf(Sub,Sup):-subclass(Sub,Sup).
subOf(Sub,Sup):-subclass(Sub,X),subOf(X,Sup).

testCycle(I,N):-subOf(I,I),isError('circular class hierarchy: ',N).
testCycle(_,_).

circular:-forall(isClass(I,N),testCycle(I,N)).

/* icircular CONSTRAINT: Check circularity of inheritance Hierarchy  */

isubOfBase(Sub,Sup):-yumlAssociation(N1,_,'^',N2,_,''),isIntf(N1,Sup),isIntf(N2,Sub).
isubOf(Sub,Sup):-isubOfBase(Sub,X),isubOf(X,Sup).

testICycle(I,N):-isubOf(I,I),isError('circular interface hierarchy: ',N).
testICycle(_,_).

icircular:-forall(isIntf(I,N),testICycle(I,N)).

/* ALL CONSTRAINTS */

run:-uniqueNames,circular,icircular.