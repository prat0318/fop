/* the discontiguous declaration says rows of "table" are not consecutive */
:- discontiguous table/2.

dbase(fsm,[node,transition]).

table(node,[nodeid,name,type]).
node(nstart,start,start).
node(nloop,loop,state).
node(nstop,stop,stop).

table(transition,[transid,startsAt,endsAt]).
transition(t1,nstart,nloop).
transition(t2,nloop,nloop).
transition(t3,nloop,nstop).
transition(t4,nstop,nstart).
