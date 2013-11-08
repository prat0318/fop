node(Id,Name,NodeType) :- node_violet(Id,Name,NodeType,_,_).

% PROGRAMMING ASSIGNMENT - 2

%% This is to suppress the singleton variables warning.
:- style_check(-singleton).

% Constraint - 1.
% A node Id and a transition Id should never be null.
primary_key_null :- node_violet(null,_,_,_,_),writeln('* A primary key in the node table is null.').
primary_key_null :- transition(null,_,_,_,_,_),writeln('* A primary key in the transition table is null.').

% Constraint - 2.
% A nodeId and transitionId should always be unique.
primary_key_not_unique :- node_violet(X,_,_,_,_),aggregate_all(count, node_violet(X,_,_,_,_), COUNT), COUNT \= 1,write('* Primary key is not unique in the node table. We are repeating: '),writeln(X).
primary_key_not_unique :- transition(X,_,_,_,_,_),aggregate_all(count, transition(X,_,_,_,_,_), COUNT), COUNT \= 1,write('* Primary key is not unique in the transition table. We are repeating: '), writeln(X).

% Constraint - 4.
% Each startsAt and endsAt is a valid node Id.
transition_type_not_identified :- transition(X,Node1,_,_,_,_),not(node_violet(Node1,_,_,_,_)),write('* Transition:- '),write(X),write(' does not start from a valid node.'), write(Node1), writeln('.').
transition_type_not_identified :- transition(X,_,Node2,_,_,_),not(node_violet(Node2,_,_,_,_)),write('* Transition:- '),write(X),write(' does not end at a valid node: '), write(Node2), writeln('.').

% Final constraint to check verify all the constraints.
run :- not(primary_key_null), not(primary_key_not_unique), not(transition_type_not_identified).
