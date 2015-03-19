import java.util.HashMap;
import java.util.Map;

public class EvalVisitor extends WikiBaseVisitor<Integer> {
    /** "memory" for our calculator; variable/value pairs go here */
    Map<String, Integer> memory = new HashMap<String, Integer>();

    /** ID '=' expr NEWLINE */
    @Override
    public Integer visitAssign(WikiParser.AssignContext ctx) {
        String id = ctx.ID().getText();  // id is left-hand side of '='
        int value = visit(ctx.expr());   // compute value of expression on right
        memory.put(id, value);           // store it in our memory
        return value;
    }

    /** expr op=(ADD|SUB) term */
    @Override
    public Integer visitAddSub(WikiParser.AddSubContext ctx) {
        int left = visit(ctx.expr());  // get value of left subexpression
        int right = visit(ctx.term()); // get value of right subexpression

        if ( ctx.op.getType() == WikiParser.ADD ) //if add
            return left + right;
        return left - right; // must be SUB
    }

    /** INT */
    @Override
    public Integer visitInteger(WikiParser.IntegerContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }

    /** ID */
    @Override
    public Integer visitVar(WikiParser.VarContext ctx) {
        String id = ctx.ID().getText();

        if ( memory.containsKey(id) ) 
            return memory.get(id);

        return 0; //this should be an error
    }

    /** term op=(MUL|DIV) fact */
    @Override
    public Integer visitMulDiv(WikiParser.MulDivContext ctx) {
        int left = visit(ctx.term());  // get value of left subexpression
        int right = visit(ctx.fact()); // get value of right subexpression

        if ( ctx.op.getType() == WikiParser.MUL ) //if MUL
            return left * right;
        return left / right; // must be DIV
    }

    /** '(' expr ')' */
    @Override
    public Integer visitParens(WikiParser.ParensContext ctx) {
        return visit(ctx.expr()); // return child expr's value
    }

    /** PRINT ID */
    @Override
    public Integer visitPrintId(WikiParser.PrintIdContext ctx) {
        String id = ctx.ID().getText();
        int value;

        if ( memory.containsKey(id) ) {
            value = memory.get(id);
            System.out.print(value);
        }

        return 0;
    }

    /** PRINT STRING */
    @Override
    public Integer visitPrintString(WikiParser.PrintStringContext ctx) {
        String word = ctx.STRING().getText();
        word = word.substring(1, word.length() - 1);
        word = replaceEscapeSeq(word);

        System.out.print(word);
        return 0;
    }

    //Note I wrote this because java's replaceAll would not recognize \\n
    private String replaceEscapeSeq(String word)
    {
        char escape[] = {'n', 'r', 't', '"', '\\'};
        char seq[] = {'\n', '\r', '\t', '\"', '\\'};

        int i;
        for(i = 1; i < word.length(); i++)
        {
            for(int k = 0; k < 5; k++)
            {
                if(word.charAt(i) == escape[k] && word.charAt(i-1) == '\\')
                {
                    String suffix = word.substring(i+ 1, word.length());
                    String prefix = word.substring(0, i - 1);
                    prefix = prefix.concat(Character.toString(seq[k]));
                    word = prefix.concat(suffix);
                }
            }
        }
        return word;
    }
}
