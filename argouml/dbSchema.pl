dbase(argo,[argoClass,argoInterface,argoAssociation,argoInterfaceExtends,argoClassImplements]).

table(argoClass,[id,"name","fields","methods",superid,x,y]).
table(argoInterface,[id,"name","methods",x,y]).
table(argoAssociation,[cid1,"role1",arrow1,type1,cid2,"role2",arrow2,type2,lineStyle]).
table(argoInterfaceExtends,[id,idx]).
table(argoClassImplements,[cid,iid]).

tuple(argoClass,L):-argoClass(I,N,F,M,S,X,Y),L=[I,N,F,M,S,X,Y].
tuple(argoInterface,L):-argoInterface(I,N,M,X,Y),L=[I,N,M,X,Y].
tuple(argoAssociation,L):-argoAssociation(To,ToRole,ToArrow,ToType,From,FromRole,FromArrow,FromType,Line),
                          L=[To,ToRole,ToArrow,ToType,From,FromRole,FromArrow,FromType,Line].
tuple(argoInterfaceExtends,L):-argoInterfaceExtends(ID,IDX),L=[ID,IDX].
tuple(argoClassImplements,L):-argoClassImplements(CID,IID),L=[CID,IID].

