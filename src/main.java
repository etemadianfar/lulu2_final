import lulu2_package.*;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenFactory;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.UnbufferedTokenStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Main{
    public static void main(String[] args) throws Exception {
        String st = "declare{\n" +
                "    int a;\n" +
                "}\n" +
                "function myFunc1(){\n" +
                "    bool a;\n" +
                "    bool b;\n" +
                "    bool c = a || b;\n" +
                "}\n";


        CharStream input = CharStreams.fromString(st);
        lulu2_grammerLexer lexer = new lulu2_grammerLexer(input);
        lexer.setTokenFactory(new CommonTokenFactory(true));
        TokenStream tokens = new UnbufferedTokenStream<CommonToken>(lexer);
        lulu2_grammerParser parser = new lulu2_grammerParser(tokens);
        parser.setBuildParseTree(true);
        ParseTree tree = parser.program();

	    lulu2_grammerListener listener = new lulu2_grammerBaseListener();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);
    }
}