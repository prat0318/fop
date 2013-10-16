:- style_check(-discontiguous).

table(node_violet,[id,name,nodeType,x,y]).
node_violet(circularinitialstatenode0,circularinitialstatenode0,start,172,262).
node_violet(circularfinalstatenode0,circularfinalstatenode0,stop,873,270).
node_violet(statenode0,Dog Awake,state,264,247).
node_violet(statenode1,Dog Hungry,state,446,145).
node_violet(statenode2,Dog  Bow-Bow,state,447,301).
node_violet(statenode3,Don Pats,state,615,246).
node_violet(statenode4,Dog Happy,state,755,243).

table(transition,[transid,startsAt,endsAt]).
transition(circularinitialstatenode0->statenode0,circularinitialstatenode0,statenode0).
transition(statenode0->statenode1,statenode0,statenode1).
transition(statenode0->statenode2,statenode0,statenode2).
transition(statenode1->statenode2,statenode1,statenode2).
transition(statenode2->statenode1,statenode2,statenode1).
transition(statenode1->statenode3,statenode1,statenode3).
transition(statenode2->statenode3,statenode2,statenode3).
transition(statenode3->statenode4,statenode3,statenode4).
transition(statenode4->circularfinalstatenode0,statenode4,circularfinalstatenode0).
