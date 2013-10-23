:- style_check(-discontiguous).
:- style_check(-singleton).

table(node_violet,[id,name,nodeType,x,y]).
node_violet(statenode0,j1,state,427,279).
node_violet(statenode1,j2 ,state,439,440).
node_violet(statenode2,j1,state,661,366).
node_violet(statenode3,j3 ,state,664,275).

table(transition,[transid,startsAt,endsAt]).
transition(statenode0_statenode1,statenode0,statenode1).
transition(statenode1_statenode2,statenode1,statenode2).
transition(statenode2_statenode0,statenode2,statenode0).
transition(statenode0_statenode3,statenode0,statenode3).
transition(statenode3_statenode2,statenode3,statenode2).
transition(statenode1_statenode3,statenode1,statenode3).
