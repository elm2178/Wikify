grammar Wiki; 

/* The Start Production */
prog: func_seq NEWLINE* stmt_seq ;

stmt_seq: stmt NEWLINE stmt_seq
        | stmt EOF
        | /* epsilon */ 
        ;
        
func_seq: func NEWLINE func_seq
        | func 
        | /* epsilon */ 
        ;

stmt: print_stmt                    # PrintStmt 
    | ID ID '=' expr                # IntAssign
    | ID ID '=' str_expr            # StrAssign
    ;

func: FUNC ID ID '('args')' NEWLINE func_stmt ret_stmt ENDFUNC # FuncDef
    ;

func_stmt: stmt NEWLINE func_stmt   # FuncStmt
        | /* epsilon */             # NoFunctStmt 
        ;


ret_stmt: RETURN expr NEWLINE               # RetExpr
    | RETURN str_expr NEWLINE               # RetStr
    | /* epsilon */                         # NoRet
    ; 

args: ID ID ',' args 
    | ID ID 
    | /* epsilon */
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
FUNC: 'func';
ENDFUNC: 'endfunc';
RETURN: 'return';
ID : [a-zA-Z][a-zA-Z0-9]*;
INT: [0-9]+;
COMMENT: '/''/'(~['\n'])*['\n'] -> skip;
LCOM: '/''*'(~[*/])*'*''/' -> skip; 
STRING: '"'( '\\''"' |~[\r\n])+'"';
NEWLINE: ['\n']+;
WS : [ \t]+ -> skip;
