:- style_check(-discontiguous).
:- style_check(-singleton).

table(node_violet,[id,name,nodeType,x,y]).
node_violet(statenode0,first ,state,203,258).
node_violet(statenode1,second,state,509,151).
node_violet(statenode2,third ,state,1052,144).
node_violet(statenode3,forth,state,588,447).

table(transition,[transid,startsAt,endsAt,function,conversionType,addFile]).
transition(statenode0_statenode1,statenode0,statenode1,toSecond,M2M,a.pl).
transition(statenode1_statenode2,statenode1,statenode2,toThird,M2T,b.vm).
transition(statenode3_statenode1,statenode3,statenode1,toSecond,java,ut.parser.second).
transition(statenode2_statenode1,statenode2,statenode1,toSecond,java,ut.parser.second2).
