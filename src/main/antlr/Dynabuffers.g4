/*
 * Lexer Rules
 */

grammar Dynabuffers;

@header {
package dynabuffers.antlr;
}

IDENTIFIER : Letter LetterOrDigit*;
STRING     : ('"' StringCharacter* '"') | ('\'' CharSequence* '\'');
NUMBER     : Digit+;
BOOLEAN    : 'true' | 'false';

fragment Digit           : '-'?[0-9];
fragment Letter          : [a-zA-Z];
fragment LetterOrDigit   : [a-zA-Z0-9_];
fragment StringCharacter :	~'"';
fragment CharSequence    :	~'\'';

//
// Whitespace and comments
//
WS           : [ \t\r\n\u000C]+ -> skip;
LINE_COMMENT : '//' ~[\r\n]*    -> skip;
WHITESPACE   : ' '              -> skip;
SEMICOLON    : ';'              -> skip;

/*
 * Parser Rules
 */
compilation  : (enumType | classType | unionType)* ;
enumType     : 'enum' IDENTIFIER '{' IDENTIFIER+ '}';
classType    : 'class' IDENTIFIER ('(' ('primary' | 'deprecated')+ ')')? '{' fieldType+ '}';
unionType    : 'union' IDENTIFIER '{' IDENTIFIER+ '}';
fieldType    : IDENTIFIER ':' (dataType | arrayType) ('(' 'deprecated' ')')? ('=' (STRING | NUMBER | BOOLEAN | IDENTIFIER))?;
dataType     : ('string' | 'short' | 'boolean' | 'byte' | 'float' | 'long' | 'int' | IDENTIFIER);
arrayType    : '[' dataType ']';
