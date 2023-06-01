package org.example.calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorUnitTest {

    private final Calculator calculator = new Calculator();

    @DisplayName("Test Calculator In Visitor Mode")
    @ParameterizedTest
    @CsvSource({
        "(1+2)(1+2), 9",
        "1 + 2, 3",
        "3 - 2, 1",
        "2 * 3, 6",
        "6 / 3, 2",
        "6 / (1 + 2) , 2",
        "50%, 0.5",
        "100 * 30%, 30.0"
    })
    void testVisitorCalculation(String expression, String expected) {
        assertEquals(expected, calculator.calculateInVisitorMode(expression).toPlainString());
    }

    @DisplayName("Test Calculator In Listener Mode")
    @ParameterizedTest
    @CsvSource({
        "(1+2)(1+2), 9",
        "1 + 2, 3",
        "3 - 2, 1",
        "2 * 3, 6",
        "6 / 3, 2",
        "6 / (1 + 2) , 2",
        "50%, 0.5",
        "100 * 30%, 30.0"
    })
    void testListenerCalculation(String expression, String expected) {
        assertEquals(expected, calculator.calculateInListenerMode(expression).toPlainString());
    }

}