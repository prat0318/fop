:- style_check(-discontiguous).
:- style_check(-singleton).

table(node_violet,[id,name,nodeType,x,y]).
node_violet(circularinitialstatenode0,circularinitialstatenode0,start,243,283).
node_violet(statenode0,s1,state,416,178).
node_violet(statenode1,s2,state,418,316).
node_violet(circularfinalstatenode0,circularfinalstatenode0,stop,640,279).

table(transition,[transid,startsAt,endsAt]).
transition(circularinitialstatenode0_statenode0,circularinitialstatenode0,statenode0).
transition(statenode0_statenode1,statenode0,statenode1).
transition(statenode0_circularfinalstatenode0,statenode0,circularfinalstatenode0).
transition(statenode1_circularfinalstatenode0,statenode1,circularfinalstatenode0).
transition(statenode1_circularinitialstatenode0,statenode1,circularinitialstatenode0).
