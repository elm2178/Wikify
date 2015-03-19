package wikify;

import java.util.HashMap;
import java.util.Map;

public class EvalVisitor extends WikiBaseVisitor<Integer> {
    /** "memory" for our calculator; variable/value pairs go here */
    Map<String, DataType> memory = new HashMap<String, DataType>();

    /** ID '=' expr NEWLINE */
    @Override
    public Integer visitAssign(WikiParser.AssignContext ctx) {
        String id = ctx.ID().getText();  // id is left-hand side of '='
        int value = visit(ctx.expr());   // compute value of expression on right

        DataType dt = new DataType(value);
        memory.put(id, dt);           // store it in our memory
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

        if ( memory.containsKey(id) ) {
            DataType dt = memory.get(id);

            if(dt.getType() == DataType.INT)
                return dt.getInt();
        }
        
        //some kind of error
        return 0; 
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
        DataType dt;

        if ( memory.containsKey(id) ) {
            dt = memory.get(id);
            if(dt.getType() == DataType.STR)
                System.out.println(dt.getString());
            if(dt.getType() == DataType.INT)
                System.out.println(dt.getInt());
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
