:- style_check(-discontiguous).

table(node,[id,name,nodeType,x,y]).
node(circularinitialstatenode0,circularinitialstatenode0,start,243,283).
node(statenode0,s1,state,416,178).
node(statenode1,s2,state,418,316).
node(circularfinalstatenode0,circularfinalstatenode0,stop,640,279).

table(transition,[transid,startsAt,endsAt]).
transition(circularinitialstatenode0->statenode0,circularinitialstatenode0,statenode0).
transition(statenode0->statenode1,statenode0,statenode1).
transition(statenode0->circularfinalstatenode0,statenode0,circularfinalstatenode0).
transition(statenode1->circularfinalstatenode0,statenode1,circularfinalstatenode0).
transition(statenode1->circularinitialstatenode0,statenode1,circularinitialstatenode0).
