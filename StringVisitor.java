package wikify;

import java.util.HashMap;
import java.util.Map;

public class StringVisitor extends WikiBaseVisitor<String> {
    /** "memory" for our calculator; variable/value pairs go here */
    Map<String, String> memory = new HashMap<String, String>();

    @Override
    public String visitStrAssign(WikiParser.StrAssignContext ctx) {
        String id = ctx.ID().getText(); //id is left side
        String word = visit(ctx.str_expr());   // compute value of string expression on right
        memory.put(id, word);           // store it in our memory

        return word;
    }

    @Override
    public String visitConcatStr(WikiParser.ConcatStrContext ctx) {
        String str = ctx.STRING().getText();
        String suffix = visit(ctx.str_expr());

        //return str + suffix;
        return suffix;
    }

    @Override
    public String visitConcatId(WikiParser.ConcatIdContext ctx) {
        String id = ctx.ID().getText();
        String id_content = memory.get(id);
        String suffix = visit(ctx.str_expr());

        //return id_content + suffix;
        return suffix;
    }

    @Override
    public String visitStrLit(WikiParser.StrLitContext ctx) {
        String str = ctx.STRING().getText();
        return str;
    }

    @Override
    public String visitIdString(WikiParser.IdStringContext ctx) {
        String id = ctx.ID().getText();
        String id_content = memory.get(id);

        return id_content;
    }
}

