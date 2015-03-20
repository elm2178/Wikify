import java.io.*;

public class wikiToJava extends WikiBaseListener {
    int main_flag = 0;

    /* creates the java test class with main method */
    @Override
    public void enterProg(WikiParser.ProgContext ctx) {
        System.out.println("public class TestWiki {");
    }

    public void enterStmt_seq(WikiParser.Stmt_seqContext ctx) {
        if(main_flag == 0)
        {
            System.out.println("public static void main(String[] args) {");
            main_flag = 1;
        }
    }

    /* Closes Parenthesis at exit prog */
    @Override
    public void exitProg(WikiParser.ProgContext ctx) {
        System.out.print("}\n}");
    }

    public void enterFuncDef(WikiParser.FuncDefContext ctx) {
        String buffer = "public "; 
        if(ctx.ID(0).getText().compareTo("string") == 0)
            buffer = buffer.concat(" String");
        else
            buffer = buffer + ctx.ID(0).getText();

        buffer = buffer+" "+ctx.ID(1).getText()+"("; 
        System.out.print(buffer);
    }

    public void enterArgs(WikiParser.ArgsContext ctx) {
        String buffer = "";
        if(ctx.ID(0) != null)
            buffer += ctx.ID(0).getText() + " " + ctx.ID(1).getText();

        if(ctx.args() != null)
            buffer += ", ";
        else
            buffer += ") {\n";

        System.out.print(buffer);
    }

    public void enterRetExpr(WikiParser.RetExprContext ctx) {
        System.out.println("return " 
                + ctx.expr().getText() 
                + ";");
    }

    public void exitFuncDef(WikiParser.FuncDefContext ctx) {
        System.out.println("} ");
    }


    /*do nothing*/
    @Override
    public void enterPrintStmt(WikiParser.PrintStmtContext ctx) {
    }


    /*do nothing*/
    @Override
    public void exitPrintStmt(WikiParser.PrintStmtContext ctx) {
    }

    /* Print Java Assignment */
    @Override
    public void enterIntAssign(WikiParser.IntAssignContext ctx) {
        System.out.println("int " 
                + ctx.ID(1).getText() 
                + " = " 
                + ctx.expr().getText() 
                + ";"); 
    }
    public void enterStrAssign(WikiParser.StrAssignContext ctx) { 
        System.out.println("String " 
                + ctx.ID(1).getText() 
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
