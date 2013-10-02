:- style_check(-singleton).

% Tables being used
dbase(biparteGraph, [unode, vnode, edges]).

% Tables Definations 
table(unode, [nodeid]).
table(vnode, [nodeid]).
tables(edges, [unode, vnode]). 

% Testing on data 
unode(1).
unode(2).
unode(3).
unode(4).
unode(5).

vnode(6).
vnode(7).
vnode(8).
vnode(9).

edges(1, 6).
edges(2, 7).
edges(2, 6).
edges(3, 8).
edges(3, 9).
edges(4, 7).
edges(5, 6).
edges(5, 8).
edges(5, 9).


% Constraints being defined for the Tables

% PART(a) : Unode and Vnode should form a disjoint set

% 1: Node's Id should not be null 
nullId:- unode(null).
nullId:- vnode(null).

% 2 Unique ID: Every Node being defined should have a unique Id. First we create a single table Node of all the Unodes and Vnodes. Then check that the Id's are unique in the Node table. 
node(N):- unode(N).
node(N):- vnode(N).
notUniqueId:- node(A), aggregate_all(count, node(A), COUNT), COUNT \= 1.

% 3 Check for a bipartite graph: When an edge is defined between two nodes, then one of the coordinate should belong to the Unode and other end should belong to Vnode. 
notCorrectEdge:- edges(A,_), not(unode(A)).
notCorrectEdge:- edges(_,B), not(vnode(B)). 



% PART (b) : There are no Cycle of Odd length 

% IMPLEMENTATION DETAILS
% a.	Constraint: If we are able to find odd length cycle then the graph being defined is incorrect. 
% b.	Cycle check needed only for one set of Nodes: If a cycle exists then it will have at least one node either in u or in v. Therefore if we are able to find all the cycles in the nodes of u, we can be sure that all the cycles present in the graph have been detected. 
% c.	Find Cycles first : In order to calculate the length of the cycle, we first try to find the cycles in the graph. In the process we will store the nodes we have traversed in a list. 
% d.	Length of cycle is equal to no of nodes traversed: Then the no of edges being traversed is equivalent to the no of nodes present in the list. So we can make sure that the cycle length is not odd by just checking the no of nodes traversed in the path. 
% e.	Recursive Function : We start off with a unode and add it to the list of nodes that have been traversed. Then whenever a node is traversed, we add that to the list of visited nodes. 
% g.	Detection of a cycle: We declare that a cycle has been detected if the node we will now be visiting is the node we started our traversal with.

oddLengthCycle:- unode(A), cycle(A,Visited).

cycle(X, Visited):- cycleh(X,[X]).                                             
cycleh(X,Visited):- edges(X,Y), edges(Z,Y), not(X == Z), not(member(Y,Visited)), (nth0(0,Visited,Z) -> append(Visited,[Y],N), isOddCycle(N), false ; append(Visited, [Y, Z], R)    , cycleh(Z, R)).

% In this section we check for the length of the cycle which has been found to be odd. We are also printing the nodes which have been found in the cylce at this point to check that only the cycles are being checked for length.... 
isOddCycle(N):- odd(length(N,Z)), write(N), writeln(' is an odd cycle.').
odd(N):- mod(N,2) == 1.

% Print function defined to print the nodes that have been found to be a part of the cycle...
% print([X|T]):- write(X), write(' ,'), print(T).
% print([X]) :-    !, writeln(' ').


% the Below sample of detecing a cycle is incorrect because in this cycles get detected in the path of the traversal rather than making sure that the cycle exists from the node we have started. Therefore in the actual implementation we are just checking for the existence of the cycle from the head of the Nodes we started traversing.
%cycle(X, Visited):- cycleh(X,[X]).
%cycleh(X,Visited):- edges(X,Y), edges(Z,Y), not(X == Z), not(member(Y,Visited)), (member(Z, Visited) -> append(Visited,[Y],N), isOddCycle(N), false ; append(Visited, [Y, Z], R), cycleh(Z, R)).

% Final Evaluation of the constraints that have been defined..
checkDataValidity:- not(nullId), not(notUniqueId), not(notCorrectEdge), not(oddLengthCycle), writeln('Data in the bipartite graph is valid'). 

% -- DEFINE tables and tuples to comply with print.pl -- %
tuple(unode,L) :- unode(I), L=[I].
tuple(vnode,L) :- vnode(I), L=[I].
tuple(edges,L) :- edges(U,V), L=[U,V].

tableAlias(unode, unode).
tableAlias(vnode, vnode).
tableAlias(edges, edges).

% -- DUMP print.pl taken from MODELite/libpl/ -- %
% ---------------------------------------------- %
comma :- write(',').
newtab:- write('(').
endtab:- writeln(').').
quote:- write('\'').
dquote:- write('\"').  /* " */
newline:- writeln(''). 
disc:-writeln(':-style_check(-discontiguous).'),writeln('').

/* rules for printing a database -- to file F else to standard out */

printDbase(D):- disc,dbase(D,L),printTables(L).
printDbase(D,F):- tell(F),printDbase(D),told.


/* rules for printing a table definition */
printTableDecl(T) :- table(T,Y),write('table('),writeAlias(T),comma,write('['),printList(Y),write(']'),endtab.

printList([X|[]]):-writeT(X).
printList([X|Y]):-writeT(X),comma,printList(Y).
writeT(X):-atom(X),write(X).
writeT(X):-dquote,atom_chars(Y,X),write(Y),dquote.
writeAlias(T):-tableAlias(T,X),write(X).
writeAlias(T):-write(T).

/* rules for printing tables: its definition and all of its tuples */

printTables([Y|X]):-printTable(Y),printTables(X).
printTables([]).
printTable(S):-tuple(S,_),table(S,D),printTableDecl(S),forall(tuple(S,L),printTuple(S,D,L)),newline.
printTable(S):-not(tuple(S,_)),table(S,D),printTableDecl(S),writedynamic(S,D),newline.
writedynamic(S,D):-count(D,L),write(':- dynamic '),write(S),write('/'),write(L),writeln('.').
count([],0).
count([_|T],N):-count(T,M),N is M+1.

/* rules for printing a tuple */
printTuple(S,D,L):-writeAlias(S),newtab,printT(D,L),endtab.
printT([Z|[]],[X|[]]):-atom(Z),write(X).
printT([_|[]],[X|[]]):-quote,write(X),quote.

printT([Z|W],[X|Y]):-atom(Z),write(X),comma,printT(W,Y).
printT([_|W],[X|Y]):-quote,write(X),quote,comma,printT(W,Y).

/*************** End PRINT ********************************/

