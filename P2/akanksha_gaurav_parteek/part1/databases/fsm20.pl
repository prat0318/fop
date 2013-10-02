/* the discontiguous declaration says rows of "table" are not consecutive */
:- discontiguous table/2.

dbase(fsm,[node,transition]).

table(node,[nodeid,name,type]).
node(start,start,start).
node(n1,n1,state).
node(n2,n2,state).
node(n3,n3,state).
node(n4,n4,state).
node(stop,stop,stop).

table(transition,[transid,startsAt,endsAt]).
transition(t1,start,n1).
transition(t2,start,n3).
transition(t3,n3,n4).
transition(t4,n1,n4).
transition(t5,n1,n2).
transition(t6,n2,stop).
transition(t7,n4,stop).
