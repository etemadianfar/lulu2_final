import lulu2_package.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.TokenStream;

public class main {
    public static void main(String[] args) throws Exception {
        String st ="";

        CharStream input = CharStreams.fromString(st);
        lulu2_grammerLexer lexer = new lulu2_grammerLexer(input);
        lexer.setTokenFactory(new CommonTokenFactory(true));
        TokenStream tokens = new UnbufferedTokenStream<CommonToken>(lexer);
        lulu2_grammerParser parser = new lulu2_grammerParser(tokens);
        parser.setBuildParseTree(true);
        ParseTree tree = parser.program();

    }


}
