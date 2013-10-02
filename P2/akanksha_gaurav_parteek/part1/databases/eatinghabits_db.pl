/* the discontiguous declaration says rows of "table" are not consecutive */
:- discontiguous table/2.

dbase(fsm,[node,transition]).

table(node,[nodeid,name,type]).
node(nstart, start, start).
node(nReady, ready, state).
node(nDrink, drink, state).
node(nEat, eat, state).
node(nFam, family, state).
node(nstop, stop, stop).
%%node(null, stop, stop).
%%node(nstop, stop, stop).
%%node(nstats, stopf, stopaa).

table(transition,[transid,startsAt,endsAt]).
transition(t1, nstart, nReady).
transition(t2, nReady, nDrink).
transition(t3, nReady, nEat).
transition(t4, nDrink, nDrink).
transition(t5, nEat, nEat).
transition(t6, nDrink, nEat).
transition(t7, nEat, nDrink).
transition(t8, nDrink, nFam).
transition(t9, nEat, nFam).
transition(t10, nFam, nstop).
%%transition(t10, nFam, nstop).
%%transition(null, nFam, nstop).
%%transition(t11, nstop, nstop).
%%transition(t12, nFam, nstopa).
%%transition(t13, nFamaa, nstop).

