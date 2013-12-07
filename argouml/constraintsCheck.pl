/** file   discontiguous.pl -- eliminates aggrevating warnings*   about tuples of a table not being listed sequentially **/

:- discontiguous table/1, composition/2, class/4, attribute/3, association/3.


/* uniqueNames CONSTRAINT: Class, and Interfaces have unique names constraint */

dbase(argo,[class,interface,association,composition,attribute]).

table(class,[id,"name","visibility",super_id]).
table(interface,[id,"name","visibility",super_id]).
table(attribute,[atr_id,"atr_name","atr_visibility","class_id"]).
table(association,[end,class_id,multiplicity]).

interface(i_1, abd, adc,iabd ).
class(class_1, shoppingCart, vk_public, class_2).
attribute(attr_1, subTotalMoney, vk_public).
attribute(attr_2, vatAmount, vk_public).
attribute(attr_3, totalMoeny, vk_public).

class(class_2, customer, vk_public, class_3).
attribute(attr_4, customerName, vk_public).
attribute(attr_5, emailAddress, vk_private).

class(class_3, creditCard, vk_public, class_1).
attribute(attr_6, issuer, vk_public).
attribute(attr_7, cardNumber, vk_private).

class(class_4, itermToPurchase, vk_public, class_1).
attribute(attr_8, quantity, vk_public).
attribute(attr_9, pricePerUnit, vk_public).

composition(assoc_1, customer_Cart).
association(assoc_end_1, class_1, "0..inf").
association(assoc_end_2, class_2, "1..1").

composition(assoc_2, cart_Item).
association(assoc_end_3, class_1, "1..1").
association(assoc_end_4, class_4, "1..inf").

composition(assoc_3, cart_Card).
association(assoc_end_5, class_1, "1..1").
association(assoc_end_6, class_3, "1..1").

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
/*
run:-uniqueNames,circular,icircular,
            legalMultiplicity,ilegalMultiplicity.
*/
run:-true.
%run:- write('__'),false.
