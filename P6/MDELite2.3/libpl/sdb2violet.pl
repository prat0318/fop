/* file: sdb2violet.pl  */

table(violetClass,[id,"name","fields","methods",superid,x,y]).
table(violetInterface,[id,"name","methods",x,y]).
table(violetAssociation,[cid1,"role1",arrow1,type1,cid2,"role2",arrow2,type2,lineStyle]).

violetClass(I,N,F,M,null,X,Y):-class(I,N,F,M,_),position(I,X,Y).

violetInterface(I,N,M,X,Y):-interface(I,N,M),position(I,X,Y).

violetClassImplements(ID,IDX):-classImplements(ID,IDX).
violetInterfaceExtends(IID,IDX):-interfaceExtends(IID,IDX).

xlate('V','arrow',_).
xlate('TRIANGLE','implem','interfaceNode').
xlate('TRIANGLE','inherit','classNode').
xlate('DIAMOND','agg',_).
xlate('BLACK_DIAMOND','comp',_).
xlate('','',_).
xlate('','none',_).

lineStyle('implem','\'DOTTED\'').
lineStyle('arrow','\'\'').
lineStyle('inherit','\'\'').
lineStyle('agg','\'\'').
lineStyle('comp','\'\'').
lineStyle('','\'\'').
lineStyle('none','\'\'').

/* % table(violetAssociation,[cid1,"role1",arrow1,type1,cid2,"role2",arrow2,type2,lineStyle]).
%% association,[cid1,"role1",arrow1,cid2,"role2",arrow2]
*/
%association(0,'','',0,'','implem').
notGen(A):-not(A==gen).
nothing(A):-(A==none;A==arrow).
something(A):-(A==comp;A==agg).
violetAssociation(C,'','\'\'','\'classnode\'',I,'','\'TRIANGLE\'','\'interfacenode\'','\'DOTTED\''):-
                classImplements(C,I).
violetAssociation(S1,'','\'\'','\'interfacenode\'',S2,'','\'TRIANGLE\'','\'interfacenode\'','\'\''):-
                interfaceExtends(S1,S2).
violetAssociation(S1,R1,Arrow11,'\'classnode\'',S2,R2,Arrow22,'\'classnode\'',LineStyle):-association(S1,R1,A1,S2,R2,A2),
                xlate(Arrow1,A1,_),xlate(Arrow2,A2,_), concat_atom(['\'',Arrow1,'\''],Arrow11),
                concat_atom(['\'',Arrow2,'\''],Arrow22),lineStyle(A2,LineStyle),
                class(S1,_,_,_,_),class(S2,_,_,_,_).

%Now we need to generate violetAssociatons from the class's superid
%And from the classimplements
%And from interfaceExtends

%jjvioletAssociation(S1,'','\'\'','\'classnode\'',S2,'','\'TRIANGLE\'','\'interfacenode\'','\'DOTTED\''):-classImplements(S1,S2),
%                    not(association(S1,_,_,S2,_,_)).
%violetAssociation(S1,'','\'\'','\'interfacenode\'',S2,'','\'TRIANGLE\'','\'interfacenode\'','\'\''):-interfaceExtends(S1,S2),
%                   not(association(S1,_,_,S2,_,_)).

violetAssociation(S2,'','\'TRIANGLE\'','\'classnode\'',S1,'','\'\'','\'classnode\'','\'\''):-class(S1,_,_,_,S2),class(S2,_,_,_,_).
%violetAssociation(S1,'','\'\'','\'classnode\'',S2,'','\'TRIANGLE\'','\'classnode\'','\'\''):-class(S1,_,_,_,S2),class(S2,_,_,_,_),association(S1,_,_,S2,_,Arrow2), Arrow2 = agg.

