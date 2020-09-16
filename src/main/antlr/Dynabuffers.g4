/*
 * Lexer Rules
 */

grammar Dynabuffers;

IDENTIFIER : Letter LetterOrDigit*;
IDENTIFIER_LITERAL : '`' LetterOrDigitOrSymbol* '`';
STRING     : '"' StringCharacter* '"';
NUMBER     : Digit+;
BOOLEAN    : 'true' | 'false';

fragment Digit           : '-'?[0-9];
fragment Letter          : [a-zA-Z];
fragment LetterOrDigit   : [a-zA-Z0-9_];
fragment LetterOrDigitOrSymbol   : [a-zA-Z0-9_/];
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
compilation   : (enumType | classType | unionType | namespaceType)* EOF ;
enumType      : 'enum' IDENTIFIER '{' IDENTIFIER+ '}';
classType     : 'class' IDENTIFIER classOptions? '{' fieldType+ '}';
unionType     : 'union' IDENTIFIER unionOptions? '{' IDENTIFIER+ '}';
fieldType     : annotation* IDENTIFIER ':' (dataType | arrayType | optionType) fieldOptions? ('=' value)?;
dataType      : ('string' | 'short' | 'boolean' | 'byte' | 'float' | 'long' | 'int' | 'map' | IDENTIFIER);
arrayType     : '[' dataType ']';
optionType    : (dataType '?') | (arrayType '?');
namespaceType : 'namespace' ( IDENTIFIER | IDENTIFIER_LITERAL ) '{' (enumType | classType | unionType | namespaceType)* '}';

// structural
classOptions : '(' ('primary' | 'deprecated' | 'implicit')+ ')';
unionOptions : '(' ('primary' | 'deprecated' | 'implicit')+ ')';
fieldOptions : '(' 'deprecated' ')';
annotation   : '@' IDENTIFIER ('(' value ')')?;
value        : (STRING | NUMBER | BOOLEAN | IDENTIFIER | '[]' | '[:]');
