/* file: sdb2umlf.pl  */

umlfSimpleClass(I,N,X,Y):-class(I,N,'','',_),position(I,X,Y).

umlfClass(I,N,M,X,Y):-class(I,N,'',M,_),not(M==''),position(I,X,Y).
umlfClass(I,N,M,X,Y):-class(I,N,M,'',_),not(M==''),position(I,X,Y).
umlfClass(I,N,M,X,Y):-class(I,N,CF,CM,_),not(CF==''),not(CM==''),concat_atom([CF,';',CM],M),position(I,X,Y).

umlfInterface(I,N,M,X,Y):-interface(I,N,M),position(I,X,Y).

xlate('composition',comp).
xlate('aggregation',agg).
xlate('none',none).
xlate('none',arrow).
xlate('none',inherit).

/* umlfAssociation(to,target,dashed,anchor,source,from,type)
%% association,[cid1,"role1",arrow1,cid2,"role2",arrow2]

%% draw from none end to {agg,none,comp} end -- important to get this right!
%% roles are always empty (ignored) in umlf.
*/

notGen(A):-not(A==gen).
nothing(A):-(A==none;A==arrow;A=='').
something(A):-(A==comp;A==agg;A==inherit).
umlfAssociation(S1,none,false,false,Ar,S2,standard):-association(S1,_,A,S2,_,AR),nothing(A),something(AR),xlate(Ar,AR).
umlfAssociation(S1,none,false,false,Ar,S2,standard):-association(S2,_,AR,S1,_,A),something(AR),nothing(A),not(A==none),xlate(Ar,AR).
umlfAssociation(S1,none,false,false,none,S2,standard):-association(S1,_,AR,S2,_,A),nothing(AR),nothing(A).


/* old umlfAssociation(To,Ar1,false,false,Ar2,Frm,standard):-association(To,_,A1,Frm,_,A2),xlate(Ar1,A1),xlate(Ar2,A2). */

/* draw gen from Frm (subclass) to To (superclass) -- important to get this right!! */
umlfAssociation(To,gen,false,true,none,Frm,standard):-class(Frm,_,_,_,To),class(To,_,_,_,_).
umlfAssociation(To,gen,true,true,none,Frm,standard):-class(Frm,_,_,_,_),classImplements(Frm,To).
umlfAssociation(To,gen,true,true,none,Frm,standard):-interfaceExtends(Frm,To).

/*  association(Frm,Tar,arrow1,To,Src,arrow2). */
