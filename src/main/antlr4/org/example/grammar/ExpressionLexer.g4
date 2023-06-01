// 定义了 grammar 的名字，名字需要与文件名对应
lexer grammar ExpressionLexer;

@Header {package org.example.grammar;}

/**
 * lexer
 */

BR_OPEN:   '(';
BR_CLOSE:  ')';
PLUS:      '+';
MINUS:     '-';
TIMES:     '*';
DIVIDE:    '/';
PERCENT:   '%';
POINT:     '.';

// 定义百分数
PERCENT_NUMBER
    : NUMBER ((' ')? PERCENT)
    ;

NUMBER
    : DIGIT+ ( POINT DIGIT+ )?
    ;

DIGIT
   : [0-9]+
   ;

// 定义了空白字符，后面的 skip 是一个特殊的标记，标记空白字符会被忽略
SPACE
   : ' ' -> skip
   ;