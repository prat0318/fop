Pr : [counter] [sizeofDel] [sizeOfBasic] [sizeOf] [debug] [rootDel] [delete] root :: PL ;

%%
rootDel iff (root and delete);
sizeOfBasic iff (sizeOf);
sizeofDel iff (sizeOf and delete);
##
rootDel {hidden}
sizeOfBasic {hidden}
sizeofDel  {hidden}
delete  {out=""}
counter {out=""}
debug   {out=""}
sizeOf  {out=""}