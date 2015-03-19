import java.io.*;

public class wikiToJava extends WikiBaseListener {

    //Translate print to 'System.out.println('
    @Override
    public void enterProg(WikiParser.ProgContext ctx) {
        System.out.print("public class TestWiki {\n" 
                +"public static void main(String[] args) {\n");
    }

    @Override
    public void exitProg(WikiParser.ProgContext ctx) {
        System.out.print("}\n}");
    }

    @Override
    public void enterPrintStmt(WikiParser.PrintStmtContext ctx) {
    }

    @Override
    public void exitPrintStmt(WikiParser.PrintStmtContext ctx) {
    }

    @Override
    public void enterAssign(WikiParser.AssignContext ctx) {
        System.out.println("int " 
                + ctx.ID().getText() 
                + " = " 
                + ctx.expr().getText() 
                + ";"); 
    }
    

    public void enterPrintString(WikiParser.PrintStringContext ctx) {
        System.out.print("System.out.print(\"");
        String word = ctx.STRING().getText();
        word = word.substring(1, word.length() - 1);
        System.out.print(word);
        System.out.print("\");\n");
    }

    public void enterPrintId(WikiParser.PrintIdContext ctx) {
        System.out.print("System.out.print(");
        String id = ctx.ID().getText();
        System.out.print(id);
        System.out.print(");\n");
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
