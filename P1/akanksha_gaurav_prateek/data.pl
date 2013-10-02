dbase(fsm,[node,transition]).

table(node,[nodeid,name,type]).
node(nstart, start, start).
node(nReady, ready, state).
node(nDrink, drink, state).
node(nEat, eat, state).
node(nFam, family, state).
node(nstop, stop, stop).

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


table(entryStation, [entryStationId, stationId, isOperating]).
table(atm, [entryStationId, stationId, isOperating, cashOnHand, dispensed, consortiumId]).
table(cashierStation, [entryStationId, stationId, isOperating, branchId]).
table(consortium, [consortiumId]).
table(branch, [branchId, connected]).
table(user, [userId]).

% Sample Data:
entryStation(1, 'es1', 1).
entryStation(2, es2, 1).
entryStation(3, es3, 1).

atm(4, atm1, 1, 1, 1, 1).
atm(5, atm2, 1, 1, 1, 2).
atm(6, atm3, 1, 1, 1, 1).

cashierStation(7, cs1, 1, 1).
cashierStation(8, cs2, 1, 2).
cashierStation(9, cs3, 1, 1).

consortium(1).
consortium(1).
consortium(2).

branch(1, 1).
branch(2, 1).


