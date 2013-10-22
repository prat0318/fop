/** file   discontiguous.pl -- eliminates aggrevating warnings 
*   about tuples of a table not being listed sequentially **/

:- discontiguous table/2.

:- style_check(-discontiguous).
:- style_check(-singleton).

table(node_violet,[id,name,nodeType,x,y]).
node_violet(statenode0,j1,state,427,279).
node_violet(statenode1,j2 ,state,439,440).
node_violet(statenode2,j1,state,661,366).
node_violet(statenode3,j3 ,state,664,275).

table(transition,[transid,startsAt,endsAt]).
transition(statenode0_statenode1,statenode0,statenode1).
transition(statenode1_statenode2,statenode1,statenode2).
transition(statenode2_statenode0,statenode2,statenode0).
transition(statenode0_statenode3,statenode0,statenode3).
transition(statenode3_statenode2,statenode3,statenode2).
transition(statenode1_statenode3,statenode1,statenode3).
/* file conform.pl */


/* conformance  utility predicates */

/* isError(S,N):- tell(user_error),write(S),writeln(N),told.*/

isError(S,N):- write(S),writeln(N).

/* the following rule/incantation I owe to Jan Wielemaker -- I never
   would have figured this out myself. this is used in comparing
   two prolog atoms (I1,I2), such as @I1 @> @I2.  */

:-op(200,fy,@).

node(Id,Name,NodeType) :- node_violet(Id,Name,NodeType,_,_).

% PROGRAMMING ASSIGNMENT - 2

%% This is to suppress the singleton variables warning.
:- style_check(-singleton).

% Constraint - 1.
% A node Id and a transition Id should never be null.
primary_key_null :- node(null,_,_),writeln('* A primary key in the node table is null.').
primary_key_null :- transition(null,_,_),writeln('* A primary key in the transition table is null.').

% Constraint - 2.
% A nodeId and transitionId should always be unique.
primary_key_not_unique :- node(X,_,_),aggregate_all(count, node(X,_,_), COUNT), COUNT \= 1,write('* Primary key is not unique in the node table. We are repeating: '),writeln(X).
primary_key_not_unique :- transition(X,_,_),aggregate_all(count, transition(X,_,_), COUNT), COUNT \= 1,write('* Primary key is not unique in the transition table. We are repeating: '), writeln(X).

% Constraint - 3.
% A node must always be of either start, state or stop type.
node_type_not_identified :- node(X,_,Type),not(member(Type,[start,state,stop])),write('* Node: "'),write(X),writeln('"s type is not one among [start, state, stop].').

% Constraint - 4.
% Each startsAt and endsAt is a valid node Id.
transition_type_not_identified :- transition(X,Node1,_),not(node(Node1,_,_)),write('* Transition:- '),write(X),write(' does not start from a valid node.'), write(Node1), writeln('.').
transition_type_not_identified :- transition(X,_,Node2),not(node(Node2,_,_)),write('* Transition:- '),write(X),write(' does not end at a valid node: '), write(Node2), writeln('.').

% Constraint - 5.
% You cannot go to any other state from the 'stop' node. However, a transition from stop to stop is allowed.
transition_starting_from_stop :- transition(T,X,Y),not(node(Y,_,stop)),node(X,_,stop),write('* Transition:- '),write(T),writeln(' is starting from a node of type stop.').

% Constraint - 6.
% There should be atleast one start and stop node.
not_enough_endnodes :- not(node(_,_,start)), writeln('* There is no start node.').
not_enough_endnodes :- not(node(_,_,stop)), writeln('* There is no stop node.').

% Constraint - 7.
% There should be atMax one start and stop node. I have created these two rules, just for better error reporting. Ideally Constraint 6 and 7 can be clubbed together.
multiple_start_nodes :- aggregate_all(count, node(_,_,start), COUNT), COUNT > 1, writeln('* We have multiple start nodes.').
multiple_end_nodes :- aggregate_all(count, node(_,_,stop), COUNT), COUNT > 1, writeln('* We have multiple end nodes.').

% Constraint - 8.
% We should check that each node in the graph should be reachable from start and stop node both.
check_reachable(CN, Processed, X) :- transition(_,CN,Next), (Next==X -> true; member(Next, Processed) -> false ; append(Processed, [Next], NewList), check_reachable(Next, NewList ,X)).

not_reachable_from_start(Start) :- node(X,_,T), not(member(T,[start,stop])), not(check_reachable(Start, [Start], X)), write('* Node: '), write(X), writeln(' is not reachable from the start.').
not_reachable_from_stop(Stop) :- node(X,_,T), not(member(T,[start,stop])), not(check_reachable(X, [X], Stop)), write('* Node: '), write(X), writeln(' is not reachable from the stop.').

not_reachable_node :- aggregate_all(count, node(_,_,start), COUNT), COUNT == 1, node(X,_,start), not_reachable_from_start(X).
not_reachable_node :- aggregate_all(count, node(_,_,stop), COUNT), COUNT == 1, node(X,_,stop), not_reachable_from_stop(X).

% Final constraint to check verify all the constraints.
run :- not(primary_key_null), not(primary_key_not_unique), not(node_type_not_identified), not(transition_type_not_identified), not(transition_starting_from_stop), not(not_enough_endnodes), not(multiple_start_nodes), not(multiple_end_nodes).

list_invalid_constraints :- primary_key_null, nb_setval(invalid_constraint, true), false.
list_invalid_constraints :- primary_key_not_unique, nb_setval(invalid_constraint, true), false.
list_invalid_constraints :- node_type_not_identified, nb_setval(invalid_constraint, true), false.
list_invalid_constraints :- transition_type_not_identified, nb_setval(invalid_constraint, true), false.
list_invalid_constraints :- transition_starting_from_stop, nb_setval(invalid_constraint, true), false.
list_invalid_constraints :- not_enough_endnodes, nb_setval(invalid_constraint, true), false.
list_invalid_constraints :- multiple_start_nodes, nb_setval(invalid_constraint, true), false.
list_invalid_constraints :- multiple_end_nodes, nb_setval(invalid_constraint, true), false.
list_invalid_constraints :- not_reachable_node, nb_setval(invalid_constraint, true), false.
list_invalid_constraints :- catch(nb_getval(invalid_constraint, X), E, false).
list_invalid_constraints :- writeln('No invalid constraints found.').
