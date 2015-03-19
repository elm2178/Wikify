grammar Wiki; 

/* The Start Production */
prog: stmt_seq ; 

stmt_seq: stmt NEWLINE stmt_seq
        | stmt
        | /* epsilon */ 
        ;

stmt: print_stmt                    # PrintStmt 
    | ID '=' expr                   # IntAssign
    | ID '=' str_expr               # StrAssign
    | COMMENT                       # Comment
    | funct                         # Funtion
    ;

/* String Expressions       */
str_expr: STRING '+' str_expr       # ConcatStr
    | ID '+' str_expr               # ConcatId
    | STRING                        # StrLit
    | ID                            # IdString
    ;
/****************************/

/* Arithmetic Expressions   */
expr: expr op=(ADD|SUB) term        # AddSub
    | term                          # TermExpr 
    ;

term: term op=(MUL|DIV) fact        # MulDiv
    | fact                          # FactTerm
    ;

fact: '('expr')'                    # Parens
    | INT                           # Integer
    | ID                            # Var
    ;
/****************************/

/*Print Stmt                */
print_stmt: PRINT str_expr          # PrintStrExpr
    | PRINT expr                    # PrintExpr
    ;
/***************************/

MUL: '*'; 
ADD: '+';
DIV: '/';
SUB: '-';
PRINT: 'print'; 
ID : [a-zA-Z]+;
INT: [0-9]+;
COMMENT: '/''/'(~['\n'])* ;
STRING: '"'( '\\''"' |~[\r\n])+'"';
NEWLINE: ['\n']+;
WS : [ \t]+ -> skip;
