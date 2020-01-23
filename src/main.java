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
                "    helloType;\n" +
                "    (int , float) = myFunc1();\n" +
                "    (string) = myFunc2(int c);\n" +
                "    (bool) = myFunc3(int[] d, string str);\n" +
                "}\n" +
                "(int a, float b) = function myFunc1(int j, int i){\n" +
                "    while j<=10 && i!=0 && (i||j){\n" +
                "        object.myFunc();\n" +
                "        if (j == 10) {\n" +
                "            i = 1;\n" +
                "            break;\n" +
                "        }\n" +
                "        else{\n" +
                "            if(j >=6){\n" +
                "                read();\n" +
                "            }\n" +
                "            if(j >= 8){\n" +
                "                write(a);\n" +
                "            }\n" +
                "            else{\n" +
                "                continue;\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "(string str1) = function myFunc2(string str2){\n" +
                " \n" +
                "}\n" +
                "(bool bo) = function myFunc3(int[] c, string str){\n" +
                "}\n" +
                "\n" +
                "type myType1: _id1{\n" +
                "   int a;\n" +
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