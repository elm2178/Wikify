import java.io.*;

public class wikiToJava extends WikiBaseListener {

    /* creates the java test class with main method */
    @Override
    public void enterProg(WikiParser.ProgContext ctx) {
        System.out.print("public class TestWiki {\n" 
                +"public static void main(String[] args) {\n");
    }

    /* Closes Parenthesis at exit prog */
    @Override
    public void exitProg(WikiParser.ProgContext ctx) {
        System.out.print("}\n}");
    }

    /*do nothing*/
    @Override
    public void enterPrintStmt(WikiParser.PrintStmtContext ctx) {
    }


    /*do nothing*/
    @Override
    public void exitPrintStmt(WikiParser.PrintStmtContext ctx) {
    }

    /* Print Java Int Assignment */
    @Override
    public void enterIntAssign(WikiParser.IntAssignContext ctx) {
        System.out.println("int " 
                + ctx.ID().getText() 
                + " = " 
                + ctx.expr().getText() 
                + ";"); 
    }

    /* Print Java String Assignment */
    public void enterStrAssign(WikiParser.StrAssignContext ctx) {
        System.out.println("String " 
                + ctx.ID().getText()
                + " = "
                + ctx.str_expr().getText()
                + ";");
    }

    /*
    public void enterPrintString(WikiParser.PrintStringContext ctx) {
        System.out.print("System.out.print(\"");
        String word = ctx.STRING().getText();
        word = word.substring(1, word.length() - 1);
        System.out.print(word);
        System.out.print("\");\n");
    }
    */

    public void enterPrintExpr(WikiParser.PrintExprContext ctx) {
        System.out.println("System.out.print("
                + ctx.expr().getText()
                + ");");
    }

    public void enterPrintStrExpr(WikiParser.PrintStrExprContext ctx) {
        System.out.println("System.out.print("
                + ctx.str_expr().getText()
                + ");");
    }

    public void enterComment(WikiParser.CommentContext ctx) {
        System.out.println(ctx.COMMENT().getText());
    }

    //Note I wrote this because java's replaceAll would not recognize \\n
    private String replaceEscapeSeq(String word)
    {
        char escape[] = {'n', 'r', 't', '"', '\\'};
        char seq[] = {'\n', '\r', '\t', '\"', '\\'};

        if(word.length() <= 1)
            return word;

        int i;
        for(i = 1; i < word.length(); i++)
        {
            for(int k = 0; k < 5; k++)
            {
                if(word.charAt(i) == escape[k] && word.charAt(i-1) == '\\')
                {
                    String suffix = word.substring(i + 1, word.length());
                    String prefix = word.substring(0, i - 1);
                    prefix = prefix.concat(Character.toString(seq[k]));
                    word = prefix.concat(suffix);
                    i--;
                    break; //you have found a matching char and can leave this loop
                }
            }
        }
        return word;
    }
}
