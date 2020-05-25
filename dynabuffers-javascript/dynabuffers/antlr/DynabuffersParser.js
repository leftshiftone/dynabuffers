// Generated from Dynabuffers.g4 by ANTLR 4.7.2
// jshint ignore: start
var antlr4 = require('antlr4/index');
var DynabuffersListener = require('./DynabuffersListener').DynabuffersListener;
var DynabuffersVisitor = require('./DynabuffersVisitor').DynabuffersVisitor;

var grammarFileName = "Dynabuffers.g4";


var serializedATN = ["\u0003\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964",
    "\u0003\u001eq\u0004\u0002\t\u0002\u0004\u0003\t\u0003\u0004\u0004\t",
    "\u0004\u0004\u0005\t\u0005\u0004\u0006\t\u0006\u0004\u0007\t\u0007\u0004",
    "\b\t\b\u0004\t\t\t\u0004\n\t\n\u0004\u000b\t\u000b\u0004\f\t\f\u0003",
    "\u0002\u0003\u0002\u0003\u0002\u0007\u0002\u001c\n\u0002\f\u0002\u000e",
    "\u0002\u001f\u000b\u0002\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003",
    "\u0006\u0003%\n\u0003\r\u0003\u000e\u0003&\u0003\u0003\u0003\u0003\u0003",
    "\u0004\u0003\u0004\u0003\u0004\u0005\u0004.\n\u0004\u0003\u0004\u0003",
    "\u0004\u0006\u00042\n\u0004\r\u0004\u000e\u00043\u0003\u0004\u0003\u0004",
    "\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0006\u0005<\n\u0005",
    "\r\u0005\u000e\u0005=\u0003\u0005\u0003\u0005\u0003\u0006\u0007\u0006",
    "C\n\u0006\f\u0006\u000e\u0006F\u000b\u0006\u0003\u0006\u0003\u0006\u0003",
    "\u0006\u0003\u0006\u0005\u0006L\n\u0006\u0003\u0006\u0005\u0006O\n\u0006",
    "\u0003\u0006\u0003\u0006\u0005\u0006S\n\u0006\u0003\u0007\u0003\u0007",
    "\u0003\b\u0003\b\u0003\b\u0003\b\u0003\t\u0003\t\u0006\t]\n\t\r\t\u000e",
    "\t^\u0003\t\u0003\t\u0003\n\u0003\n\u0003\n\u0003\n\u0003\u000b\u0003",
    "\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0005\u000bm",
    "\n\u000b\u0003\f\u0003\f\u0003\f\u0002\u0002\r\u0002\u0004\u0006\b\n",
    "\f\u000e\u0010\u0012\u0014\u0016\u0002\u0005\u0004\u0002\n\u0010\u0018",
    "\u0018\u0003\u0002\u0014\u0015\u0003\u0002\u0018\u001b\u0002r\u0002",
    "\u001d\u0003\u0002\u0002\u0002\u0004 \u0003\u0002\u0002\u0002\u0006",
    "*\u0003\u0002\u0002\u0002\b7\u0003\u0002\u0002\u0002\nD\u0003\u0002",
    "\u0002\u0002\fT\u0003\u0002\u0002\u0002\u000eV\u0003\u0002\u0002\u0002",
    "\u0010Z\u0003\u0002\u0002\u0002\u0012b\u0003\u0002\u0002\u0002\u0014",
    "f\u0003\u0002\u0002\u0002\u0016n\u0003\u0002\u0002\u0002\u0018\u001c",
    "\u0005\u0004\u0003\u0002\u0019\u001c\u0005\u0006\u0004\u0002\u001a\u001c",
    "\u0005\b\u0005\u0002\u001b\u0018\u0003\u0002\u0002\u0002\u001b\u0019",
    "\u0003\u0002\u0002\u0002\u001b\u001a\u0003\u0002\u0002\u0002\u001c\u001f",
    "\u0003\u0002\u0002\u0002\u001d\u001b\u0003\u0002\u0002\u0002\u001d\u001e",
    "\u0003\u0002\u0002\u0002\u001e\u0003\u0003\u0002\u0002\u0002\u001f\u001d",
    "\u0003\u0002\u0002\u0002 !\u0007\u0003\u0002\u0002!\"\u0007\u0018\u0002",
    "\u0002\"$\u0007\u0004\u0002\u0002#%\u0007\u0018\u0002\u0002$#\u0003",
    "\u0002\u0002\u0002%&\u0003\u0002\u0002\u0002&$\u0003\u0002\u0002\u0002",
    "&\'\u0003\u0002\u0002\u0002\'(\u0003\u0002\u0002\u0002()\u0007\u0005",
    "\u0002\u0002)\u0005\u0003\u0002\u0002\u0002*+\u0007\u0006\u0002\u0002",
    "+-\u0007\u0018\u0002\u0002,.\u0005\u0010\t\u0002-,\u0003\u0002\u0002",
    "\u0002-.\u0003\u0002\u0002\u0002./\u0003\u0002\u0002\u0002/1\u0007\u0004",
    "\u0002\u000202\u0005\n\u0006\u000210\u0003\u0002\u0002\u000223\u0003",
    "\u0002\u0002\u000231\u0003\u0002\u0002\u000234\u0003\u0002\u0002\u0002",
    "45\u0003\u0002\u0002\u000256\u0007\u0005\u0002\u00026\u0007\u0003\u0002",
    "\u0002\u000278\u0007\u0007\u0002\u000289\u0007\u0018\u0002\u00029;\u0007",
    "\u0004\u0002\u0002:<\u0007\u0018\u0002\u0002;:\u0003\u0002\u0002\u0002",
    "<=\u0003\u0002\u0002\u0002=;\u0003\u0002\u0002\u0002=>\u0003\u0002\u0002",
    "\u0002>?\u0003\u0002\u0002\u0002?@\u0007\u0005\u0002\u0002@\t\u0003",
    "\u0002\u0002\u0002AC\u0005\u0014\u000b\u0002BA\u0003\u0002\u0002\u0002",
    "CF\u0003\u0002\u0002\u0002DB\u0003\u0002\u0002\u0002DE\u0003\u0002\u0002",
    "\u0002EG\u0003\u0002\u0002\u0002FD\u0003\u0002\u0002\u0002GH\u0007\u0018",
    "\u0002\u0002HK\u0007\b\u0002\u0002IL\u0005\f\u0007\u0002JL\u0005\u000e",
    "\b\u0002KI\u0003\u0002\u0002\u0002KJ\u0003\u0002\u0002\u0002LN\u0003",
    "\u0002\u0002\u0002MO\u0005\u0012\n\u0002NM\u0003\u0002\u0002\u0002N",
    "O\u0003\u0002\u0002\u0002OR\u0003\u0002\u0002\u0002PQ\u0007\t\u0002",
    "\u0002QS\u0005\u0016\f\u0002RP\u0003\u0002\u0002\u0002RS\u0003\u0002",
    "\u0002\u0002S\u000b\u0003\u0002\u0002\u0002TU\t\u0002\u0002\u0002U\r",
    "\u0003\u0002\u0002\u0002VW\u0007\u0011\u0002\u0002WX\u0005\f\u0007\u0002",
    "XY\u0007\u0012\u0002\u0002Y\u000f\u0003\u0002\u0002\u0002Z\\\u0007\u0013",
    "\u0002\u0002[]\t\u0003\u0002\u0002\\[\u0003\u0002\u0002\u0002]^\u0003",
    "\u0002\u0002\u0002^\\\u0003\u0002\u0002\u0002^_\u0003\u0002\u0002\u0002",
    "_`\u0003\u0002\u0002\u0002`a\u0007\u0016\u0002\u0002a\u0011\u0003\u0002",
    "\u0002\u0002bc\u0007\u0013\u0002\u0002cd\u0007\u0015\u0002\u0002de\u0007",
    "\u0016\u0002\u0002e\u0013\u0003\u0002\u0002\u0002fg\u0007\u0017\u0002",
    "\u0002gl\u0007\u0018\u0002\u0002hi\u0007\u0013\u0002\u0002ij\u0005\u0016",
    "\f\u0002jk\u0007\u0016\u0002\u0002km\u0003\u0002\u0002\u0002lh\u0003",
    "\u0002\u0002\u0002lm\u0003\u0002\u0002\u0002m\u0015\u0003\u0002\u0002",
    "\u0002no\t\u0004\u0002\u0002o\u0017\u0003\u0002\u0002\u0002\u000e\u001b",
    "\u001d&-3=DKNR^l"].join("");


var atn = new antlr4.atn.ATNDeserializer().deserialize(serializedATN);

var decisionsToDFA = atn.decisionToState.map( function(ds, index) { return new antlr4.dfa.DFA(ds, index); });

var sharedContextCache = new antlr4.PredictionContextCache();

var literalNames = [ null, "'enum'", "'{'", "'}'", "'class'", "'union'", 
                     "':'", "'='", "'string'", "'short'", "'boolean'", "'byte'", 
                     "'float'", "'long'", "'int'", "'['", "']'", "'('", 
                     "'primary'", "'deprecated'", "')'", "'@'", null, null, 
                     null, null, null, null, "';'" ];

var symbolicNames = [ null, null, null, null, null, null, null, null, null, 
                      null, null, null, null, null, null, null, null, null, 
                      null, null, null, null, "IDENTIFIER", "STRING", "NUMBER", 
                      "BOOLEAN", "WHITESPACE", "LINE_COMMENT", "SEMICOLON" ];

var ruleNames =  [ "compilation", "enumType", "classType", "unionType", 
                   "fieldType", "dataType", "arrayType", "classOptions", 
                   "fieldOptions", "annotation", "value" ];

function DynabuffersParser (input) {
	antlr4.Parser.call(this, input);
    this._interp = new antlr4.atn.ParserATNSimulator(this, atn, decisionsToDFA, sharedContextCache);
    this.ruleNames = ruleNames;
    this.literalNames = literalNames;
    this.symbolicNames = symbolicNames;
    return this;
}

DynabuffersParser.prototype = Object.create(antlr4.Parser.prototype);
DynabuffersParser.prototype.constructor = DynabuffersParser;

Object.defineProperty(DynabuffersParser.prototype, "atn", {
	get : function() {
		return atn;
	}
});

DynabuffersParser.EOF = antlr4.Token.EOF;
DynabuffersParser.T__0 = 1;
DynabuffersParser.T__1 = 2;
DynabuffersParser.T__2 = 3;
DynabuffersParser.T__3 = 4;
DynabuffersParser.T__4 = 5;
DynabuffersParser.T__5 = 6;
DynabuffersParser.T__6 = 7;
DynabuffersParser.T__7 = 8;
DynabuffersParser.T__8 = 9;
DynabuffersParser.T__9 = 10;
DynabuffersParser.T__10 = 11;
DynabuffersParser.T__11 = 12;
DynabuffersParser.T__12 = 13;
DynabuffersParser.T__13 = 14;
DynabuffersParser.T__14 = 15;
DynabuffersParser.T__15 = 16;
DynabuffersParser.T__16 = 17;
DynabuffersParser.T__17 = 18;
DynabuffersParser.T__18 = 19;
DynabuffersParser.T__19 = 20;
DynabuffersParser.T__20 = 21;
DynabuffersParser.IDENTIFIER = 22;
DynabuffersParser.STRING = 23;
DynabuffersParser.NUMBER = 24;
DynabuffersParser.BOOLEAN = 25;
DynabuffersParser.WHITESPACE = 26;
DynabuffersParser.LINE_COMMENT = 27;
DynabuffersParser.SEMICOLON = 28;

DynabuffersParser.RULE_compilation = 0;
DynabuffersParser.RULE_enumType = 1;
DynabuffersParser.RULE_classType = 2;
DynabuffersParser.RULE_unionType = 3;
DynabuffersParser.RULE_fieldType = 4;
DynabuffersParser.RULE_dataType = 5;
DynabuffersParser.RULE_arrayType = 6;
DynabuffersParser.RULE_classOptions = 7;
DynabuffersParser.RULE_fieldOptions = 8;
DynabuffersParser.RULE_annotation = 9;
DynabuffersParser.RULE_value = 10;


function CompilationContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DynabuffersParser.RULE_compilation;
    return this;
}

CompilationContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
CompilationContext.prototype.constructor = CompilationContext;

CompilationContext.prototype.enumType = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(EnumTypeContext);
    } else {
        return this.getTypedRuleContext(EnumTypeContext,i);
    }
};

CompilationContext.prototype.classType = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(ClassTypeContext);
    } else {
        return this.getTypedRuleContext(ClassTypeContext,i);
    }
};

CompilationContext.prototype.unionType = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(UnionTypeContext);
    } else {
        return this.getTypedRuleContext(UnionTypeContext,i);
    }
};

CompilationContext.prototype.enterRule = function(listener) {
    if(listener instanceof DynabuffersListener ) {
        listener.enterCompilation(this);
	}
};

CompilationContext.prototype.exitRule = function(listener) {
    if(listener instanceof DynabuffersListener ) {
        listener.exitCompilation(this);
	}
};

CompilationContext.prototype.accept = function(visitor) {
    if ( visitor instanceof DynabuffersVisitor ) {
        return visitor.visitCompilation(this);
    } else {
        return visitor.visitChildren(this);
    }
};




DynabuffersParser.CompilationContext = CompilationContext;

DynabuffersParser.prototype.compilation = function() {

    var localctx = new CompilationContext(this, this._ctx, this.state);
    this.enterRule(localctx, 0, DynabuffersParser.RULE_compilation);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 27;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while((((_la) & ~0x1f) == 0 && ((1 << _la) & ((1 << DynabuffersParser.T__0) | (1 << DynabuffersParser.T__3) | (1 << DynabuffersParser.T__4))) !== 0)) {
            this.state = 25;
            this._errHandler.sync(this);
            switch(this._input.LA(1)) {
            case DynabuffersParser.T__0:
                this.state = 22;
                this.enumType();
                break;
            case DynabuffersParser.T__3:
                this.state = 23;
                this.classType();
                break;
            case DynabuffersParser.T__4:
                this.state = 24;
                this.unionType();
                break;
            default:
                throw new antlr4.error.NoViableAltException(this);
            }
            this.state = 29;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};


function EnumTypeContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DynabuffersParser.RULE_enumType;
    return this;
}

EnumTypeContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
EnumTypeContext.prototype.constructor = EnumTypeContext;

EnumTypeContext.prototype.IDENTIFIER = function(i) {
	if(i===undefined) {
		i = null;
	}
    if(i===null) {
        return this.getTokens(DynabuffersParser.IDENTIFIER);
    } else {
        return this.getToken(DynabuffersParser.IDENTIFIER, i);
    }
};


EnumTypeContext.prototype.enterRule = function(listener) {
    if(listener instanceof DynabuffersListener ) {
        listener.enterEnumType(this);
	}
};

EnumTypeContext.prototype.exitRule = function(listener) {
    if(listener instanceof DynabuffersListener ) {
        listener.exitEnumType(this);
	}
};

EnumTypeContext.prototype.accept = function(visitor) {
    if ( visitor instanceof DynabuffersVisitor ) {
        return visitor.visitEnumType(this);
    } else {
        return visitor.visitChildren(this);
    }
};




DynabuffersParser.EnumTypeContext = EnumTypeContext;

DynabuffersParser.prototype.enumType = function() {

    var localctx = new EnumTypeContext(this, this._ctx, this.state);
    this.enterRule(localctx, 2, DynabuffersParser.RULE_enumType);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 30;
        this.match(DynabuffersParser.T__0);
        this.state = 31;
        this.match(DynabuffersParser.IDENTIFIER);
        this.state = 32;
        this.match(DynabuffersParser.T__1);
        this.state = 34; 
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        do {
            this.state = 33;
            this.match(DynabuffersParser.IDENTIFIER);
            this.state = 36; 
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        } while(_la===DynabuffersParser.IDENTIFIER);
        this.state = 38;
        this.match(DynabuffersParser.T__2);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};


function ClassTypeContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DynabuffersParser.RULE_classType;
    return this;
}

ClassTypeContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
ClassTypeContext.prototype.constructor = ClassTypeContext;

ClassTypeContext.prototype.IDENTIFIER = function() {
    return this.getToken(DynabuffersParser.IDENTIFIER, 0);
};

ClassTypeContext.prototype.classOptions = function() {
    return this.getTypedRuleContext(ClassOptionsContext,0);
};

ClassTypeContext.prototype.fieldType = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(FieldTypeContext);
    } else {
        return this.getTypedRuleContext(FieldTypeContext,i);
    }
};

ClassTypeContext.prototype.enterRule = function(listener) {
    if(listener instanceof DynabuffersListener ) {
        listener.enterClassType(this);
	}
};

ClassTypeContext.prototype.exitRule = function(listener) {
    if(listener instanceof DynabuffersListener ) {
        listener.exitClassType(this);
	}
};

ClassTypeContext.prototype.accept = function(visitor) {
    if ( visitor instanceof DynabuffersVisitor ) {
        return visitor.visitClassType(this);
    } else {
        return visitor.visitChildren(this);
    }
};




DynabuffersParser.ClassTypeContext = ClassTypeContext;

DynabuffersParser.prototype.classType = function() {

    var localctx = new ClassTypeContext(this, this._ctx, this.state);
    this.enterRule(localctx, 4, DynabuffersParser.RULE_classType);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 40;
        this.match(DynabuffersParser.T__3);
        this.state = 41;
        this.match(DynabuffersParser.IDENTIFIER);
        this.state = 43;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===DynabuffersParser.T__16) {
            this.state = 42;
            this.classOptions();
        }

        this.state = 45;
        this.match(DynabuffersParser.T__1);
        this.state = 47; 
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        do {
            this.state = 46;
            this.fieldType();
            this.state = 49; 
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        } while(_la===DynabuffersParser.T__20 || _la===DynabuffersParser.IDENTIFIER);
        this.state = 51;
        this.match(DynabuffersParser.T__2);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};


function UnionTypeContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DynabuffersParser.RULE_unionType;
    return this;
}

UnionTypeContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
UnionTypeContext.prototype.constructor = UnionTypeContext;

UnionTypeContext.prototype.IDENTIFIER = function(i) {
	if(i===undefined) {
		i = null;
	}
    if(i===null) {
        return this.getTokens(DynabuffersParser.IDENTIFIER);
    } else {
        return this.getToken(DynabuffersParser.IDENTIFIER, i);
    }
};


UnionTypeContext.prototype.enterRule = function(listener) {
    if(listener instanceof DynabuffersListener ) {
        listener.enterUnionType(this);
	}
};

UnionTypeContext.prototype.exitRule = function(listener) {
    if(listener instanceof DynabuffersListener ) {
        listener.exitUnionType(this);
	}
};

UnionTypeContext.prototype.accept = function(visitor) {
    if ( visitor instanceof DynabuffersVisitor ) {
        return visitor.visitUnionType(this);
    } else {
        return visitor.visitChildren(this);
    }
};




DynabuffersParser.UnionTypeContext = UnionTypeContext;

DynabuffersParser.prototype.unionType = function() {

    var localctx = new UnionTypeContext(this, this._ctx, this.state);
    this.enterRule(localctx, 6, DynabuffersParser.RULE_unionType);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 53;
        this.match(DynabuffersParser.T__4);
        this.state = 54;
        this.match(DynabuffersParser.IDENTIFIER);
        this.state = 55;
        this.match(DynabuffersParser.T__1);
        this.state = 57; 
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        do {
            this.state = 56;
            this.match(DynabuffersParser.IDENTIFIER);
            this.state = 59; 
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        } while(_la===DynabuffersParser.IDENTIFIER);
        this.state = 61;
        this.match(DynabuffersParser.T__2);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};


function FieldTypeContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DynabuffersParser.RULE_fieldType;
    return this;
}

FieldTypeContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
FieldTypeContext.prototype.constructor = FieldTypeContext;

FieldTypeContext.prototype.IDENTIFIER = function() {
    return this.getToken(DynabuffersParser.IDENTIFIER, 0);
};

FieldTypeContext.prototype.dataType = function() {
    return this.getTypedRuleContext(DataTypeContext,0);
};

FieldTypeContext.prototype.arrayType = function() {
    return this.getTypedRuleContext(ArrayTypeContext,0);
};

FieldTypeContext.prototype.annotation = function(i) {
    if(i===undefined) {
        i = null;
    }
    if(i===null) {
        return this.getTypedRuleContexts(AnnotationContext);
    } else {
        return this.getTypedRuleContext(AnnotationContext,i);
    }
};

FieldTypeContext.prototype.fieldOptions = function() {
    return this.getTypedRuleContext(FieldOptionsContext,0);
};

FieldTypeContext.prototype.value = function() {
    return this.getTypedRuleContext(ValueContext,0);
};

FieldTypeContext.prototype.enterRule = function(listener) {
    if(listener instanceof DynabuffersListener ) {
        listener.enterFieldType(this);
	}
};

FieldTypeContext.prototype.exitRule = function(listener) {
    if(listener instanceof DynabuffersListener ) {
        listener.exitFieldType(this);
	}
};

FieldTypeContext.prototype.accept = function(visitor) {
    if ( visitor instanceof DynabuffersVisitor ) {
        return visitor.visitFieldType(this);
    } else {
        return visitor.visitChildren(this);
    }
};




DynabuffersParser.FieldTypeContext = FieldTypeContext;

DynabuffersParser.prototype.fieldType = function() {

    var localctx = new FieldTypeContext(this, this._ctx, this.state);
    this.enterRule(localctx, 8, DynabuffersParser.RULE_fieldType);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 66;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        while(_la===DynabuffersParser.T__20) {
            this.state = 63;
            this.annotation();
            this.state = 68;
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        }
        this.state = 69;
        this.match(DynabuffersParser.IDENTIFIER);
        this.state = 70;
        this.match(DynabuffersParser.T__5);
        this.state = 73;
        this._errHandler.sync(this);
        switch(this._input.LA(1)) {
        case DynabuffersParser.T__7:
        case DynabuffersParser.T__8:
        case DynabuffersParser.T__9:
        case DynabuffersParser.T__10:
        case DynabuffersParser.T__11:
        case DynabuffersParser.T__12:
        case DynabuffersParser.T__13:
        case DynabuffersParser.IDENTIFIER:
            this.state = 71;
            this.dataType();
            break;
        case DynabuffersParser.T__14:
            this.state = 72;
            this.arrayType();
            break;
        default:
            throw new antlr4.error.NoViableAltException(this);
        }
        this.state = 76;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===DynabuffersParser.T__16) {
            this.state = 75;
            this.fieldOptions();
        }

        this.state = 80;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===DynabuffersParser.T__6) {
            this.state = 78;
            this.match(DynabuffersParser.T__6);
            this.state = 79;
            this.value();
        }

    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};


function DataTypeContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DynabuffersParser.RULE_dataType;
    return this;
}

DataTypeContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
DataTypeContext.prototype.constructor = DataTypeContext;

DataTypeContext.prototype.IDENTIFIER = function() {
    return this.getToken(DynabuffersParser.IDENTIFIER, 0);
};

DataTypeContext.prototype.enterRule = function(listener) {
    if(listener instanceof DynabuffersListener ) {
        listener.enterDataType(this);
	}
};

DataTypeContext.prototype.exitRule = function(listener) {
    if(listener instanceof DynabuffersListener ) {
        listener.exitDataType(this);
	}
};

DataTypeContext.prototype.accept = function(visitor) {
    if ( visitor instanceof DynabuffersVisitor ) {
        return visitor.visitDataType(this);
    } else {
        return visitor.visitChildren(this);
    }
};




DynabuffersParser.DataTypeContext = DataTypeContext;

DynabuffersParser.prototype.dataType = function() {

    var localctx = new DataTypeContext(this, this._ctx, this.state);
    this.enterRule(localctx, 10, DynabuffersParser.RULE_dataType);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 82;
        _la = this._input.LA(1);
        if(!((((_la) & ~0x1f) == 0 && ((1 << _la) & ((1 << DynabuffersParser.T__7) | (1 << DynabuffersParser.T__8) | (1 << DynabuffersParser.T__9) | (1 << DynabuffersParser.T__10) | (1 << DynabuffersParser.T__11) | (1 << DynabuffersParser.T__12) | (1 << DynabuffersParser.T__13) | (1 << DynabuffersParser.IDENTIFIER))) !== 0))) {
        this._errHandler.recoverInline(this);
        }
        else {
        	this._errHandler.reportMatch(this);
            this.consume();
        }
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};


function ArrayTypeContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DynabuffersParser.RULE_arrayType;
    return this;
}

ArrayTypeContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
ArrayTypeContext.prototype.constructor = ArrayTypeContext;

ArrayTypeContext.prototype.dataType = function() {
    return this.getTypedRuleContext(DataTypeContext,0);
};

ArrayTypeContext.prototype.enterRule = function(listener) {
    if(listener instanceof DynabuffersListener ) {
        listener.enterArrayType(this);
	}
};

ArrayTypeContext.prototype.exitRule = function(listener) {
    if(listener instanceof DynabuffersListener ) {
        listener.exitArrayType(this);
	}
};

ArrayTypeContext.prototype.accept = function(visitor) {
    if ( visitor instanceof DynabuffersVisitor ) {
        return visitor.visitArrayType(this);
    } else {
        return visitor.visitChildren(this);
    }
};




DynabuffersParser.ArrayTypeContext = ArrayTypeContext;

DynabuffersParser.prototype.arrayType = function() {

    var localctx = new ArrayTypeContext(this, this._ctx, this.state);
    this.enterRule(localctx, 12, DynabuffersParser.RULE_arrayType);
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 84;
        this.match(DynabuffersParser.T__14);
        this.state = 85;
        this.dataType();
        this.state = 86;
        this.match(DynabuffersParser.T__15);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};


function ClassOptionsContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DynabuffersParser.RULE_classOptions;
    return this;
}

ClassOptionsContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
ClassOptionsContext.prototype.constructor = ClassOptionsContext;


ClassOptionsContext.prototype.enterRule = function(listener) {
    if(listener instanceof DynabuffersListener ) {
        listener.enterClassOptions(this);
	}
};

ClassOptionsContext.prototype.exitRule = function(listener) {
    if(listener instanceof DynabuffersListener ) {
        listener.exitClassOptions(this);
	}
};

ClassOptionsContext.prototype.accept = function(visitor) {
    if ( visitor instanceof DynabuffersVisitor ) {
        return visitor.visitClassOptions(this);
    } else {
        return visitor.visitChildren(this);
    }
};




DynabuffersParser.ClassOptionsContext = ClassOptionsContext;

DynabuffersParser.prototype.classOptions = function() {

    var localctx = new ClassOptionsContext(this, this._ctx, this.state);
    this.enterRule(localctx, 14, DynabuffersParser.RULE_classOptions);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 88;
        this.match(DynabuffersParser.T__16);
        this.state = 90; 
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        do {
            this.state = 89;
            _la = this._input.LA(1);
            if(!(_la===DynabuffersParser.T__17 || _la===DynabuffersParser.T__18)) {
            this._errHandler.recoverInline(this);
            }
            else {
            	this._errHandler.reportMatch(this);
                this.consume();
            }
            this.state = 92; 
            this._errHandler.sync(this);
            _la = this._input.LA(1);
        } while(_la===DynabuffersParser.T__17 || _la===DynabuffersParser.T__18);
        this.state = 94;
        this.match(DynabuffersParser.T__19);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};


function FieldOptionsContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DynabuffersParser.RULE_fieldOptions;
    return this;
}

FieldOptionsContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
FieldOptionsContext.prototype.constructor = FieldOptionsContext;


FieldOptionsContext.prototype.enterRule = function(listener) {
    if(listener instanceof DynabuffersListener ) {
        listener.enterFieldOptions(this);
	}
};

FieldOptionsContext.prototype.exitRule = function(listener) {
    if(listener instanceof DynabuffersListener ) {
        listener.exitFieldOptions(this);
	}
};

FieldOptionsContext.prototype.accept = function(visitor) {
    if ( visitor instanceof DynabuffersVisitor ) {
        return visitor.visitFieldOptions(this);
    } else {
        return visitor.visitChildren(this);
    }
};




DynabuffersParser.FieldOptionsContext = FieldOptionsContext;

DynabuffersParser.prototype.fieldOptions = function() {

    var localctx = new FieldOptionsContext(this, this._ctx, this.state);
    this.enterRule(localctx, 16, DynabuffersParser.RULE_fieldOptions);
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 96;
        this.match(DynabuffersParser.T__16);
        this.state = 97;
        this.match(DynabuffersParser.T__18);
        this.state = 98;
        this.match(DynabuffersParser.T__19);
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};


function AnnotationContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DynabuffersParser.RULE_annotation;
    return this;
}

AnnotationContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
AnnotationContext.prototype.constructor = AnnotationContext;

AnnotationContext.prototype.IDENTIFIER = function() {
    return this.getToken(DynabuffersParser.IDENTIFIER, 0);
};

AnnotationContext.prototype.value = function() {
    return this.getTypedRuleContext(ValueContext,0);
};

AnnotationContext.prototype.enterRule = function(listener) {
    if(listener instanceof DynabuffersListener ) {
        listener.enterAnnotation(this);
	}
};

AnnotationContext.prototype.exitRule = function(listener) {
    if(listener instanceof DynabuffersListener ) {
        listener.exitAnnotation(this);
	}
};

AnnotationContext.prototype.accept = function(visitor) {
    if ( visitor instanceof DynabuffersVisitor ) {
        return visitor.visitAnnotation(this);
    } else {
        return visitor.visitChildren(this);
    }
};




DynabuffersParser.AnnotationContext = AnnotationContext;

DynabuffersParser.prototype.annotation = function() {

    var localctx = new AnnotationContext(this, this._ctx, this.state);
    this.enterRule(localctx, 18, DynabuffersParser.RULE_annotation);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 100;
        this.match(DynabuffersParser.T__20);
        this.state = 101;
        this.match(DynabuffersParser.IDENTIFIER);
        this.state = 106;
        this._errHandler.sync(this);
        _la = this._input.LA(1);
        if(_la===DynabuffersParser.T__16) {
            this.state = 102;
            this.match(DynabuffersParser.T__16);
            this.state = 103;
            this.value();
            this.state = 104;
            this.match(DynabuffersParser.T__19);
        }

    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};


function ValueContext(parser, parent, invokingState) {
	if(parent===undefined) {
	    parent = null;
	}
	if(invokingState===undefined || invokingState===null) {
		invokingState = -1;
	}
	antlr4.ParserRuleContext.call(this, parent, invokingState);
    this.parser = parser;
    this.ruleIndex = DynabuffersParser.RULE_value;
    return this;
}

ValueContext.prototype = Object.create(antlr4.ParserRuleContext.prototype);
ValueContext.prototype.constructor = ValueContext;

ValueContext.prototype.STRING = function() {
    return this.getToken(DynabuffersParser.STRING, 0);
};

ValueContext.prototype.NUMBER = function() {
    return this.getToken(DynabuffersParser.NUMBER, 0);
};

ValueContext.prototype.BOOLEAN = function() {
    return this.getToken(DynabuffersParser.BOOLEAN, 0);
};

ValueContext.prototype.IDENTIFIER = function() {
    return this.getToken(DynabuffersParser.IDENTIFIER, 0);
};

ValueContext.prototype.enterRule = function(listener) {
    if(listener instanceof DynabuffersListener ) {
        listener.enterValue(this);
	}
};

ValueContext.prototype.exitRule = function(listener) {
    if(listener instanceof DynabuffersListener ) {
        listener.exitValue(this);
	}
};

ValueContext.prototype.accept = function(visitor) {
    if ( visitor instanceof DynabuffersVisitor ) {
        return visitor.visitValue(this);
    } else {
        return visitor.visitChildren(this);
    }
};




DynabuffersParser.ValueContext = ValueContext;

DynabuffersParser.prototype.value = function() {

    var localctx = new ValueContext(this, this._ctx, this.state);
    this.enterRule(localctx, 20, DynabuffersParser.RULE_value);
    var _la = 0; // Token type
    try {
        this.enterOuterAlt(localctx, 1);
        this.state = 108;
        _la = this._input.LA(1);
        if(!((((_la) & ~0x1f) == 0 && ((1 << _la) & ((1 << DynabuffersParser.IDENTIFIER) | (1 << DynabuffersParser.STRING) | (1 << DynabuffersParser.NUMBER) | (1 << DynabuffersParser.BOOLEAN))) !== 0))) {
        this._errHandler.recoverInline(this);
        }
        else {
        	this._errHandler.reportMatch(this);
            this.consume();
        }
    } catch (re) {
    	if(re instanceof antlr4.error.RecognitionException) {
	        localctx.exception = re;
	        this._errHandler.reportError(this, re);
	        this._errHandler.recover(this, re);
	    } else {
	    	throw re;
	    }
    } finally {
        this.exitRule();
    }
    return localctx;
};


exports.DynabuffersParser = DynabuffersParser;
