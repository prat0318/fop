/* the discontiguous declaration says rows of "table" are not consecutive */
:- discontiguous table/2.

dbase(fsm,[node,transition]).

table(node,[nodeid,name,type]).
node(node_start, start, start).
node(node_stop, stop, stop).
node(node_awake, awake, state).
node(node_hungry, hungry, state).
node(node_bow, bow, state).
node(node_pats, pat, state).
node(node_happy, happy, state).

table(transition,[transid,startsAt,endsAt]).
transition(t1, node_start, node_awake).
transition(t2, node_awake, node_hungry).
transition(t3, node_awake, node_bow).
transition(t4, node_bow, node_hungry).
transition(t5, node_hungry, node_bow).
transition(t6, node_bow, node_pats).
transition(t7, node_hungry, node_pats).
transition(t8, node_pats, node_happy).
transition(t9, node_happy, node_stop).
