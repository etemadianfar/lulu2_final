import antlr.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.TokenStream;

public class main {
    public static void main(String[] args) throws Exception {
        String st = 
                "declare{\n" +
                "    (int) = fib(bool v, string s);\n" +
                "    lalalala la;\n" +
                "}\n" +
                "\n" +
                "%% zombi ha az lulu nemitarsand\n" +
                "(int n) = function fib(int n_){\n" +
                "    if n_ <= 2 && n_ >= 3{\n" +
                "        if m == n-3*-a {\n" +
                "            (n) = 1;\n" +
                "        }\n" +
                "    }\n" +
                "    else {\n" +
                "        if b > 2 || !true {\n" +
                "            %~\n" +
                "            Calculating fibonacci recursively.\n" +
                "            Fib[n] = Fib[n-1] + Fib[n-2]\n" +
                "            ~%\n" +
                "            (n) = fib(n_-1) + fib(n_-2);\n" +
                "        }\n" +
                "    }\n" +
                "    return;\n" +
                "}\n" +
                "\n" +
                "type db:bd{\n" +
                "    private const string a[allocate b()], p[[[this.a.a.a], [((write(t)))]]][nil];\n" +
                "    %~\n" +
                "    Man an morghe siah balam.\n" +
                "    Man an morghe siah balam. ~%\n" +
                "    protected int a[b[c[d]]];\n" +
                "}\n" +
                "\n" +
                "function fun (  ) {\n" +
                "    %%Let's have some fun\n" +
                "    string str = 'RTFM\\X78\\xF1';\n" +
                "    \n" +
                "}\n" +
                "\n" +
                "function alex(){\n" +
                "    switch super.alex{\n" +
                "        case 1:{\n" +
                "            destruct [][] alex;\n" +
                "        }\n" +
                "        case 2:{\n" +
                "            destruct [][][] karshenas;\n" +
                "        }\n" +
                "        case 3:{\n" +
                "            const bool var = a *!b[d[3]+~W]-c/2|c(v(5,6))^3==3%a&3&&2||sds!=q>t>=y; \n" +
                "        }\n" +
                "        default:{\n" +
                "            book memo[n][m] = allocate book(); \n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "(float f, float e) = function yechizi(){\n" +
                "    while e == f == e {\n" +
                "        const float b, k;\n" +
                "        int a[12], z[13][17];\n" +
                "        return;\n" +
                "    }\n" +
                "}";


        CharStream input = CharStreams.fromString(st);
        antlrLexer lexer = new antlrLexer(input);
        lexer.setTokenFactory(new CommonTokenFactory(true));
        TokenStream tokens = new UnbufferedTokenStream<CommonToken>(lexer);
        antlrParser parser = new antlrParser(tokens);
        parser.setBuildParseTree(true);
        ParseTree tree = parser.program();

    }


}
