grammar styx;
import Lexer;

prog: stat+ ;

stat
  : expr NEWLINE          # printExpr
  | ID '=' expr NEWLINE   # assign
  | NEWLINE               # blank
  ;

expr
  : expr op=('*'|'/') expr   # mulDiv
  | expr op=('+'|'-') expr   # addSub
  | INT                      # int
  | ID                       # id
  | '(' expr ')'             # parens
  ;