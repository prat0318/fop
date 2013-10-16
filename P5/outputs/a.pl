:- style_check(-discontiguous).

table(node_violet,[id,name,nodeType,x,y]).
node_violet(circularinitialstatenode0,circularinitialstatenode0,start,172,262).
node_violet(circularfinalstatenode0,circularfinalstatenode0,stop,873,270).
node_violet(statenode0,dogAwake,state,264,247).
node_violet(statenode1,dogHungry,state,446,145).
node_violet(statenode2,dogBowBow,state,447,301).
node_violet(statenode3,donPats,state,615,246).
node_violet(statenode4,dogHappy,state,755,243).

table(transition,[transid,startsAt,endsAt]).
transition(circularinitialstatenode0_statenode0,circularinitialstatenode0,statenode0).
transition(statenode0_statenode1,statenode0,statenode1).
transition(statenode0_statenode2,statenode0,statenode2).
transition(statenode1_statenode2,statenode1,statenode2).
transition(statenode2_statenode1,statenode2,statenode1).
transition(statenode1_statenode3,statenode1,statenode3).
transition(statenode2_statenode3,statenode2,statenode3).
transition(statenode3_statenode4,statenode3,statenode4).
transition(statenode4_circularfinalstatenode0,statenode4,circularfinalstatenode0).
