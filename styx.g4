grammar styx;
import Lexer;

prog: stat+ EOF;

stat
  : expr ( NEWLINE | EOF )         # ExprStmt
  | ID '=' expr ( NEWLINE | EOF )   # assign
  | 'print' '(' expr ')' ( NEWLINE | EOF ) # printStmt
  | NEWLINE               # blank
  ;

expr
  : expr op=('*'|'/') expr   # mulDiv
  | expr op=('+'|'-') expr   # addSub
  | INT                      # int
  | ID                       # id
  | '(' expr ')'             # parens
  ;