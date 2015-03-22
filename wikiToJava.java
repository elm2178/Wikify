import java.io.*;

public class wikiToJava extends WikiBaseListener {
    int main_flag = 0;
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    PrintStream old = System.out; 

    /* creates the java test class with main method */
    @Override
    public void enterProg(WikiParser.ProgContext ctx) {
        System.out.print("public class TestWiki { \n");
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
        String buffer = "public static "; 
        if(ctx.type() == null)
            buffer += "void ";
        else
            buffer += matchJava(ctx.type().getText());

        buffer = buffer+ctx.ID().getText()+"("; 
        System.out.print(buffer);
    }

    public void enterArgs(WikiParser.ArgsContext ctx) {
        String buffer = "";
        if(ctx.type() != null)
            buffer += matchJava(ctx.type().getText()) + " " + ctx.ID().getText();

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

    public void enterFuncCall(WikiParser.FuncCallContext ctx) {
        System.out.print(ctx.ID().getText() 
                + "( "); 
    }

    public void exitFuncCall(WikiParser.FuncCallContext ctx) {
        System.out.println(");");
    }


    //for_stmt: FOR '('stmt';'expr';'stmt')' NL stmt_seq END;
    //for loop problems!! set for loop flag?
    public void enterFor_stmt(WikiParser.For_stmtContext ctx) {
        System.setOut(new PrintStream(stream));
    }

    public void exitFor_stmt(WikiParser.For_stmtContext ctx) {
        System.out.flush();
        String result = stream.toString();
        System.setOut(old);

        //get rid of newline and tabs in for statement
        result = result.replaceAll("[\t\n]", "");
        //split into an array
        String for_args[] = result.split(";");

        System.out.print("for(" + for_args[0] +";" + ctx.expr().getText()
                + ";" + for_args[1] +") { \n");

        //print out rest of statment sequence
        int i = 2;
        while(i < for_args.length)
        {
            System.out.println(for_args[i]+";");
            i++;
        }
        System.out.println("}");
    }

    public void enterWhile_stmt(WikiParser.While_stmtContext ctx) {
        System.out.println("while( " + ctx.expr().getText() + ") {");
    }
    public void exitWhile_stmt(WikiParser.While_stmtContext ctx) {
        System.out.println("}");
    }

    public void enterIf_stmt(WikiParser.If_stmtContext ctx) {
        System.out.println("if (" + ctx.expr().getText() + ") {");
    }
    public void enterElse_stmt(WikiParser.Else_stmtContext ctx) {
        System.out.print("}\nelse {\n");
    }

    public void exitElse_stmt(WikiParser.Else_stmtContext ctx) {
        System.out.println("}");
    }

    public void enterParams(WikiParser.ParamsContext ctx) {
         String buffer = "";
        if(ctx.expr() != null)
            buffer += ctx.expr().getText();
        if(ctx.str_expr() != null) 
            buffer += ctx.str_expr().getText();

        if(ctx.params() != null)
            buffer += ", ";

        System.out.print(buffer);
    }

    public void enterFuncAssign(WikiParser.FuncAssignContext ctx) {
        System.out.print(ctx.ID(0).getText()
                + " = "
                + ctx.ID(1).getText()
                + "( ");
    }

    public void exitFuncAssign(WikiParser.FuncAssignContext ctx) {
        System.out.println(");");
    }

    public void enterFuncDecAssign(WikiParser.FuncDecAssignContext ctx) {
        System.out.println(matchJava(ctx.type().getText())
                + ctx.ID(0).getText()
                + " = "
                + ctx.ID(1).getText()
                + "( ");
    }

    public void exitFuncDecAssign(WikiParser.FuncDecAssignContext ctx) {
        System.out.println(");");
    }

    public void enterFCall(WikiParser.FCallContext ctx) {
        System.out.print(ctx.ID().getText() + " ( ");
    }

    public void exitFCall(WikiParser.FCallContext ctx) {
        System.out.print(");");
    }

    /* Print Java Assignment */
    @Override
    public void enterDecAssign(WikiParser.DecAssignContext ctx) {
        System.out.println(matchJava(ctx.type().getText())
                + ctx.ID().getText() 
                + " = " 
                + ctx.expr().getText() 
                + ";"); 
    }
    public void enterAssign(WikiParser.AssignContext ctx) {
        System.out.println(ctx.ID().getText() 
                + " = " 
                + ctx.expr().getText() 
                + ";"); 
    }
 
    public void enterPrint(WikiParser.PrintContext ctx) {
        System.out.println("System.out.print("
                + ctx.expr().getText()
                + ");");
    }

    private String matchJava(String type) {
        String result = "undefined";
        if(type.compareTo("string") == 0)
            result = "String ";
        if(type.compareTo("bool") == 0)
            result = "boolean ";
        if(type.compareTo("int") == 0)
            result = "int ";

        return result;
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
