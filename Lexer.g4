lexer grammar Lexer;
ID : [a-zA-Z_][a-zA-Z_0-9]* ;
INT : [0-9]+ ;
NEWLINE:'\r'? '\n' -> skip ;
WS : [ \t]+ -> skip ;