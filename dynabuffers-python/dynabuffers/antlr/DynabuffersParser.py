# Generated from Dynabuffers.g4 by ANTLR 4.7.2
# encoding: utf-8
import sys
from io import StringIO

from antlr4 import *
from typing.io import TextIO


def serializedATN():
    with StringIO() as buf:
        buf.write("\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\36")
        buf.write("U\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b")
        buf.write("\t\b\3\2\3\2\3\2\7\2\24\n\2\f\2\16\2\27\13\2\3\3\3\3\3")
        buf.write("\3\3\3\6\3\35\n\3\r\3\16\3\36\3\3\3\3\3\4\3\4\3\4\3\4")
        buf.write("\6\4\'\n\4\r\4\16\4(\3\4\5\4,\n\4\3\4\3\4\6\4\60\n\4\r")
        buf.write("\4\16\4\61\3\4\3\4\3\5\3\5\3\5\3\5\6\5:\n\5\r\5\16\5;")
        buf.write("\3\5\3\5\3\6\3\6\3\6\3\6\5\6D\n\6\3\6\3\6\3\6\5\6I\n\6")
        buf.write("\3\6\3\6\5\6M\n\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\2\2\t\2")
        buf.write("\4\6\b\n\f\16\2\5\3\2\b\t\3\2\27\32\4\2\16\24\27\27\2")
        buf.write("X\2\25\3\2\2\2\4\30\3\2\2\2\6\"\3\2\2\2\b\65\3\2\2\2\n")
        buf.write("?\3\2\2\2\fN\3\2\2\2\16P\3\2\2\2\20\24\5\4\3\2\21\24\5")
        buf.write("\6\4\2\22\24\5\b\5\2\23\20\3\2\2\2\23\21\3\2\2\2\23\22")
        buf.write("\3\2\2\2\24\27\3\2\2\2\25\23\3\2\2\2\25\26\3\2\2\2\26")
        buf.write("\3\3\2\2\2\27\25\3\2\2\2\30\31\7\3\2\2\31\32\7\27\2\2")
        buf.write("\32\34\7\4\2\2\33\35\7\27\2\2\34\33\3\2\2\2\35\36\3\2")
        buf.write("\2\2\36\34\3\2\2\2\36\37\3\2\2\2\37 \3\2\2\2 !\7\5\2\2")
        buf.write("!\5\3\2\2\2\"#\7\6\2\2#+\7\27\2\2$&\7\7\2\2%\'\t\2\2\2")
        buf.write("&%\3\2\2\2\'(\3\2\2\2(&\3\2\2\2()\3\2\2\2)*\3\2\2\2*,")
        buf.write("\7\n\2\2+$\3\2\2\2+,\3\2\2\2,-\3\2\2\2-/\7\4\2\2.\60\5")
        buf.write("\n\6\2/.\3\2\2\2\60\61\3\2\2\2\61/\3\2\2\2\61\62\3\2\2")
        buf.write("\2\62\63\3\2\2\2\63\64\7\5\2\2\64\7\3\2\2\2\65\66\7\13")
        buf.write("\2\2\66\67\7\27\2\2\679\7\4\2\28:\7\27\2\298\3\2\2\2:")
        buf.write(";\3\2\2\2;9\3\2\2\2;<\3\2\2\2<=\3\2\2\2=>\7\5\2\2>\t\3")
        buf.write("\2\2\2?@\7\27\2\2@C\7\f\2\2AD\5\f\7\2BD\5\16\b\2CA\3\2")
        buf.write("\2\2CB\3\2\2\2DH\3\2\2\2EF\7\7\2\2FG\7\t\2\2GI\7\n\2\2")
        buf.write("HE\3\2\2\2HI\3\2\2\2IL\3\2\2\2JK\7\r\2\2KM\t\3\2\2LJ\3")
        buf.write("\2\2\2LM\3\2\2\2M\13\3\2\2\2NO\t\4\2\2O\r\3\2\2\2PQ\7")
        buf.write("\25\2\2QR\5\f\7\2RS\7\26\2\2S\17\3\2\2\2\f\23\25\36(+")
        buf.write("\61;CHL")
        return buf.getvalue()


class DynabuffersParser ( Parser ):

    grammarFileName = "Dynabuffers.g4"

    atn = ATNDeserializer().deserialize(serializedATN())

    decisionsToDFA = [ DFA(ds, i) for i, ds in enumerate(atn.decisionToState) ]

    sharedContextCache = PredictionContextCache()

    literalNames = [ "<INVALID>", "'enum'", "'{'", "'}'", "'class'", "'('",
                     "'primary'", "'deprecated'", "')'", "'union'", "':'",
                     "'='", "'string'", "'short'", "'boolean'", "'byte'",
                     "'float'", "'long'", "'int'", "'['", "']'", "<INVALID>",
                     "<INVALID>", "<INVALID>", "<INVALID>", "<INVALID>",
                     "<INVALID>", "' '", "';'" ]

    symbolicNames = [ "<INVALID>", "<INVALID>", "<INVALID>", "<INVALID>",
                      "<INVALID>", "<INVALID>", "<INVALID>", "<INVALID>",
                      "<INVALID>", "<INVALID>", "<INVALID>", "<INVALID>",
                      "<INVALID>", "<INVALID>", "<INVALID>", "<INVALID>",
                      "<INVALID>", "<INVALID>", "<INVALID>", "<INVALID>",
                      "<INVALID>", "IDENTIFIER", "STRING", "NUMBER", "BOOLEAN",
                      "WS", "LINE_COMMENT", "WHITESPACE", "SEMICOLON" ]

    RULE_compilation = 0
    RULE_enumType = 1
    RULE_classType = 2
    RULE_unionType = 3
    RULE_fieldType = 4
    RULE_dataType = 5
    RULE_arrayType = 6

    ruleNames =  [ "compilation", "enumType", "classType", "unionType",
                   "fieldType", "dataType", "arrayType" ]

    EOF = Token.EOF
    T__0=1
    T__1=2
    T__2=3
    T__3=4
    T__4=5
    T__5=6
    T__6=7
    T__7=8
    T__8=9
    T__9=10
    T__10=11
    T__11=12
    T__12=13
    T__13=14
    T__14=15
    T__15=16
    T__16=17
    T__17=18
    T__18=19
    T__19=20
    IDENTIFIER=21
    STRING=22
    NUMBER=23
    BOOLEAN=24
    WS=25
    LINE_COMMENT=26
    WHITESPACE=27
    SEMICOLON=28

    def __init__(self, input:TokenStream, output:TextIO = sys.stdout):
        super().__init__(input, output)
        self.checkVersion("4.7.2")
        self._interp = ParserATNSimulator(self, self.atn, self.decisionsToDFA, self.sharedContextCache)
        self._predicates = None




    class CompilationContext(ParserRuleContext):

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def enumType(self, i:int=None):
            if i is None:
                return self.getTypedRuleContexts(DynabuffersParser.EnumTypeContext)
            else:
                return self.getTypedRuleContext(DynabuffersParser.EnumTypeContext,i)


        def classType(self, i:int=None):
            if i is None:
                return self.getTypedRuleContexts(DynabuffersParser.ClassTypeContext)
            else:
                return self.getTypedRuleContext(DynabuffersParser.ClassTypeContext,i)


        def unionType(self, i:int=None):
            if i is None:
                return self.getTypedRuleContexts(DynabuffersParser.UnionTypeContext)
            else:
                return self.getTypedRuleContext(DynabuffersParser.UnionTypeContext,i)


        def getRuleIndex(self):
            return DynabuffersParser.RULE_compilation

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterCompilation" ):
                listener.enterCompilation(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitCompilation" ):
                listener.exitCompilation(self)

        def accept(self, visitor:ParseTreeVisitor):
            if hasattr( visitor, "visitCompilation" ):
                return visitor.visitCompilation(self)
            else:
                return visitor.visitChildren(self)




    def compilation(self):

        localctx = DynabuffersParser.CompilationContext(self, self._ctx, self.state)
        self.enterRule(localctx, 0, self.RULE_compilation)
        self._la = 0 # Token type
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 19
            self._errHandler.sync(self)
            _la = self._input.LA(1)
            while (((_la) & ~0x3f) == 0 and ((1 << _la) & ((1 << DynabuffersParser.T__0) | (1 << DynabuffersParser.T__3) | (1 << DynabuffersParser.T__8))) != 0):
                self.state = 17
                self._errHandler.sync(self)
                token = self._input.LA(1)
                if token in [DynabuffersParser.T__0]:
                    self.state = 14
                    self.enumType()
                    pass
                elif token in [DynabuffersParser.T__3]:
                    self.state = 15
                    self.classType()
                    pass
                elif token in [DynabuffersParser.T__8]:
                    self.state = 16
                    self.unionType()
                    pass
                else:
                    raise NoViableAltException(self)

                self.state = 21
                self._errHandler.sync(self)
                _la = self._input.LA(1)

        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx


    class EnumTypeContext(ParserRuleContext):

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def IDENTIFIER(self, i:int=None):
            if i is None:
                return self.getTokens(DynabuffersParser.IDENTIFIER)
            else:
                return self.getToken(DynabuffersParser.IDENTIFIER, i)

        def getRuleIndex(self):
            return DynabuffersParser.RULE_enumType

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterEnumType" ):
                listener.enterEnumType(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitEnumType" ):
                listener.exitEnumType(self)

        def accept(self, visitor:ParseTreeVisitor):
            if hasattr( visitor, "visitEnumType" ):
                return visitor.visitEnumType(self)
            else:
                return visitor.visitChildren(self)




    def enumType(self):

        localctx = DynabuffersParser.EnumTypeContext(self, self._ctx, self.state)
        self.enterRule(localctx, 2, self.RULE_enumType)
        self._la = 0 # Token type
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 22
            self.match(DynabuffersParser.T__0)
            self.state = 23
            self.match(DynabuffersParser.IDENTIFIER)
            self.state = 24
            self.match(DynabuffersParser.T__1)
            self.state = 26
            self._errHandler.sync(self)
            _la = self._input.LA(1)
            while True:
                self.state = 25
                self.match(DynabuffersParser.IDENTIFIER)
                self.state = 28
                self._errHandler.sync(self)
                _la = self._input.LA(1)
                if not (_la==DynabuffersParser.IDENTIFIER):
                    break

            self.state = 30
            self.match(DynabuffersParser.T__2)
        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx


    class ClassTypeContext(ParserRuleContext):

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def IDENTIFIER(self):
            return self.getToken(DynabuffersParser.IDENTIFIER, 0)

        def fieldType(self, i:int=None):
            if i is None:
                return self.getTypedRuleContexts(DynabuffersParser.FieldTypeContext)
            else:
                return self.getTypedRuleContext(DynabuffersParser.FieldTypeContext,i)


        def getRuleIndex(self):
            return DynabuffersParser.RULE_classType

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterClassType" ):
                listener.enterClassType(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitClassType" ):
                listener.exitClassType(self)

        def accept(self, visitor:ParseTreeVisitor):
            if hasattr( visitor, "visitClassType" ):
                return visitor.visitClassType(self)
            else:
                return visitor.visitChildren(self)




    def classType(self):

        localctx = DynabuffersParser.ClassTypeContext(self, self._ctx, self.state)
        self.enterRule(localctx, 4, self.RULE_classType)
        self._la = 0 # Token type
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 32
            self.match(DynabuffersParser.T__3)
            self.state = 33
            self.match(DynabuffersParser.IDENTIFIER)
            self.state = 41
            self._errHandler.sync(self)
            _la = self._input.LA(1)
            if _la==DynabuffersParser.T__4:
                self.state = 34
                self.match(DynabuffersParser.T__4)
                self.state = 36
                self._errHandler.sync(self)
                _la = self._input.LA(1)
                while True:
                    self.state = 35
                    _la = self._input.LA(1)
                    if not(_la==DynabuffersParser.T__5 or _la==DynabuffersParser.T__6):
                        self._errHandler.recoverInline(self)
                    else:
                        self._errHandler.reportMatch(self)
                        self.consume()
                    self.state = 38
                    self._errHandler.sync(self)
                    _la = self._input.LA(1)
                    if not (_la==DynabuffersParser.T__5 or _la==DynabuffersParser.T__6):
                        break

                self.state = 40
                self.match(DynabuffersParser.T__7)


            self.state = 43
            self.match(DynabuffersParser.T__1)
            self.state = 45
            self._errHandler.sync(self)
            _la = self._input.LA(1)
            while True:
                self.state = 44
                self.fieldType()
                self.state = 47
                self._errHandler.sync(self)
                _la = self._input.LA(1)
                if not (_la==DynabuffersParser.IDENTIFIER):
                    break

            self.state = 49
            self.match(DynabuffersParser.T__2)
        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx


    class UnionTypeContext(ParserRuleContext):

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def IDENTIFIER(self, i:int=None):
            if i is None:
                return self.getTokens(DynabuffersParser.IDENTIFIER)
            else:
                return self.getToken(DynabuffersParser.IDENTIFIER, i)

        def getRuleIndex(self):
            return DynabuffersParser.RULE_unionType

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterUnionType" ):
                listener.enterUnionType(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitUnionType" ):
                listener.exitUnionType(self)

        def accept(self, visitor:ParseTreeVisitor):
            if hasattr( visitor, "visitUnionType" ):
                return visitor.visitUnionType(self)
            else:
                return visitor.visitChildren(self)




    def unionType(self):

        localctx = DynabuffersParser.UnionTypeContext(self, self._ctx, self.state)
        self.enterRule(localctx, 6, self.RULE_unionType)
        self._la = 0 # Token type
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 51
            self.match(DynabuffersParser.T__8)
            self.state = 52
            self.match(DynabuffersParser.IDENTIFIER)
            self.state = 53
            self.match(DynabuffersParser.T__1)
            self.state = 55
            self._errHandler.sync(self)
            _la = self._input.LA(1)
            while True:
                self.state = 54
                self.match(DynabuffersParser.IDENTIFIER)
                self.state = 57
                self._errHandler.sync(self)
                _la = self._input.LA(1)
                if not (_la==DynabuffersParser.IDENTIFIER):
                    break

            self.state = 59
            self.match(DynabuffersParser.T__2)
        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx


    class FieldTypeContext(ParserRuleContext):

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def IDENTIFIER(self, i:int=None):
            if i is None:
                return self.getTokens(DynabuffersParser.IDENTIFIER)
            else:
                return self.getToken(DynabuffersParser.IDENTIFIER, i)

        def dataType(self):
            return self.getTypedRuleContext(DynabuffersParser.DataTypeContext,0)


        def arrayType(self):
            return self.getTypedRuleContext(DynabuffersParser.ArrayTypeContext,0)


        def STRING(self):
            return self.getToken(DynabuffersParser.STRING, 0)

        def NUMBER(self):
            return self.getToken(DynabuffersParser.NUMBER, 0)

        def BOOLEAN(self):
            return self.getToken(DynabuffersParser.BOOLEAN, 0)

        def getRuleIndex(self):
            return DynabuffersParser.RULE_fieldType

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterFieldType" ):
                listener.enterFieldType(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitFieldType" ):
                listener.exitFieldType(self)

        def accept(self, visitor:ParseTreeVisitor):
            if hasattr( visitor, "visitFieldType" ):
                return visitor.visitFieldType(self)
            else:
                return visitor.visitChildren(self)




    def fieldType(self):

        localctx = DynabuffersParser.FieldTypeContext(self, self._ctx, self.state)
        self.enterRule(localctx, 8, self.RULE_fieldType)
        self._la = 0 # Token type
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 61
            self.match(DynabuffersParser.IDENTIFIER)
            self.state = 62
            self.match(DynabuffersParser.T__9)
            self.state = 65
            self._errHandler.sync(self)
            token = self._input.LA(1)
            if token in [DynabuffersParser.T__11, DynabuffersParser.T__12, DynabuffersParser.T__13, DynabuffersParser.T__14, DynabuffersParser.T__15, DynabuffersParser.T__16, DynabuffersParser.T__17, DynabuffersParser.IDENTIFIER]:
                self.state = 63
                self.dataType()
                pass
            elif token in [DynabuffersParser.T__18]:
                self.state = 64
                self.arrayType()
                pass
            else:
                raise NoViableAltException(self)

            self.state = 70
            self._errHandler.sync(self)
            _la = self._input.LA(1)
            if _la==DynabuffersParser.T__4:
                self.state = 67
                self.match(DynabuffersParser.T__4)
                self.state = 68
                self.match(DynabuffersParser.T__6)
                self.state = 69
                self.match(DynabuffersParser.T__7)


            self.state = 74
            self._errHandler.sync(self)
            _la = self._input.LA(1)
            if _la==DynabuffersParser.T__10:
                self.state = 72
                self.match(DynabuffersParser.T__10)
                self.state = 73
                _la = self._input.LA(1)
                if not((((_la) & ~0x3f) == 0 and ((1 << _la) & ((1 << DynabuffersParser.IDENTIFIER) | (1 << DynabuffersParser.STRING) | (1 << DynabuffersParser.NUMBER) | (1 << DynabuffersParser.BOOLEAN))) != 0)):
                    self._errHandler.recoverInline(self)
                else:
                    self._errHandler.reportMatch(self)
                    self.consume()


        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx


    class DataTypeContext(ParserRuleContext):

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def IDENTIFIER(self):
            return self.getToken(DynabuffersParser.IDENTIFIER, 0)

        def getRuleIndex(self):
            return DynabuffersParser.RULE_dataType

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterDataType" ):
                listener.enterDataType(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitDataType" ):
                listener.exitDataType(self)

        def accept(self, visitor:ParseTreeVisitor):
            if hasattr( visitor, "visitDataType" ):
                return visitor.visitDataType(self)
            else:
                return visitor.visitChildren(self)




    def dataType(self):

        localctx = DynabuffersParser.DataTypeContext(self, self._ctx, self.state)
        self.enterRule(localctx, 10, self.RULE_dataType)
        self._la = 0 # Token type
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 76
            _la = self._input.LA(1)
            if not((((_la) & ~0x3f) == 0 and ((1 << _la) & ((1 << DynabuffersParser.T__11) | (1 << DynabuffersParser.T__12) | (1 << DynabuffersParser.T__13) | (1 << DynabuffersParser.T__14) | (1 << DynabuffersParser.T__15) | (1 << DynabuffersParser.T__16) | (1 << DynabuffersParser.T__17) | (1 << DynabuffersParser.IDENTIFIER))) != 0)):
                self._errHandler.recoverInline(self)
            else:
                self._errHandler.reportMatch(self)
                self.consume()
        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx


    class ArrayTypeContext(ParserRuleContext):

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def dataType(self):
            return self.getTypedRuleContext(DynabuffersParser.DataTypeContext,0)


        def getRuleIndex(self):
            return DynabuffersParser.RULE_arrayType

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterArrayType" ):
                listener.enterArrayType(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitArrayType" ):
                listener.exitArrayType(self)

        def accept(self, visitor:ParseTreeVisitor):
            if hasattr( visitor, "visitArrayType" ):
                return visitor.visitArrayType(self)
            else:
                return visitor.visitChildren(self)




    def arrayType(self):

        localctx = DynabuffersParser.ArrayTypeContext(self, self._ctx, self.state)
        self.enterRule(localctx, 12, self.RULE_arrayType)
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 78
            self.match(DynabuffersParser.T__18)
            self.state = 79
            self.dataType()
            self.state = 80
            self.match(DynabuffersParser.T__19)
        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx





