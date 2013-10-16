:- style_check(-discontiguous).

table(node_violet,[id,name,nodeType,x,y]).
:- dynamic violetClass/7.

table(transition,[transid,startsAt,endsAt]).
:- dynamic violetAssociation/9.
