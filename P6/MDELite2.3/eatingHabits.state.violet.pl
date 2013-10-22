:- style_check(-discontiguous).
:- style_check(-singleton).

table(node_violet,[id,name,nodeType,x,y]).
node_violet(circularinitialstatenode0,circularinitialstatenode0,start,335,334).
node_violet(circularfinalstatenode0,circularfinalstatenode0,stop,970,311).
node_violet(statenode0,Ready ,state,472,306).
node_violet(statenode1,Drink,state,643,228).
node_violet(statenode2,Eat,state,646,374).
node_violet(statenode3,Pig,state,810,294).

table(transition,[transid,startsAt,endsAt]).
transition(circularinitialstatenode0_statenode0,circularinitialstatenode0,statenode0).
transition(statenode0_statenode1,statenode0,statenode1).
transition(statenode0_statenode2,statenode0,statenode2).
transition(statenode1_statenode2,statenode1,statenode2).
transition(statenode2_statenode1,statenode2,statenode1).
transition(statenode1_statenode3,statenode1,statenode3).
transition(statenode2_statenode3,statenode2,statenode3).
transition(statenode3_circularfinalstatenode0,statenode3,circularfinalstatenode0).
