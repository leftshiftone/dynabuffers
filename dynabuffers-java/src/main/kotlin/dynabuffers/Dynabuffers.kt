package dynabuffers

import dynabuffers.antlr.DynabuffersLexer
import dynabuffers.antlr.DynabuffersParser
import dynabuffers.api.IType
import dynabuffers.exception.DynabuffersException
import dynabuffers.exception.DynabuffersExceptionListener
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CodePointCharStream
import org.antlr.v4.runtime.CommonTokenStream
import java.io.InputStream
import java.io.Reader
import java.nio.channels.ReadableByteChannel
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets.UTF_8
import java.util.*
import java.util.Optional.empty

/**
 * Factory class for creating a DynabuffersEngine instance.
 */
class Dynabuffers {

    companion object {

        @JvmStatic
        @JvmOverloads
        fun parse(string: String, namespace:Optional<String> = empty<String>(), charset: Charset = UTF_8): DynabuffersEngine {
            return DynabuffersEngine(parse(CharStreams.fromString(string), charset))
        }

        @JvmStatic
        @JvmOverloads
        fun parse(stream: InputStream, charset: Charset = UTF_8): DynabuffersEngine {
            return DynabuffersEngine(parse(CharStreams.fromStream(stream), charset))
        }

        @JvmStatic
        @JvmOverloads
        fun parse(reader: Reader, charset: Charset = UTF_8): DynabuffersEngine {
            return DynabuffersEngine(parse(CharStreams.fromReader(reader), charset))
        }

        @JvmStatic
        @JvmOverloads
        fun parse(channel: ReadableByteChannel, charset: Charset = UTF_8): DynabuffersEngine {
            return DynabuffersEngine(parse(CharStreams.fromChannel(channel), charset))
        }

        @JvmStatic
        @JvmOverloads
        fun parse(resource: DynabuffersResource, charset: Charset = UTF_8): DynabuffersEngine {
            return DynabuffersEngine(parse(resource.getCharStream(), charset))
        }

        private fun parse(stream: CharStream, charset: Charset): List<IType> {
            val lexer = DynabuffersLexer(stream)
            val commonTokenStream = CommonTokenStream(lexer)
            val parser = DynabuffersParser(commonTokenStream)

            val errorListener = DynabuffersExceptionListener()
            parser.addErrorListener(errorListener)

            val visitor = DynabuffersVisitor(charset)
            val astList = visitor.visit(parser.compilation())
                    ?: throw DynabuffersException("invalid dynabuffers scheme")

            if (errorListener.get() != null) {
                throw DynabuffersException(errorListener.get())
            }
            return astList
        }
    }

    /**
     * This class can be used to group some resources for the dynabuffers parsing.
     */
    class DynabuffersResource {

        private val builder = StringBuilder()

        fun append(string: String) {
            builder.append(string)
        }

        @JvmOverloads
        fun append(stream: InputStream, charset: Charset = UTF_8) {
            builder.append(stream.reader(charset).readText())
        }

        fun append(reader: Reader) {
            builder.append(reader.readText())
        }

        fun getCharStream(): CodePointCharStream = CharStreams.fromString(builder.toString())

    }

}
