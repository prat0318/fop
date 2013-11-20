/* file scalePosition.pl -- these are the rules to scale the position table by a factor of 7 */

table(scaledPosition,[id,px,py]).
tuple(scaledPosition,L):-scaledPosition(A,B,C),L=[A,B,C].

scaledPosition(I,Px,Py) :- position(I,X,Y), Px is X*7, Py is Y*7.

tableAlias(scaledPosition,position).

run :- printTable(scaledPosition).