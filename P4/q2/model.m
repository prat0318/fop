Pr : [counterBasic] [sizeofDel] [sizeOfBasic] [debugBasic] [rootDel] [delete] [sizeOf] [counter] [debug] root :: PL ;

%%
debugBasic iff (debug);
counterBasic iff (counter);
rootDel iff (root and delete);
sizeOfBasic iff (sizeOf);
sizeofDel iff (sizeOf and delete);
##
rootDel {hidden}
counterBasic {hidden}
debugBasic {hidden}
sizeOfBasic {hidden}
sizeofDel  {hidden}
delete  {out=""}
counter {out=""}
debug   {out=""}
sizeOf  {out=""}