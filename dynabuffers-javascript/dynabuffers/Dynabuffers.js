import {DynabuffersLexer} from "./antlr/DynabuffersLexer";
import {InputStream} from "antlr4";

class Dynabuffers {

    static parse(schema) {
        const lexer = new DynabuffersLexer(new InputStream(schema));
        const stream = new CommonTokenStream(lexer);
        const parser = new DynabuffersParser();
    }

}
