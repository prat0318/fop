/* file: violet2sdb.pl */

:-style_check(-discontiguous).

% note: superid is always null -- should fix this
table(violetClass,[id,"name","fields","methods",superid,x,y]).
table(violetInterface,[id,"name","methods",x,y]).
table(violetAssociation,[cid1,"role1",arrow1,type1,cid2,"role2",arrow2,type2,lineStyle]).

/*********** translation of violetClass to class tuples *************/
inherit(N,N1):-violetAssociation(N1,_,'TRIANGLE',_,N,_,_,_,_),violetClass(N1,_,_,_,_,_,_),!.
inherit(N,N1):-violetAssociation(N,_,_,_,N1,_,'TRIANGLE',_,_),violetClass(N1,_,_,_,_,_,_),!.
inherit(_,null).
class(I,N,F,M,S):-violetClass(I,N,F,M,_,_,_),inherit(I,S).
position(I,X,Y):-violetClass(I,_,_,_,_,X,Y).



/*********** translation of violetInterface to interface tuples *************/
interface(I,N,M):-violetInterface(I,N,M,_,_).
position(I,X,Y):-violetInterface(I,_,_,X,Y).

/*********** translation to violetAssociation to violetAssociation, classImplements, and interfaceExtends tuples *************/
xlate('V','\'arrow\'',_,_).
xlate('TRIANGLE','\'implem\'','classnode','interfacenode').
xlate('TRIANGLE','\'inherit\'','interfacenode',_).
xlate('TRIANGLE','\'inherit\'','classnode','classnode').
xlate('DIAMOND','\'agg\'',_,_).
xlate('BLACK_DIAMOND','\'comp\'',_,_).
xlate('','\'\'',_,_).
association(I1,R1,A1,I2,R2,A2):-violetAssociation(I1,R1,Arrow1,Type1,I2,R2,Arrow2,Type2,_),
                    not(Arrow1=='TRIANGLE'),not(Arrow2=='TRIANGLE'),
                    xlate(Arrow1,A1,Type1,Type2),xlate(Arrow2,A2,Type1,Type2).
classImplements(C,I):-violetClassImplements(C,I).
interfaceExtends(C,P):-violetInterfaceExtends(C,P).
