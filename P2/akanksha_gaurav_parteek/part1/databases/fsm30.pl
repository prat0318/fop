/* the discontiguous declaration says rows of "table" are not consecutive */
:- discontiguous table/2.

dbase(fsm,[node,transition]).

table(transition,[transid,startsAt,endsAt]).
transition(t1,start,n1).
transition(t5,n1,n2).
transition(t2,start,n3).
transition(t4,n1,n4).
transition(t3,n3,n4).
transition(t6,n2,stop).
transition(t7,n4,stop).
transition(t3,n3,n5).


table(node,[nodeid,name,type]).
node(n3,j3,state).
node(nstart,start,start).
node(stop,stop,stop).
node(n1,j1,state).
node(n2,j2,state).
node(start,start,start).
node(n4,j4,state).
node(n6,j6,state).

