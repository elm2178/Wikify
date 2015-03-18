grammar Wiki;

/*The Start rule */
prog: stmt_seq ; 

stmt_seq: stmt NEWLINE stmt_seq
        | stmt
        | /* epsilon */ 
        ;

stmt: print_stmt ;

print_stmt: PRINT '"' ID '"';

PRINT: 'print'; 
ID : [a-zA-Z]+;
INT: [0-9]+;
NEWLINE: ['\n']+;
WS : [ \t]+ -> skip;
