/* the discontiguous declaration says rows of "table" are not consecutive */
:- discontiguous table/2.

dbase(fsm,[class, attribute, compostion, assocaitedEnd, inheritance]).

table(class,[classId, name]).
table(attribute,[attributeId, name, attrOf]).
table(composition, [compostionId]).
table(associationEnd, [assId, name, cardinality, endOf, anchoredAt]).
table(inheritance, [inheritanceId, parentClassAnchor, childClassAnchor]).

% Data.
class(c1, c1).
class(c2, c2).
class(c3, c3).
class(c4, c3).

attribute(a1, value, c1).
attribute(a2, value, c2).
attribute(a3, value, c2).

inheritance(i1, c1, c3).
inheritance(i1, c1, c1).

composition(comp1).
composition(comp2).

associationEnd(ass1, has, 1, comp1, c1).
associationEnd(ass2, has, *, comp1, c2).
associationEnd(ass3, has, 0, comp2, c3).
associationEnd(ass4, has, 0, comp2, c2).

% Inheritance related Constraints.
inheritance_and_composition :- inheritance(_,P,C), associationEnd(_,_,_,CompId,P), associationEnd(_,_,_,CompId, C), writeln('Inheritance and composition co-exist among two classes.').

parent_and_child_same :- inheritance(_,P,P), writeln('Parent and child of inheritance relation defined to be same.').

% Homework-3 related constraints.
class_name_not_unique :- class(_,C1), aggregate_all(count, class(_,C1), COUNT), COUNT\=1, write('Class name not unique.').

attribute_name_not_unique :- attribute(_,Name,C), aggregate_all(count, attribute(_,Name,C), COUNT), COUNT\=1, write('Attribute name for a class is not unique.').

zero_zero_cardinality :- composition(C), aggregate_all(count, associationEnd(_,_,0,C,_), COUNT), COUNT == 2, write('A composition has zero-zero cardinality').

% Validate all constraints.
all_constraints_valid :- not(parent_and_child_same), not(inheritance_and_composition), not(class_name_not_unique), not(attribute_name_not_unique), not(zero_zero_cardinality) .

