:- style_check(-discontiguous).
:- style_check(-singleton).

table(node_violet,[id,name,nodeType,x,y]).
node_violet(circularinitialstatenode0,circularinitialstatenode0,start,428,367).
node_violet(circularfinalstatenode0,circularfinalstatenode0,stop,824,373).
node_violet(statenode0,loop,state,603,352).

table(transition,[transid,startsAt,endsAt]).
transition(circularinitialstatenode0_statenode0,circularinitialstatenode0,statenode0).
transition(statenode0_statenode0,statenode0,statenode0).
transition(statenode0_circularfinalstatenode0,statenode0,circularfinalstatenode0).
