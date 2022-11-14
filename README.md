# JavaServerExecutor-Antrl4_Compailer

## Server
- inside hr.fer.ilj.antlr.server package
- start `Main` to start ServerExecutor pool

## Demo
- inside hr.fer.ilj.antlr.server.demo
- you can test local input from own console for antlr4 language input

## Client
- inside hr.tel.fer.lab1.client
- in your console you can input query

## Logging
- inside hr.tel.fer.lab1.logging
- this package is used to parse logging file used on server
- every line in parsed using `LogLexer` then its information is stored inside `LogEntry`
- `Expression` class is used to store query
- `ExpressionExtractor` extends `ExpressionExtractor`, stores query inside `Expression`

## ANTLR4
- Inside build/generated-src/antlr/main
- Created from grammarwhich is inside `src/main/antlr/hr.fer.ilj.antlr`

## Grammar
```
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
```


## Query Examples
- FILTER METHOD!="GET" STATUS=="200" RETURN"5"
- FILTER IP=="161.53.19.XXX" VERSION=="1.1" RETURN"*" 

```
> FILTER METHOD!="GET" STATUS=="200" RETURN"5"
127.0.0.1 [11/Feb/2014:10:45:02] OPTIONS * HTTP/1.0 200 "Apache/2.2.22 (Debian) (internal dummy connection)"
127.0.0.1 [11/Feb/2014:10:45:02] OPTIONS * HTTP/1.0 200 "Apache/2.2.22 (Debian) (internal dummy connection)"
127.0.0.1 [11/Feb/2014:10:45:02] OPTIONS * HTTP/1.0 200 "Apache/2.2.22 (Debian) (internal dummy connection)"
127.0.0.1 [11/Feb/2014:10:45:02] OPTIONS * HTTP/1.0 200 "Apache/2.2.22 (Debian) (internal dummy connection)"
127.0.0.1 [11/Feb/2014:10:45:02] OPTIONS * HTTP/1.0 200 "Apache/2.2.22 (Debian) (internal dummy connection)"
```
