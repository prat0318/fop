table(yumlClass,[id,"name","fields","methods"]).
yumlClass(c4,'G','','apply(A);apply(I);').
yumlClass(c15,'intsum','','eval(String);toString();apply(A);apply(G);apply(H);apply(I);').
yumlClass(c3,'advsum','','eval(String);toString();apply(A);').
yumlClass(c12,'hoasum','','eval(String);toString();apply(H);').
yumlClass(c0,'A','','apply(A);apply(G);apply(H);apply(I);').
yumlClass(c11,'hoaadv','','eval(String);toString();apply(H);').
yumlClass(c10,'hoa','','eval(String);toString();apply(H);').
yumlClass(c9,'H','','apply(A);apply(G);apply(I);').
yumlClass(c13,'I','','').
yumlClass(c14,'intro','','eval(String);toString();apply(A);apply(G);apply(H);apply(I);').
yumlClass(c16,'Main','initChoices;','_Main();_Main(String);_main(String#);applicationExit();initAtoms();initConstants();initContentPane();initLayout();initListeners();updateQuarkPanel();').
yumlClass(c18,'tree','','eval(String);toString();apply(A);apply(G);apply(H);apply(I);print();').
yumlClass(c8,'ghoaadv','','eval(String);toString();apply(G);apply(H);').
yumlClass(c19,'JFrame','','').
yumlClass(c6,'gadvprog','','eval(String);toString();apply(A);apply(G);apply(H);apply(I);').
yumlClass(c2,'advprog','','eval(String);toString();apply(A);apply(G);apply(H);apply(I);').
yumlClass(c5,'gadvice','','eval(String);toString();apply(G);apply(H);').
yumlClass(c17,'SwingApp','ContentPane;','_SwingApp();_SwingApp(String);applicationExit();init();initAtoms();initConstants();initContentPane();initLayout();initListeners();').
yumlClass(c1,'advice','','eval(String);toString();').
yumlClass(c7,'gadvsum','','eval(String);toString();apply(A);apply(G);apply(H);').

table(yumlInterface,[id,"name","methods"]).
:- dynamic yumlInterface/3.

table(yumlAssociation,["name1","role1",end1,"name2","role2",end2]).
yumlAssociation('tree','','^','A','','').
yumlAssociation('A','','^','advice','','').
yumlAssociation('I','','^','advprog','','').
yumlAssociation('advprog','','','A','left','<>').
yumlAssociation('advprog','','','I','right','<>').
yumlAssociation('A','','^','advsum','','').
yumlAssociation('advsum','','','A','left','<>').
yumlAssociation('advsum','','','A','right','<>').
yumlAssociation('tree','','^','G','','').
yumlAssociation('G','','^','gadvice','','').
yumlAssociation('I','','^','gadvprog','','').
yumlAssociation('gadvprog','','','G','left','<>').
yumlAssociation('gadvprog','','','I','right','<>').
yumlAssociation('G','','^','gadvsum','','').
yumlAssociation('gadvsum','','','G','left','<>').
yumlAssociation('gadvsum','','','G','right','<>').
yumlAssociation('G','','^','ghoaadv','','').
yumlAssociation('ghoaadv','','','G','right','<>').
yumlAssociation('ghoaadv','','','H','left','<>').
yumlAssociation('tree','','^','H','','').
yumlAssociation('H','','^','hoa','','').
yumlAssociation('A','','^','hoaadv','','').
yumlAssociation('hoaadv','','','A','right','<>').
yumlAssociation('hoaadv','','','H','left','<>').
yumlAssociation('H','','^','hoasum','','').
yumlAssociation('hoasum','','','H','left','<>').
yumlAssociation('hoasum','','','H','right','<>').
yumlAssociation('tree','','^','I','','').
yumlAssociation('I','','^','intro','','').
yumlAssociation('I','','^','intsum','','').
yumlAssociation('intsum','','','I','left','<>').
yumlAssociation('intsum','','','I','right','<>').
yumlAssociation('SwingApp','','^','Main','','').
yumlAssociation('Main','','','tree','t','<>').
yumlAssociation('JFrame','','^','SwingApp','','').

