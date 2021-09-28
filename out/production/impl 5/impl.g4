grammar impl;

/* A small imperative language */

start   :  cs+=command* EOF ;

program : c=command                      # SingleCommand
	| '{' cs+=command* '}'               # MultipleCommands
	;
	
command : x=ID '=' e=expr ';'	             # Assignment
	| 'output' e=expr ';'                    # Output
    | 'while' '('c=condition')' p=program    # WhileLoop
    | 'for' '(i=' i=expr '..' n=expr ')' p=program    # ForLoop
    | 'if' '('c=condition')' p=program       # IfStatement
	;
	
expr	:  e1=expr '*' e2=expr          # Multiplication
	| e1=expr '/' e2=expr               # Division
	| e1=expr  op=('+' | '-') e2=expr   # Addition
	| c= FLOAT     	                    # Constant
//	| e1=expr '-' e2=expr # Subtraction
	| x=ID		                        # Variable
	| '(' e=expr ')'                    # Parenthesis
	;

condition : e1=expr '!=' e2=expr         # Unequal
    | e1=expr '==' e2=expr               # Equal
    | e1=expr '>' e2=expr                # GreaterThan
    | e1=expr '<' e2=expr                # LessThan
    | e1=condition '||' e2=condition     # OrBinary

	;

ID    : ALPHA (ALPHA|NUM)* ;
FLOAT : NUM+ ('.' NUM+)? ;

ALPHA : [a-zA-Z_ÆØÅæøå] ;
NUM   : [0-9] ;


WHITESPACE : [ \n\t\r]+ -> skip;
COMMENT    : '//'~[\n]*  -> skip;
COMMENT2   : '/*' (~[*] | '*'~[/]  )*   '*/'  -> skip;