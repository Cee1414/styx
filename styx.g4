grammar styx;
import Lexer;

prog: mainFunction EOF;

mainFunction
  : 'main' '('')' block
  ;

block
  : '{' stat* '}'
  ;

stat
  : expr ';'                 # ExprStmt
  | ID '=' expr ';'          # assign
  | 'print' '(' expr ')' ';' # printStmt
  | block                    # blockStmt
  ;

expr
  : expr op=('*'|'/') expr   # mulDiv
  | expr op=('+'|'-') expr   # addSub
  | expr op=('>'|'<') expr   # comparison
  | expr   op='=='    expr   # equality
  | INT                      # int
  | ID                       # id
  | '(' expr ')'             # parens
  ;