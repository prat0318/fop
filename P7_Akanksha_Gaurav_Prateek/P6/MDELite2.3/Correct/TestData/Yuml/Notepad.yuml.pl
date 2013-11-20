table(yumlClass,[id,"name","fields","methods"]).
yumlClass(c10,'filechooser.FileFilter','','').
yumlClass(c4,'Fonts','','_Fonts();font();getCajb();getOkjb();').
yumlClass(c9,'JPanel','','').
yumlClass(c11,'JDialog','','').
yumlClass(c6,'Print','','_Print(Component);print(Graphics print.PageFormat int);_disableDoubleBuffering(Component);_enableDoubleBuffering(Component);_printComponent(Component);print();').
yumlClass(c5,'Main','','_Main();getLineWrap();getTextArea();_main(String#);').
yumlClass(c7,'RedoAction','','_RedoAction(Main);actionPerformed(ActionEvent);').
yumlClass(c12,'JFrame','','').
yumlClass(c13,'AbstractAction','','').
yumlClass(c1,'Actions','','_Actions(Main);abouT();copY();cuT();exiT();finD();findNexT();fonT();lineWraP();neW();opeN();open();pastE();prinT();savE();save();saveAs();selectALL();').
yumlClass(c2,'Center','','_Center(Fonts);_Center(Main);fCenter();nCenter();').
yumlClass(c0,'About','','_About();').
yumlClass(c8,'UndoAction','','_UndoAction(Main);actionPerformed(ActionEvent);').
yumlClass(c3,'ExampleFileFilter','','_ExampleFileFilter();_ExampleFileFilter(String);_ExampleFileFilter(String String);_ExampleFileFilter(String#);_ExampleFileFilter(String# String);getDescription();getExtension(File);accept(File);isExtensionListInDescription();addExtension(String);setDescription(String);setExtensionListInDescription(boolean);').

table(yumlInterface,[id,"name","methods"]).
:- dynamic yumlInterface/3.

table(yumlAssociation,["name1","role1",end1,"name2","role2",end2]).
yumlAssociation('JPanel','','^','About','','').
yumlAssociation('Actions','','','Fonts','font','<>').
yumlAssociation('filechooser.FileFilter','','^','ExampleFileFilter','','').
yumlAssociation('JDialog','','^','Fonts','','').
yumlAssociation('Fonts','','','Center','center','<>').
yumlAssociation('JFrame','','^','Main','','').
yumlAssociation('Main','','','Actions','actions','<>').
yumlAssociation('Main','','','Center','center','<>').
yumlAssociation('AbstractAction','','^','RedoAction','','').
yumlAssociation('AbstractAction','','^','UndoAction','','').

