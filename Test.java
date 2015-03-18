import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Test {
    public static void main(String[] args) throws Exception {
        //creates a charStream taht reads from standard input
        ANTLRInputStream input = new ANTLRInputStream(System.in);

        //creates a lexer that feeds off of charStream
        WikiLexer lexer = new WikiLexer(input);

        //creates a buffer of tokens pulled from lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        //creates a parser that feeds off of token buffer
        WikiParser parser = new WikiParser(tokens);

        //begin parsing at init rule
        ParseTree tree = parser.prog();

        //Create a gneric parse tree walker
        ParseTreeWalker walker = new ParseTreeWalker();

        walker.walk(new WikiToJava(), tree);
        System.out.println();
    }
}
