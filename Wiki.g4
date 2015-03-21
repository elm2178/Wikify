grammar Wiki; 

/* The Start Production */
/* at some point I would like to add import statements */
prog: func_seq NEWLINE* stmt_seq ;

stmt_seq: stmt NEWLINE stmt_seq
        | stmt EOF
        | /* epsilon */ 
        ;
        
func_seq: func NEWLINE func_seq
        | func  
        | /* epsilon */ 
        ;

stmt: PRINT expr                    # Print
    | type ID                       # Declare
    | type ID '=' expr              # DecAssign 
    | ID '=' expr                   # Assign
    | ID '=' ID '(' params ')'      # FuncAssign
    | type ID '=' ID '(' params ')' # FuncDecAssign
    | ID '(' params ')'             # FuncCall
    ;

/* maybe a funccall should be an expression, not sure yet */
expr: int_expr
    | bool_expr
    | str_expr
    ;

/* Function Definition *******/
func: FUNC (type|) ID '('args')' NEWLINE func_stmt ret_stmt ENDFUNC # FuncDef
    ;

func_stmt: stmt NEWLINE func_stmt   # FuncStmt
        | /* epsilon */             # NoFunctStmt 
        | ID '(' params ')'         # FCall
        ;

/* Function Arguments ********/
params: expr ',' params            
    | str_expr ',' params         
    | expr
    | str_expr
    | /* epsilon */   
    ;

args: type ID ',' args 
    | type ID 
    | /* epsilon */
    ; 
/****************************/

/* Return Statements ********/
ret_stmt: RETURN expr NEWLINE               # RetExpr
    | /* epsilon */                         # NoRet
    ; 
/****************************/

/* Boolean Expressions ******/
bool_expr: bool_expr '||' bool_term
    | bool_term
    ;
bool_term: bool_term '&&' bool_fact
    | bool_fact
    ;
bool_fact: '(' bool_expr ')'
    | TRUE
    | FALSE
    | ID                          
    | '~' bool_fact                
    ;
/****************************/

/* String Expressions *******/
str_expr: STRLIT '+' str_expr       # ConcatStr
    | ID '+' str_expr               # ConcatId
    | STRLIT                        # StrLit
    | ID                            # IdString
    ;
/****************************/

/* Arithmetic Expressions ***/
int_expr: int_expr op=(ADD|SUB) term# AddSub
    | term                          # TermExpr 
    ;

term: term op=(MUL|DIV) fact        # MulDiv
    | fact                          # FactTerm
    ;

fact: '('expr')'
    | NUM
    | ID 
    ;
/****************************/

type: INT
    | STRING
    | BOOL 
    ; 

MUL: '*';
ADD: '+';
DIV: '/';
SUB: '-';
GT: '>';
LT: '<';
GTE:'>=';
LTE: '<=';
EQ: '==';
/* Keywords in Wikify *******/
INT: 'int';
STRING: 'string';
BOOL: 'bool';
TRUE: 'true';
FALSE: 'false';
PRINT: 'print';
FUNC: 'func';
ENDFUNC: 'endfunc';
RETURN: 'return';
IF: 'if';
/****************************/
ID : [a-zA-Z][a-zA-Z0-9]*;
NUM: [0-9]+;
COMMENT: '/''/'(~['\n'])*['\n'] -> skip;
LCOM: '/''*'(~[*/])*'*''/' -> skip; 
STRLIT: '"'( '\\''"' |~[\r\n])+'"';
NEWLINE: ['\n']+;
WS : [ \t]+ -> skip;
