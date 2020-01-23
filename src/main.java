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
                "    int a[2][3], b[2], c[15];\n" +
                "    helloType;\n" +
                "    (int, float) = myFunc1(int , float);\n" +
                "}\n" +
                "(int a, float b) = function myFunc1(int j, int i){\n" +
                "for int i=0 ; i < 2 ; i= i+1 {\n" +
                "   \t\n" +
                "   }" +
                "}";

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