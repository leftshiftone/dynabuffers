/*
 * Lexer Rules
 */

grammar Dynabuffers;

IDENTIFIER : Letter LetterOrDigit*;
STRING     : '"' StringCharacter* '"';
NUMBER     : Digit+;
BOOLEAN    : 'true' | 'false';

fragment Digit           : '-'?[0-9];
fragment Letter          : [a-zA-Z];
fragment LetterOrDigit   : [a-zA-Z0-9_];
fragment StringCharacter :	~'"';

//
// Whitespace and comments
//
WHITESPACE   : [ \t\r\n\u000C]+ -> skip;
LINE_COMMENT : '//' ~[\r\n]*    -> skip;
SEMICOLON    : ';'              -> skip;

/*
 * Parser Rules
 */
// serializable
compilation   : (enumType | classType | unionType)* ;
enumType      : 'enum' IDENTIFIER '{' IDENTIFIER+ '}';
classType     : 'class' IDENTIFIER classOptions? '{' fieldType+ '}';
unionType     : 'union' IDENTIFIER '{' IDENTIFIER+ '}';
fieldType     : annotation* IDENTIFIER ':' (dataType | arrayType) fieldOptions? ('=' value)?;
dataType      : ('string' | 'short' | 'boolean' | 'byte' | 'float' | 'long' | 'int' | 'map' | IDENTIFIER);
arrayType     : '[' dataType ']';

// structural
classOptions : '(' ('primary' | 'deprecated')+ ')';
fieldOptions : '(' 'deprecated' ')';
annotation   : '@' IDENTIFIER ('(' value ')')?;
value        : (STRING | NUMBER | BOOLEAN | IDENTIFIER);
