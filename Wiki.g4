grammar Wiki; 
@header {
    package wikify;
}

/* The Start Production */
prog: stmt_seq ; 

stmt_seq: stmt NEWLINE stmt_seq
        | stmt
        | /* epsilon */ 
        ;

stmt: print_stmt                    # PrintStmt
    | ID '=' expr                   # Assign
    | ID '=' str_expr               # StrAssign
    ;
/* String Expressions       */
str_expr: STRING '+' str_expr         # ConcatStr
    | ID '+' str_expr                 # ConcatId
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
print_stmt: PRINT STRING            # PrintString
    | PRINT ID                      # PrintId
    ;
/***************************/

MUL: '*'; 
ADD: '+';
DIV: '/';
SUB: '-';
PRINT: 'print'; 
ID : [a-zA-Z]+;
INT: [0-9]+;
STRING: '"'( '\\''"' |~[\r\n])+'"';
NEWLINE: ['\n']+;
WS : [ \t]+ -> skip;
