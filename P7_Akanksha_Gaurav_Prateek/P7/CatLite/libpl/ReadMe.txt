This is the directory of all prolog rule files.
The following Sections describe basic file types in 
this directory

-------------.schema.pl files--------------------------------
Files that have the name <ObjectType>.schema.pl  define
the prolog tables of the <ObjectType> database (e.g., sdb, yuml, etc.)
Let's look at yuml.schema.pl as an example, with comments unindented

	dbase(yuml,[yumlClass,yumlInterface,yumlAssociation]).

The above line declares that a yuml database contains 3 relations/tables
named yumlClass, yumlInterface, and yumlAssociation.  The schemas
for these tables are defined on the next 3 lines:

	table(yumlClass,[id,"name","fields","methods"]).
	table(yumlInterface,[id,"name","methods"]).
	table(yumlAssociation,["name1","role1","end1","name2","role2","end2"]).

Specifically, table yumlClass has 4 attributes: id, name, field, and methods.
There are 2 possible attribute types: plain or single-quoted.  id 
has a plain type whose values are prolog atoms of lower-case names.
name, field, and methods (because they are listed in "quotes") have
single-quoted 'values'.

	tuple(yumlClass,L):-yumlClass(I,N,F,M),L=[I,N,F,M].
	tuple(yumlInterface,L):-yumlInterface(I,N,M),L=[I,N,M].
	tuple(yumlAssociation,L):-yumlAssociation(N1,R1,E1,N2,R2,E2),L=[N1,R1,E1,N2,R2,E2].

The above 3 rules is a standard form for printing tuples of such tables.
I believe they can be "generated" from a schema tuple, but right now, they
have to be hand-written.


------------- prolog database files--------------------------------
Note: Specific instances of a yuml schema must have the following format,
of which the example below is such an instance:

	table(yumlClass,[id,"name","fields","methods"]).
	yumlClass(c1,'Customer','','').
	yumlClass(c0,'User','+Forename+;Surname;+HashedPassword;-Salt','+Login();+Logout()').
	yumlClass(c4,'Root','','').
	yumlClass(c2,'Order','','').
	yumlClass(c3,'LineItem','','').

	table(yumlInterface,[id,"name","methods"]).
	:- dynamic yumlInterface/3.

	table(yumlAssociation,["name1","role1",end1,"name2","role2",end2]).
	yumlAssociation('User','','^','Customer','','').
	yumlAssociation('Customer','','<>','Order','orders*','>').
	yumlAssociation('Order','','++','LineItem','0..*','>').
	yumlAssociation('Order','','','Root','','').

Here is what the above means:  First, we see a repeat of the yuml class table
declaration -- with the notable exception that the declaration itself is a prolog 
comment (proceeded by '%').  The tuples of this table must immediately follow
this commented definition.  In this case there are 5 such tuples.  The order
in which these tuples are listed does not matter.

The next table declaration (for yumlInterface) is different.  It has
no tuples.  But Prolog needs to know that such a table does exist and it has
3 attributes.  That's what the ":- dynamic yumlInterface/3" means.  If you
don't include this line, Prolog will think that you are referencing an undefined
set of facts.

The last table declaration (yumlAssociation) is no differen than the first
table -- it has 6 attributes of which end1 and end2 are unquoted prolog atoms.
There are 4 tuples in this table.


-------------.conform.pl files--------------------------------
Every database has a set of constraints that must be satisfied to make it "legal".
(In MDE terms, these are the constraints that models must satisfy to conform to
their metamodels).

So yuml.conform.pl are the constraints, written in prolog, that must be satisfied
for a yuml database to conform to its schema.  All of the conform.pl files that
are present rather similar, so look at any of them to get an idea of how constraints
are written.

------------------------run.pl files---------------------------
Files with name <ObjectType>.run.pl define the prolog rules for executing
prolog.  Normally, a single rule is defined, called "run;-" which generally
prints the tables of a database that is synthesized.  Let's look at
yuml.run.pl as an example; comments are unindented.

    /* output the tables of a Yuml database */

    run:-printTables([yumlClass,yumlAssociation,yumlInterface]).

This says print all tables of a yuml prolog database.  Note that each
table has to be listed explicitly, just in case.  The print.pl prolog
file will print all tables, but may end up printing tables that you don't
need.

-------------------------A2B.pl files-------------------------

Files of the form <ObjectTypeA>2<ObjectTypeB> define the rules for translating
an database of type ObjectTypeA  to a database of ObjectTypeB.

------------------------print.pl------------------------------
This is key file for printing tables.  Generally, you use the rule:

    printTables([<table list>]

to print the tables of a database.  Now the way printTable works is
that it searches for each table() declaration, and for each table-fact
there is a tuple-fact that converts a tuple into a printable format.

To project (remove) a table from a database, don't list it in 
the <table list> above.

Occasionally, some transformation is made to table X that produces
another instance of table X.  Well, in Prolog, you can't do this
directly. What you can do is produce table X1 as a translation of
table X.  Now, to print X1 as an X table, define this rule
for each such table pair:

    tableAlias(X1,X).

This tells the print rules to print "X" instead of "X1" when 
the tuples of X1 are being printed.
