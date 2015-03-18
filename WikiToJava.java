public class WikiToJava extends WikiBaseListener {

    //Translate print to 'System.out.println('
    @Override
    public void enterProg(WikiParser.ProgContext ctx) {
        System.out.print("public class Test {\n"
            +"public static void main(String[] args) {"
            +"System.out.println(\"");
    }

    //Translate } to "
    @Override
    public void exitProg(WikiParser.ProgContext ctx) {
        System.out.print("\"); } } ");
    }

    //translate integers into 4 digit hex prefixed with \\u
    @Override
    public void enterPrint_stmt(WikiParser.Print_stmtContext ctx) {
        String word = ctx.ID().getText();
        System.out.print(word);
    }
}
