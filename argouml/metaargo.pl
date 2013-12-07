dbname(argouml, [class, attribute, association, association_end]).
table(class,[id,name,visibility,parent_id]).
table(attribute,[id,name,class_id, visibility]).
table(association,[id, name]).
table(association_end, [id, association_id, class_id, cardinality]).


