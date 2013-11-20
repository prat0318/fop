noElementNamedDon :- forall(element(I,'Don'),reportError(I)).
 
element(I,N):-violetClass(I,N,_,_,_,_,_).
element(I,N):-violetInterface(I,N,_,_,_).
 
reportError(I):-isError('class or interface named Don has identifier ',I).
 
run :- noElementNamedDon.