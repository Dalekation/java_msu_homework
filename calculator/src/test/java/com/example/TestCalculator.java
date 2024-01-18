package com.example;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.Math;

public class TestCalculator {
    @Test
    @DisplayName("Test for Sum")
    public void testSum() throws Exception{
        assertEquals(Calculator.calculate_expr("2 + 2"), 2 + 2, "Wrong calculation1");
        assertEquals(Calculator.calculate_expr("(2 + 2)"), 2 + 2, "Wrong calculation2");
    }

    @Test
    @DisplayName("Test for Difference")
    public void testDiff() throws Exception{
        assertEquals(Calculator.calculate_expr("2 - 2"), 2 - 2, "Wrong calculation1");
        assertEquals(Calculator.calculate_expr("(2 - 2)"), 2 - 2, "Wrong calculation2");
        assertEquals(Calculator.calculate_expr("(-2 + 2)"), 2 - 2, "Wrong calculation2");
    }

    @Test
    @DisplayName("Test for Multiplication")
    public void testMult() throws Exception{
        assertEquals(Calculator.calculate_expr("2 * 2"), 2 * 2, "Wrong calculation1");
        assertEquals(Calculator.calculate_expr("(2 * 2)"), 2 * 2, "Wrong calculation2");
    }

    @Test
    @DisplayName("Test for Division")
    public void testDiv() throws Exception{
        assertEquals(Calculator.calculate_expr("2 / 3"), 2.0 / 3.0, "Wrong calculation1");
        assertEquals(Calculator.calculate_expr("(2 / 3)"), 2.0 / 3.0, "Wrong calculation2");
    }

    @Test
    @DisplayName("Test for Division")
    public void testDifferentStuff() throws Exception{
        assertEquals(Calculator.calculate_expr("2+3*(8-7/2)"), 15.5, "Wrong calculation1");
        assertEquals(Calculator.calculate_expr("2-7/5+11*5"), 55.6, "Wrong calculation2");
        assertTrue(Math.abs(Calculator.calculate_expr("3.5 - 3.4") - 0.1) < 1e-6, "Wrong calculation3");
    }
}
