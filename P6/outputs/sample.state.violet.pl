:- style_check(-discontiguous).
:- style_check(-singleton).

table(node_violet,[id,name,nodeType,x,y]).
node_violet(circularinitialstatenode0,circularinitialstatenode0,start,292,169).
node_violet(statenode0,q1,state,506,145).
node_violet(statenode1,q2,state,263,294).
node_violet(circularfinalstatenode0,circularfinalstatenode0,stop,541,313).

table(transition,[transid,startsAt,endsAt]).
transition(circularinitialstatenode0_statenode0,circularinitialstatenode0,statenode0).
transition(statenode0_circularinitialstatenode0,statenode0,circularinitialstatenode0).
transition(circularinitialstatenode0_statenode1,circularinitialstatenode0,statenode1).
transition(statenode1_circularinitialstatenode0,statenode1,circularinitialstatenode0).
transition(statenode1_statenode1,statenode1,statenode1).
transition(statenode0_circularfinalstatenode0,statenode0,circularfinalstatenode0).
transition(statenode1_circularfinalstatenode0,statenode1,circularfinalstatenode0).
