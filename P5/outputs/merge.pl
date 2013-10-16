:- style_check(-discontiguous).

table(node_violet,[id,name,nodeType,x,y]).
node_violet(circularinitialstatenode0,circularinitialstatenode0,start,292,169).
node_violet(statenode0,q1,state,506,145).
node_violet(statenode1,q2,state,263,294).
node_violet(circularfinalstatenode0,circularfinalstatenode0,stop,541,313).

table(transition,[transid,startsAt,endsAt]).
transition(circularinitialstatenode0_statenode0,circularinitialstatenode0,statenode0).
transition(statenode0_circularinitialstatenode0,statenode0,circularinitialstatenode0).
transition(circularinitialstatenode0_statenode1,circularinitialstatenode0,statenode1).
transition(statenode1_circularinitialstatenode0,statenode1,circularinitialstatenode0).
transition(statenode1_statenode1,statenode1,statenode1).
transition(statenode0_circularfinalstatenode0,statenode0,circularfinalstatenode0).
transition(statenode1_circularfinalstatenode0,statenode1,circularfinalstatenode0).
% Model to model conversion.
:- discontiguous table/2.

dbase(fsm, [node]).

node(Id,Name,NodeType) :- node_violet(Id,Name,NodeType,_,_).

tableAlias(node, node).

% -- DEFINE tables and tuples to comply with print.pl -- %
table(node, [id, "name", "nodeType"]).

tuple(node,L) :- node(I,N,T), L=[I,N,T].

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
