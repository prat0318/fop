/*  file violet.schema.pl -- Violet Schema Declaration */

dbase(violet,[violetClass,violetInterface,violetAssociation,violetInterfaceExtends,violetClassImplements]).

table(violetClass,[id,"name","fields","methods",superid,x,y]).
table(violetInterface,[id,"name","methods",x,y]).
table(violetAssociation,[cid1,"role1",arrow1,type1,cid2,"role2",arrow2,type2,lineStyle]).
table(violetInterfaceExtends,[id,idx]).
table(violetClassImplements,[cid,iid]).

tuple(violetClass,L):-violetClass(I,N,F,M,S,X,Y),L=[I,N,F,M,S,X,Y].
tuple(violetInterface,L):-violetInterface(I,N,M,X,Y),L=[I,N,M,X,Y].
tuple(violetAssociation,L):-violetAssociation(To,ToRole,ToArrow,ToType,From,FromRole,FromArrow,FromType,Line),
                          L=[To,ToRole,ToArrow,ToType,From,FromRole,FromArrow,FromType,Line].
tuple(violetInterfaceExtends,L):-violetInterfaceExtends(ID,IDX),L=[ID,IDX].
tuple(violetClassImplements,L):-violetClassImplements(CID,IID),L=[CID,IID].

