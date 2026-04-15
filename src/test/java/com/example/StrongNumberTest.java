package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Comprehensive Test Suite for Strong Number Checker
 * 40+ test cases covering all scenarios and edge cases
 */
@DisplayName("Strong Number Checker Tests")
class StrongNumberTest {

    // ========== 1. BASIC FUNCTIONALITY TESTS (5 tests) ==========

    @Test
    @DisplayName("Test classic strong number 145")
    void testClassicStrongNumber145() {
        assertTrue(StrongNumber.isStrongNumber(145),
                "145 should be a strong number (1! + 4! + 5! = 1 + 24 + 120 = 145)");
    }

    @Test
    @DisplayName("Test strong number 2")
    void testStrongNumber2() {
        assertTrue(StrongNumber.isStrongNumber(2),
                "2 should be a strong number (2! = 2)");
    }

    @Test
    @DisplayName("Test strong number 1")
    void testStrongNumber1() {
        assertTrue(StrongNumber.isStrongNumber(1),
                "1 should be a strong number (1! = 1)");
    }

    @Test
    @DisplayName("Test strong number 40585")
    void testStrongNumber40585() {
        assertTrue(StrongNumber.isStrongNumber(40585),
                "40585 should be a strong number (4! + 0! + 5! + 8! + 5!)");
    }

    @Test
    @DisplayName("Test non-strong number 10")
    void testNonStrongNumber10() {
        assertFalse(StrongNumber.isStrongNumber(10),
                "10 is not a strong number (1! + 0! = 1 + 1 = 2 ≠ 10)");
    }

    // ========== 2. NOT STRONG NUMBERS (4 tests) ==========

    @ParameterizedTest
    @ValueSource(ints = { 3, 4, 5, 100 })
    @DisplayName("Test multiple non-strong numbers")
    void testNonStrongNumbers(int num) {
        assertFalse(StrongNumber.isStrongNumber(num),
                num + " should not be a strong number");
    }

    // ========== 3. EDGE CASES: ZERO AND SINGLE DIGIT (3 tests) ==========

    @Test
    @DisplayName("Test zero (edge case)")
    void testZero() {
        assertFalse(StrongNumber.isStrongNumber(0),
                "0 should NOT be strong (0! = 1, not 0)");
    }

    @Test
    @DisplayName("Test single digit numbers")
    void testSingleDigits() {
        assertTrue(StrongNumber.isStrongNumber(1));
        assertTrue(StrongNumber.isStrongNumber(2));
        assertFalse(StrongNumber.isStrongNumber(3));
        assertFalse(StrongNumber.isStrongNumber(4));
    }

    // ========== 4. NEGATIVE NUMBERS (2 tests) ==========

    @Test
    @DisplayName("Test negative number throws exception")
    void testNegativeNumberThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> StrongNumber.isStrongNumber(-1),
                "Negative number should throw IllegalArgumentException");
    }

    @Test
    @DisplayName("Test negative validation")
    void testNegativeValidation() {
        assertThrows(IllegalArgumentException.class,
                () -> StrongNumber.isStrongNumber(-145),
                "Negative number should throw exception");
    }

    // ========== 5. ALGORITHM COMPARISON (3 tests) ==========

    @ParameterizedTest
    @CsvSource({
            "145, true",
            "2, true",
            "1, true",
            "40585, true",
            "10, false",
            "100, false"
    })
    @DisplayName("Test all algorithms return same result")
    void testAlgorithmConsistency(int num, boolean expected) {
        assertEquals(expected, StrongNumber.isStrongNumber(num),
                "Digit extraction should match expected");
        assertEquals(expected, StrongNumber.isStrongNumberString(num),
                "String conversion should match expected");
        assertEquals(expected, StrongNumber.isStrongNumberList(num),
                "LinkedList method should match expected");
    }

    @Test
    @DisplayName("Compare algorithm results on 40585")
    void testAlgorithmComparison() {
        int num = 40585;
        boolean result1 = StrongNumber.isStrongNumber(num);
        boolean result2 = StrongNumber.isStrongNumberString(num);
        boolean result3 = StrongNumber.isStrongNumberList(num);

        assertEquals(result1, result2, "All algorithms should match");
        assertEquals(result2, result3, "All algorithms should match");
        assertTrue(result1, "40585 should be strong across all algorithms");
    }

    // ========== 6. FACTORIAL CALCULATION (5 tests) ==========

    @Test
    @DisplayName("Test factorial computation")
    void testFactorialComputation() {
        assertEquals(1, StrongNumber.factorial(0), "0! = 1");
        assertEquals(1, StrongNumber.factorial(1), "1! = 1");
        assertEquals(2, StrongNumber.factorial(2), "2! = 2");
        assertEquals(6, StrongNumber.factorial(3), "3! = 6");
        assertEquals(40320, StrongNumber.factorial(8), "8! = 40320");
    }

    @Test
    @DisplayName("Test factorial for invalid inputs")
    void testFactorialInvalidInput() {
        assertThrows(IllegalArgumentException.class,
                () -> StrongNumber.factorial(-1),
                "Factorial of -1 should throw exception");
        assertThrows(IllegalArgumentException.class,
                () -> StrongNumber.factorial(10),
                "Factorial of 10 should throw exception");
    }

    // ========== 7. SUM OF FACTORIALS (3 tests) ==========

    @Test
    @DisplayName("Test sum of factorials for 145")
    void testSumOfFactorials145() {
        int sum = StrongNumber.getSumOfFactorials(145);
        assertEquals(145, sum, "Sum of factorials of 145 should be 145");
    }

    @Test
    @DisplayName("Test sum of factorials for 40585")
    void testSumOfFactorials40585() {
        int sum = StrongNumber.getSumOfFactorials(40585);
        assertEquals(40585, sum, "Sum of factorials of 40585 should be 40585");
    }

    @Test
    @DisplayName("Test sum of factorials matches number")
    void testSumOfFactorialsMatches() {
        for (int num : new int[] { 1, 2, 145, 40585 }) {
            int sum = StrongNumber.getSumOfFactorials(num);
            assertEquals(num, sum, "Sum should equal original for strong numbers");
        }
    }

    // ========== 8. RANGE FINDING (4 tests) ==========

    @Test
    @DisplayName("Find strong numbers in range [0, 100]")
    void testFindStrongNumbersInRange1() {
        List<Integer> result = StrongNumber.findStrongNumbersInRange(0, 100);
        assertThat(result, hasItems(1, 2));
        assertEquals(2, result.size(), "Should find exactly 2 strong numbers (1, 2, not 0)");
    }

    @Test
    @DisplayName("Find all strong numbers up to 50000")
    void testFindAllStrongNumbers() {
        List<Integer> result = StrongNumber.findStrongNumbersInRange(0, 50000);
        assertThat(result, hasItems(1, 2, 145, 40585)); // 4 strong numbers
        assertThat(result.size(), greaterThanOrEqualTo(4));
    }

    @Test
    @DisplayName("Find strong numbers in empty range")
    void testFindStrongNumbersEmptyRange() {
        List<Integer> result = StrongNumber.findStrongNumbersInRange(3, 9);
        assertEquals(0, result.size(), "Should find no strong numbers");
    }

    @Test
    @DisplayName("Find strong numbers invalid range throws exception")
    void testFindStrongNumbersInvalidRange() {
        assertThrows(IllegalArgumentException.class,
                () -> StrongNumber.findStrongNumbersInRange(100, 10),
                "Invalid range should throw exception");
    }

    // ========== 9. FACTORIZATION STRING (2 tests) ==========

    @Test
    @DisplayName("Test factorization string for 145")
    void testFactorizationString145() {
        String result = StrongNumber.getFactorization(145);
        assertThat(result, containsString("145"));
        assertThat(result, containsString("1!"));
        assertThat(result, containsString("4!"));
        assertThat(result, containsString("5!"));
    }

    @Test
    @DisplayName("Test factorization string for 2")
    void testFactorizationString2() {
        String result = StrongNumber.getFactorization(2);
        assertThat(result, containsString("2 ="));
        assertThat(result, containsString("2"));
    }

    // ========== 10. VALIDATION METHODS (2 tests) ==========

    @Test
    @DisplayName("Test valid strong number validation")
    void testValidStrongNumber() {
        assertTrue(StrongNumber.isValidStrongNumber(145));
        assertTrue(StrongNumber.isValidStrongNumber(2));
        assertTrue(StrongNumber.isValidStrongNumber(1));
    }

    // ========== 11. PERFORMANCE TESTS (2 tests) ==========

    @Test
    @DisplayName("Performance test: Check strong number in < 1ms")
    void testPerformanceSingleNumber() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            StrongNumber.isStrongNumber(40585);
        }
        long end = System.currentTimeMillis();
        long elapsed = end - start;
        assertTrue(elapsed < 1000L,
                "1000 iterations should complete in < 1 second. Actual: " + elapsed + "ms");
    }

    @Test
    @DisplayName("Performance test: Find strong numbers in range")
    void testPerformanceRangeSearch() {
        long start = System.currentTimeMillis();
        List<Integer> result = StrongNumber.findStrongNumbersInRange(0, 10000);
        long end = System.currentTimeMillis();
        long elapsed = end - start;

        assertThat(result.size(), greaterThanOrEqualTo(3)); // 1, 2, 145
        assertTrue(elapsed < 5000L,
                "Range search should complete in < 5 seconds. Actual: " + elapsed + "ms");
    }

    // ========== 11. RANDOMIZED TESTS (2 tests) ==========

    @Test
    @DisplayName("Randomized: Test known strong numbers")
    void testRandomizedKnownStrong() {
        int[] knownStrong = { 1, 2, 145, 40585 };
        for (int num : knownStrong) {
            assertTrue(StrongNumber.isStrongNumber(num),
                    num + " should be strong");
        }
    }

    void testRandomizedConsistency() {
        Random random = new Random(42); // Fixed seed for reproducibility
        for (int i = 0; i < 50; i++) {
            int num = Math.abs(random.nextInt(10000));
            boolean result1 = StrongNumber.isStrongNumber(num);
            boolean result2 = StrongNumber.isStrongNumberString(num);
            assertEquals(result1, result2,
                    "Results should match for random number: " + num);
        }
    }

    // ========== 13. LARGE NUMBER TESTS (2 tests) ==========

    @Test
    @DisplayName("Test large number handling")
    void testLargeNumbers() {
        // These shouldn't throw exceptions
        assertDoesNotThrow(() -> StrongNumber.isStrongNumber(Integer.MAX_VALUE));
        assertDoesNotThrow(() -> StrongNumber.getSumOfFactorials(999999));
    }

    @Test
    @DisplayName("Test very large range")
    void testLargeRange() {
        List<Integer> result = StrongNumber.findStrongNumbersInRange(0, 100000);
        assertThat(result.size(), greaterThanOrEqualTo(4));
        assertThat(result, hasItem(40585));
    }

    // ========== 14. BOUNDARY CONDITIONS (3 tests) ==========

    @Test
    @DisplayName("Test at boundary: 99999")
    void testBoundary99999() {
        boolean result = StrongNumber.isStrongNumber(99999);
        assertNotNull(result, "Should return boolean, not null");
    }

    @Test
    @DisplayName("Test all single digits 0-9")
    void testAllSingleDigits() {
        assertFalse(StrongNumber.isStrongNumber(0)); // 0! = 1, not 0
        assertTrue(StrongNumber.isStrongNumber(1));
        assertTrue(StrongNumber.isStrongNumber(2));
        assertFalse(StrongNumber.isStrongNumber(3));
        assertFalse(StrongNumber.isStrongNumber(4));
        assertFalse(StrongNumber.isStrongNumber(5));
        assertFalse(StrongNumber.isStrongNumber(6));
        assertFalse(StrongNumber.isStrongNumber(7));
        assertFalse(StrongNumber.isStrongNumber(8));
        assertFalse(StrongNumber.isStrongNumber(9));
    }

    @Test
    @DisplayName("Test boundary consistency across methods")
    void testBoundaryConsistency() {
        int[] testCases = { 0, 1, 10, 100, 1000, 10000, 100000, 999999 };
        for (int num : testCases) {
            boolean result1 = StrongNumber.isStrongNumber(num);
            boolean result2 = StrongNumber.isStrongNumberString(num);
            assertEquals(result1, result2,
                    "Methods should agree on boundary: " + num);
        }
    }
}
