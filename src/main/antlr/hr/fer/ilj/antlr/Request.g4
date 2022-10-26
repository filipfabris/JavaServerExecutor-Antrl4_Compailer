grammar Request;

@header {
package hr.fer.ilj.antlr; 
}

request: 'FILTER' expr* 'RETURN' exp ;

expr: KEY OP value;
exp: count ;

value: STRING ;
count: STRING ;

STRING: '"' (ESC|.)*? '"' ;
ESC : '\\"' | '\\\\' ; 

KEY: 'IP' | 'DATETIME' | 'METHOD' | 'VERSION' | 'STATUS' ;
OP: '==' | '!=';
WS : [ \t\r\n]+ -> skip ;