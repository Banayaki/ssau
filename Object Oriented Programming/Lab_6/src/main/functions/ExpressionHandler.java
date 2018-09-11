package functions;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ExpressionHandler {

    public static Expression buildExpression(String expr) {
        return new ExpressionBuilder(expr).variables("x").build();
    }
}
