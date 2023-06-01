package org.example.calculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;
import java.util.Stack;
import org.antlr.v4.runtime.Token;
import org.example.grammar.ExpressionLexer;
import org.example.grammar.ExpressionParser;
import org.example.grammar.ExpressionParserBaseListener;

public class ExpressionParserListener extends ExpressionParserBaseListener {

    private final Stack<BigDecimal> stack = new Stack<>();

    /**
     * 100
     */
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    /**
     * DECIMAL128のMathContext (桁数34、RoundingMode.HALF_EVEN)
     */
    private static final MathContext MATH_CONTEXT = MathContext.DECIMAL128;

    public BigDecimal result() {
        return stack.pop();
    }

    @Override
    public void exitCalculationBlock(final ExpressionParser.CalculationBlockContext ctx) {
        super.exitCalculationBlock(ctx);

        if (stack.size() == 1) {
            return;
        }

        BigDecimal result = BigDecimal.ONE;
        while (!stack.isEmpty()) {
            result = result.multiply(stack.pop(), MATH_CONTEXT);
        }

        stack.add(result);
    }

    @Override
    public void exitExpressionNumeric(final ExpressionParser.ExpressionNumericContext ctx) {
        BigDecimal numeric = numberOrPercent(ctx.num);
        if (Objects.nonNull(ctx.sign) && ctx.sign.getType() == ExpressionLexer.MINUS) {
            numeric = numeric.negate();
        }
        stack.add(numeric);
    }

    @Override
    public void exitExpressionTimesOrDivide(final ExpressionParser.ExpressionTimesOrDivideContext ctx) {
        BigDecimal right = stack.pop();
        BigDecimal left = stack.pop();

        switch (ctx.op.getType()) {
            case ExpressionLexer.TIMES:
                stack.add(left.multiply(right, MATH_CONTEXT));
                break;
            case ExpressionLexer.DIVIDE:
                stack.add(left.divide(right, MATH_CONTEXT));
                break;
            default:
                throw new RuntimeException("unsupported operator type");
        }
    }

    @Override
    public void exitExpressionPlusOrMinus(final ExpressionParser.ExpressionPlusOrMinusContext ctx) {
        BigDecimal right = stack.pop();
        BigDecimal left = stack.pop();

        switch (ctx.op.getType()) {
            case ExpressionLexer.MINUS:
                stack.add(left.subtract(right, MATH_CONTEXT));
                break;
            case ExpressionLexer.PLUS:
                stack.add(left.add(right, MATH_CONTEXT));
                break;
            default:
                throw new RuntimeException("unsupported operator type");
        }
    }

    private BigDecimal numberOrPercent(Token num) {
        String numberStr = num.getText();
        switch (num.getType()) {
            case ExpressionLexer.NUMBER:
                return decimal(numberStr);
            case ExpressionLexer.PERCENT_NUMBER:
                return decimal(numberStr.substring(0, numberStr.length() - 1).trim()).divide(HUNDRED, MATH_CONTEXT);
            default:
                throw new RuntimeException("unsupported number type");
        }
    }

    private BigDecimal decimal(String decimalStr) {
        return new BigDecimal(decimalStr);
    }
}
