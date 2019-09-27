package dynabuffers

import dynabuffers.antlr.DynabuffersLexer
import dynabuffers.antlr.DynabuffersParser
import dynabuffers.ast.AbstractAST
import dynabuffers.exception.DynabuffersException
import dynabuffers.exception.DynabuffersExceptionListener
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.io.InputStream
import java.io.Reader
import java.nio.channels.ReadableByteChannel
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets.UTF_8

class Dynabuffers {

    companion object {

        @JvmStatic
        @JvmOverloads
        fun parse(string: String, charset: Charset = UTF_8) = DynabuffersEngine(parse(CharStreams.fromString(string), charset))

        @JvmStatic
        @JvmOverloads
        fun parse(stream: InputStream, charset: Charset = UTF_8) = DynabuffersEngine(parse(CharStreams.fromStream(stream), charset))

        @JvmStatic
        @JvmOverloads
        fun parse(reader: Reader, charset: Charset = UTF_8) = DynabuffersEngine(parse(CharStreams.fromReader(reader), charset))

        @JvmStatic
        @JvmOverloads
        fun parse(channel: ReadableByteChannel, charset: Charset = UTF_8) = DynabuffersEngine(parse(CharStreams.fromChannel(channel), charset))

        private fun parse(stream: CharStream, charset: Charset): List<AbstractAST> {
            val lexer = DynabuffersLexer(stream)
            val commonTokenStream = CommonTokenStream(lexer)
            val parser = DynabuffersParser(commonTokenStream)

            val errorListener = DynabuffersExceptionListener()
            parser.addErrorListener(errorListener)

            val visitor = DynabuffersVisitor(charset)
            val astList = visitor.visit(parser.compilation()) ?: throw DynabuffersException("invalid dynabuffers scheme")

            if (errorListener.get() != null) {
                throw DynabuffersException(errorListener.get())
            }
            return astList
        }
    }

}
