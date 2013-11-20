table(yumlClass,[id,"name","fields","methods"]).
yumlClass(c3,'Main','','_Main();_main(String#);').
yumlClass(c5,'NumberWorkSpace','','_NumberWorkSpace();preVisitAction(Vertex);').
yumlClass(c4,'Neighbor','','_Neighbor();_Neighbor(Vertex Edge);').
yumlClass(c0,'CycleWorkSpace','AnyCycles;isDirected;counter;BLACK;GRAY;WHITE;','_CycleWorkSpace(boolean);checkNeighborAction(Vertex Vertex);init_vertex(Vertex);postVisitAction(Vertex);preVisitAction(Vertex);').
yumlClass(c2,'Graph','inFile;edges;vertices;isDirected;ch;','_Graph();findsVertex(String);CycleCheck();readNumber();_endProfile();_resumeProfile();_startProfile();_stopProfile();GraphSearch(WorkSpace);NumberVertices();addAnEdge(Vertex Vertex int);addEdge(Edge);addOnlyEdge(Edge);addVertex(Vertex);display();run(Vertex);runBenchmark(String);stopBenchmark();').
yumlClass(c7,'WorkSpace','','_WorkSpace();checkNeighborAction(Vertex Vertex);init_vertex(Vertex);nextRegionAction(Vertex);postVisitAction(Vertex);preVisitAction(Vertex);').
yumlClass(c1,'Edge','weight;','_Edge();EdgeConstructor(Vertex Vertex);EdgeConstructor(Vertex Vertex int);adjustAdorns(Edge);display();').
yumlClass(c6,'Vertex','visited;VertexColor;VertexCycle;VertexNumber;name;neighbors;','_Vertex();assignName(String);VertexConstructor();addNeighbor(Neighbor);dftNodeSearch(WorkSpace);display();init_vertex(WorkSpace);').

table(yumlInterface,[id,"name","methods"]).
:- dynamic yumlInterface/3.

table(yumlAssociation,["name1","role1",end1,"name2","role2",end2]).
yumlAssociation('WorkSpace','','^','CycleWorkSpace','','').
yumlAssociation('Neighbor','','^','Edge','','').
yumlAssociation('Edge','','','Vertex','start','<>').
yumlAssociation('Neighbor','','','Edge','edge','<>').
yumlAssociation('Neighbor','','','Vertex','end','<>').
yumlAssociation('WorkSpace','','^','NumberWorkSpace','','').

