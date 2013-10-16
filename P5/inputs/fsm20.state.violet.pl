:- style_check(-discontiguous).

table(node_violet,[id,name,nodeType,x,y]).
node_violet(circularinitialstatenode0,circularinitialstatenode0,start,403,352).
node_violet(statenode0,n1,state,534,275).
node_violet(statenode1,n2,state,726,280).
node_violet(statenode2,n3,state,532,387).
node_violet(statenode3,n4 ,state,733,383).
node_violet(circularfinalstatenode0,circularfinalstatenode0,stop,936,343).

table(transition,[transid,startsAt,endsAt]).
transition(circularinitialstatenode0_statenode0,circularinitialstatenode0,statenode0).
transition(statenode0_statenode1,statenode0,statenode1).
transition(statenode1_circularfinalstatenode0,statenode1,circularfinalstatenode0).
transition(circularinitialstatenode0_statenode2,circularinitialstatenode0,statenode2).
transition(statenode2_statenode3,statenode2,statenode3).
transition(statenode0_statenode3,statenode0,statenode3).
transition(statenode3_circularfinalstatenode0,statenode3,circularfinalstatenode0).
