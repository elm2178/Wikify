grammar Wiki; 
/* Lennart just tried github push*/
/* The Start Production */
/* at some point I would like to add import statements */

prog: prog_seq;

prog_seq: seq prog_seq 
    | /* epsilon */
    ;

seq: func_seq
    | main_func
    | stmt_seq
    | NL
    ;

main_func: MAIN '()' NL stmt_seq END 
    ;

stmt_seq: stmt NL stmt_seq
        | stmt (NL|EOF)
        ;
        
func_seq: func NL func_seq
        | func 
        ;

stmt: PRINT '(' expr ')'            # Print
    | type ID                       # Declare
    | type ID '=' expr              # DecAssign 
    | ID '=' expr                   # Assign
    | (ID u=(PP|MM) | u=(PP|MM) ID) # IncDec
    | func_action                   # FuncAct
    | if_stmt                       # IfStmt
    | while_stmt                    # WhileStmt
    | for_stmt                      # ForStmt
    | comm                          # Comment
    | BRK                           # Break
    ;

comm: LCOM
    | COMMENT
    ;
    
/* Loop Types ***************/
for_stmt: FOR '('stmt ';'expr';'stmt')' NL stmt_seq END;

while_stmt: WHILE '('expr')' NL stmt_seq END;

/*****************************/

/* if statements *************/
if_stmt: IF '('expr')' NL stmt_seq else_stmt END;

else_stmt: ELSE NL stmt_seq 
    | /* epsilon */
    ;
/*****************************/

func_action: ID '=' ID '(' params ')'# FuncAssign
    | type ID '=' ID '(' params ')'  # FuncDecAssign
    | ID '(' params ')'              # FuncCall
    ;

/* maybe a funccall should be an expression, not sure yet */
expr: int_expr
    | bool_expr
    | str_expr
    ;

/* Function Definition *******/
func: FUNC (type|) ID '('args')' NL func_stmt ret_stmt END # FuncDef
    ;

func_stmt: stmt NL func_stmt        # FuncStmt
        | /* epsilon */             # NoFunctStmt 
        | ID '(' params ')'         # FCall
        ;

/* Function Arguments ********/
params: expr ',' params            
    | expr
    | /* epsilon */   
    ;

args: type ID ',' args 
    | type ID 
    | /* epsilon */
    ; 
/****************************/

/* Return Statements ********/
ret_stmt: RETURN expr NL                        # RetExpr
    | /* epsilon */                             # NoRet
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
    | '!'bool_fact                
    | cond
    ;
cond: int_expr (LT|GT|LTE|GTE|EQ|NEQ) int_expr;
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

term: term op=(MUL|DIV|MOD) fact    # MulDiv
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
MOD: '%';
GT: '>';
LT: '<';
GTE:'>=';
LTE: '<=';
EQ: '==';
NEQ: '!=';
PP: '++';
MM: '--';
/* Keywords in Wikify *******/
INT: 'num';
STRING: 'string';
BOOL: 'bool';
TRUE: 'true';
FALSE: 'false';
PRINT: 'print';
FUNC: 'func';
END: 'end';
RETURN: 'return';
IF: 'if';
ELSE: 'else';
WHILE: 'while';
FOR: 'for';
BRK: 'break';
MAIN: 'main';
/****************************/
ID : [a-zA-Z][a-zA-Z0-9]*;
NUM: [0-9]+;
COMMENT: '//'~[\r\n]*;
LCOM: '/*'.*?'*/'; 
STRLIT: '"'( '\\''"' |~[\r\n])+'"';
NL: [\n]+;
WS : [ \t]+ -> skip;
