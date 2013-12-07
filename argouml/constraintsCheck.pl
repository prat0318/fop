/* isError(S,N):- tell(user_error),write(S),writeln(N),told.*/
  
isError(S,N):- write(S),writeln(N).
  
findPair(I1,I2,N):-ename(I1,N),ename(I2,N),@I1 @< @I2.
uniqueNames:-forall(findPair(_,_,N),isError('unique names constraint violated: ',N)).

/* the following rule/incantation I owe to Jan Wielemaker -- I never
*    would have figured this out myself. this is used in comparing
*       two prolog atoms (I1,I2), such as @I1 @> @I2.  */

:-op(200,fy,@).

/* circular CONSTRAINT: Check circularity of class Hierarchy */

/*  utility predicates */
isClass(I,N):-class(I,N,_,_).
isIntf(I,N):-interface(I,N,_,_).
ename(I,N):-isIntf(I,N);isClass(I,N).


subClass(Sub,Sup):-class(Sub,_,_,Sup),isClass(Sup,_),isClass(Sub,_).
subOf(Sub,Sup):-subClass(Sub,Sup).
subOf(Sub,Sup):-subClass(Sub,X),subOf(X,Sup).

testCycle(I,N):-subOf(I,I),isError('circular class hierarchy: ',N).
testCycle(_,_).

circular:-forall(isClass(I,N),testCycle(I,N)).

/* icircular CONSTRAINT: Check circularity of inheritance Hierarchy  */

subIntf(Sub,Sup):-interface(Sub,_,_,Sup),isIntf(Sup,_),isIntf(Sub,_).
isubOf(Sub,Sup):-subIntf(Sub,Sup).
isubOf(Sub,Sup):-subIntf(Sub,X),isubOf(X,Sup).

testICycle(I,N):-isubOf(I,I),isError('circular interface hierarchy: ',N).
testICycle(_,_).

icircular:-forall(isIntf(I,N),testICycle(I,N)).

/* legalMultiplicity CONSTRAINT: Check the legality of the multiplicity */

noMult('').

isTriangle('TRIANGLE').

%(\+ noMult(Mult1);\+ noMult(Mult2)),
testMultiplicity(I,N):- association(I,Mult1,Arrow1,_,_,Mult2,Arrow2,_,_),
                            (not(noMult(Mult1));not(noMult(Mult2))),
                                (isTriangle(Arrow1);isTriangle(Arrow2)), 
                                    isError('illegal multiplicity: ',N).
testMultiplicity(_,_).

legalMultiplicity :- forall(isClass(I,N),testMultiplicity(I,N)).
ilegalMultiplicity :- forall(isIntf(I,N),testMultiplicity(I,N)).


/* ALL CONSTRAINTS */
run:-uniqueNames,circular,icircular,
            legalMultiplicity,ilegalMultiplicity.
