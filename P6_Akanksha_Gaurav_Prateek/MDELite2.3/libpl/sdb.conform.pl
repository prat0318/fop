/* file sdb.conform.pl */

/* uniqueNames CONSTRAINT: Classes and Interfaces have unique names constraint */

findPair(I1,I2,N):-ename(I1,N),ename(I2,N),@I1 @< @I2.
uniqueNames:-forall(findPair(_,_,N),isError('unique names constraint violated: ',N)).


/* circular CONSTRAINT: Check circularity of class Hierarchy */

/*  utility predicates */
isClass(I,N):-class(I,N,_,_,_).
isIntf(I,N):-interface(I,N,_).
ename(I,N):-isIntf(I,N);isClass(I,N).

subclass(Sub,Sup):-class(Sub,_,_,_,Sup),not(Sup==none).
subOf(Sub,Sup):-subclass(Sub,Sup).
subOf(Sub,Sup):-subclass(Sub,X),subOf(X,Sup).

testCycle(I,N):-subOf(I,I),isError('circular class hierarchy: ',N).
testCycle(_,_).

circular:-forall(isClass(I,N),testCycle(I,N)).

/* icircular CONSTRAINT: Check circularity of inheritance Hierarchy  */

isubOf(Sub,Sup):-interfaceExtends(Sub,Sup).
isubOf(Sub,Sup):-interfaceExtends(Sub,X),isubOf(X,Sup).

testICycle(I,N):-isubOf(I,I),isError('circular interface hierarchy: ',N).
testICycle(_,_).

icircular:-forall(isIntf(I,N),testICycle(I,N)).

/* ALL CONSTRAINTS */

/* position CONSTRAINT: every class and interface has a position */

testPosition(I):-not(position(I,_,_)),isError('class has no position',I).
testPosition(_).

hasPosition:-forall(ename(I,_),testPosition(I)).

run:-uniqueNames,circular,icircular,hasPosition.
