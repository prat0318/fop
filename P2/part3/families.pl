% world of families and family members
:- discontiguous table/2.

dbase(families,[family,member]).
%ADD families2 db entry%
dbase(families2,[family2,member2]).

family(f1, march).
family(f2, sailor).

member(m1, jim,     f1,   null, null, null).
member(m2, cindy,   null, f1,   null, null).
member(m3, brandon, null, null, f1,   null).
member(m4, brenda,  null, null, null, f1  ).
member(m5, peter,   f2,   null, null, null).
member(m6, jackie,  null, f2,   null, null).
member(m7, david,   null, null, f2,   null).
member(m8, dylan,   null, null, f2,   null).
member(m9, kelly,   null, null, null, f2  ).

% -- MAIN M2M RULES START HERE -- %
% define family2 %
family2(FamilyId, LastName, Father, Mother) :- family(FamilyId, LastName), member(Father,_, FamilyId,null,null,null), member(Mother,_,null,FamilyId,null,null).

% define member2 %
member2(MemberId, FirstName, Fson, Fdaughter) :- member(MemberId, FirstName,_,_,Fson, Fdaughter).
% -- M2M RULES END HERE -- %

% ------------------------------------------------------------ %
% Purpose of below code is just to print tables neatly %
% Use tableAlias to print member instead of member2 when printing %
tableAlias(member2, member).
tableAlias(family2, family).

% -- DEFINE tables and tuples to comply with print.pl -- %
table(family,[id, "lastName"]).
table(member,[id, "firstName", "fatherOf", "motherOf", "sonOf", "daughterOf"]).
table(family2,[id, "lastName", "fatherMemberId", "motherMemberId"]).
table(member2,[id, "firstName", "sonOf", "daughterOf"]).

tuple(family,L) :- family(I,SN), L=[I,SN].
tuple(member,L) :- member(I,FN,F,M,S,D), L=[I,FN,F,M,S,D].
tuple(family2,L) :- family2(I,SN,F,M), L=[I,SN,F,M].
tuple(member2,L) :- member2(I,FN,S,D), L=[I,FN,S,D].

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
