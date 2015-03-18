grammar Wiki; 
/*The Start rule */
prog: stmt_seq ; 

stmt_seq: stmt NEWLINE stmt_seq
        | stmt
        | /* epsilon */ 
        ;

stmt: print_stmt 
    | assignment
    ;

assignment: ID '=' expr;

expr: expr ADD term
    | expr SUB term
    | term
    ;

term: term MUL fact
    | term DIV fact
    | fact
    ;

fact: LPAREN expr RPAREN
    | number 
    ;

number: INT;

print_stmt: PRINT STRING;

RPAREN: ')';
LPAREN: '(';
MUL: '*'; 
ADD: '+';
DIV: '/';
SUB: '-';
PRINT: 'print'; 
ID : [a-zA-Z]+;
INT: [0-9]+;
STRING: '"'(~["\\\r\n])+'"';
NEWLINE: ['\n']+;
WS : [ \t]+ -> skip;
