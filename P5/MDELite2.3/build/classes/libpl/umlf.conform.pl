/* file umlf.conform.pl */

/* uniqueNames CONSTRAINT: Class, SimpleClass, and Interfaces have unique names constraint */

findPair(I1,I2,N):-ename(I1,N),ename(I2,N),@I1 @< @I2.
uniqueNames:-forall(findPair(_,_,N),isError('unique names constraint violated: ',N)).


/* circular CONSTRAINT: Check circularity of class Hierarchy */

/*  utility predicates */
isClass(I,N):-umlfSimpleClass(I,N,_,_).
isClass(I,N):-umlfClass(I,N,_,_,_).
isIntf(I,N):-umlfInterface(I,N,_,_,_).
ename(I,N):-isIntf(I,N);isClass(I,N).

subClass(Sub,Sup):-umlfAssociation(Sup,'gen',_,_,'none',Sub,_),isClass(Sup,_),isClass(Sub,_).
subClass(Sub,Sup):-umlfAssociation(Sub,'none',_,_,'gen',Sup,_),isClass(Sup,_),isClass(Sub,_).
subOf(Sub,Sup):-subClass(Sub,Sup).
subOf(Sub,Sup):-subClass(Sub,X),subOf(X,Sup).

testCycle(I,N):-subOf(I,I),isError('circular class hierarchy: ',N).
testCycle(_,_).

circular:-forall(isClass(I,N),testCycle(I,N)).

/* icircular CONSTRAINT: Check circularity of inheritance Hierarchy  */

subIntf(Sub,Sup):-umlfAssociation(Sup,'gen',_,_,'none',Sub,standard),isIntf(Sup,_),isIntf(Sub,_).
subIntf(Sub,Sup):-umlfAssociation(Sub,'none',_,_,'gen',Sup,standard),isIntf(Sup,_),isIntf(Sub,_).
isubOf(Sub,Sup):-subIntf(Sub,Sup).
isubOf(Sub,Sup):-subIntf(Sub,X),isubOf(X,Sup).

testICycle(I,N):-isubOf(I,I),isError('circular interface hierarchy: ',N).
testICycle(_,_).

icircular:-forall(isIntf(I,N),testICycle(I,N)).

/* ALL CONSTRAINTS */

run:-uniqueNames,circular,icircular.
