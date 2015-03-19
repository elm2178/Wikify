import java.io.*;
import java.util.*;

public class WikiToJava extends WikiBaseListener {

    private LinkedList<String> queue;
    //Translate print to 'System.out.println('
    @Override
    public void enterProg(WikiParser.ProgContext ctx) {
        queue = new LinkedList<String>();
        System.out.print("public class Test {\n"
            +"public static void main(String[] args) {\n");
    }

    //Translate } to "
    @Override
    public void exitProg(WikiParser.ProgContext ctx) {
        while(queue.peekFirst() != null)
        {
            System.out.println(queue.pop());
        }
        System.out.print("}\n}");
    }

    @Override
    public void enterPrint_stmt(WikiParser.Print_stmtContext ctx) {
        System.out.print("System.out.println(\"");
        String word = ctx.STRING().getText();
        word = word.substring(1, word.length() - 1);
        System.out.print(word);
    }
    
    @Override
    public void exitPrint_stmt(WikiParser.Print_stmtContext ctx) {
        System.out.print("\");\n");
    }

}
