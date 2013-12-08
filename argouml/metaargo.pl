dbname(argouml, [class, interface, attribute, association, association_end]).
table(class,[id,name,visibility,parent_id]).
table(attribute,[id,name,class_id, visibility]).
table(association,[id, name]).
table(association_end, [id, association_id, class_id, cardinality]).
table(interface,[id,name,visibility,parent_id]).

tuple(class,L):-class(I,N,V,P),L=[I,N,V,P].
tuple(interface,L):-interface(I,N,V,P),L=[I,N,V,P].
tuple(association,L):-association(I,N),L=[I,N].
tuple(association_end,L):-association_end(ID,A_id,C_id,C),L=[ID,A_id,C_id,C].

