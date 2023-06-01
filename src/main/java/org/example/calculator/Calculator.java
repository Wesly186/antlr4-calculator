package org.example.calculator;

import java.math.BigDecimal;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.example.grammar.ExpressionLexer;
import org.example.grammar.ExpressionParser;

public class Calculator {

    public BigDecimal calculateInVisitorMode(String expression) {
        // expression --> lexer --> tokens
        ExpressionLexer lexer = new ExpressionLexer(CharStreams.fromString(expression));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // tokens --> parser
        ExpressionParser parser = new ExpressionParser(tokens);

        // visitor mode
        ParseTree calc = parser.calc();
        ExpressionParserVisitor visitor = new ExpressionParserVisitor();
        return visitor.visit(calc);
    }

    public BigDecimal calculateInListenerMode(String expression) {
        // expression --> lexer --> tokens
        ExpressionLexer lexer = new ExpressionLexer(CharStreams.fromString(expression));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // tokens --> parser
        ExpressionParser parser = new ExpressionParser(tokens);

        // listener mode
        ParseTree calc = parser.calc();
        ParseTreeWalker walker = new ParseTreeWalker();
        ExpressionParserListener expressionParserListener = new ExpressionParserListener();
        walker.walk(expressionParserListener, calc);

        // listener result
        return expressionParserListener.result();
    }
}
