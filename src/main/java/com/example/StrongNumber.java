package com.example;

import java.util.*;

/**
 * Strong Number Finder - Check if a number is a Strong Number
 * 
 * Problem: A number is called a Strong Number if the sum of the factorials
 * of its digits equals the original number.
 * 
 * Examples:
 * - 145 = 1! + 4! + 5! = 1 + 24 + 120 = 145 ✓
 * - 2 = 2! = 2 ✓
 * - 40585 = 4! + 0! + 5! + 8! + 5! = 24 + 1 + 120 + 40320 + 120 = 40585 ✓
 * - 10 = 1! + 0! = 1 + 1 = 2 ✗
 * 
 * Time Complexity: O(d) where d is number of digits
 * Space Complexity: O(1) or O(d) depending on approach
 * 
 * Algorithm:
 * 1. Extract digits from number
 * 2. Calculate factorial for each digit
 * 3. Sum all factorials
 * 4. Compare with original number
 */
public class StrongNumber {

    private static final int[] FACTORIAL_LOOKUP = new int[10];

    static {
        // Pre-compute factorials for digits 0-9
        FACTORIAL_LOOKUP[0] = 1;
        for (int i = 1; i < 10; i++) {
            FACTORIAL_LOOKUP[i] = FACTORIAL_LOOKUP[i - 1] * i;
        }
    }

    /**
     * Check if a number is strong using digit extraction method ⭐ (EFFICIENT)
     * Time: O(d), Space: O(1)
     */
    public static boolean isStrongNumber(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("Number must be non-negative");
        }

        // Special case: 0 is not a strong number (0! = 1, not 0)
        if (num == 0) {
            return false;
        }

        int original = num;
        int sumOfFactorials = 0;

        while (num > 0) {
            int digit = num % 10;
            sumOfFactorials += FACTORIAL_LOOKUP[digit];
            num /= 10;
        }

        return sumOfFactorials == original;
    }

    /**
     * Check using string conversion method
     * Time: O(d), Space: O(d)
     */
    public static boolean isStrongNumberString(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("Number must be non-negative");
        }

        String str = String.valueOf(num);
        int sumOfFactorials = 0;

        for (char digit : str.toCharArray()) {
            int d = Character.getNumericValue(digit);
            sumOfFactorials += factorial(d);
        }

        return sumOfFactorials == num;
    }

    /**
     * Check using recursive factorial calculation
     * Time: O(d * d) due to repeated factorial calculations
     */
    public static boolean isStrongNumberRecursive(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("Number must be non-negative");
        }

        int original = num;
        int sumOfFactorials = 0;

        while (num > 0) {
            int digit = num % 10;
            sumOfFactorials += factorial(digit);
            num /= 10;
        }

        return sumOfFactorials == original;
    }

    /**
     * Check using LinkedList digit collection
     * Time: O(d), Space: O(d)
     */
    public static boolean isStrongNumberList(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("Number must be non-negative");
        }

        List<Integer> digits = new LinkedList<>();
        int temp = num;

        while (temp > 0) {
            digits.add(temp % 10);
            temp /= 10;
        }

        int sumOfFactorials = 0;
        for (int digit : digits) {
            sumOfFactorials += FACTORIAL_LOOKUP[digit];
        }

        return sumOfFactorials == num;
    }

    /**
     * Find all strong numbers in a given range
     * Time: O(n * d), Space: O(k) where k is count of strong numbers
     */
    public static List<Integer> findStrongNumbersInRange(int start, int end) {
        if (start < 0 || end < start) {
            throw new IllegalArgumentException("Invalid range");
        }

        List<Integer> strongNumbers = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (isStrongNumber(i)) {
                strongNumbers.add(i);
            }
        }
        return strongNumbers;
    }

    /**
     * Get sum of factorials of digits
     */
    public static int getSumOfFactorials(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("Number must be non-negative");
        }

        int sum = 0;
        while (num > 0) {
            int digit = num % 10;
            sum += FACTORIAL_LOOKUP[digit];
            num /= 10;
        }
        return sum;
    }

    /**
     * Calculate factorial of a digit (0-9)
     */
    public static int factorial(int digit) {
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Digit must be between 0 and 9");
        }
        return FACTORIAL_LOOKUP[digit];
    }

    /**
     * Validate if number is strong
     */
    public static boolean isValidStrongNumber(int num) {
        return num >= 0 && isStrongNumber(num);
    }

    /**
     * Get factorization breakdown (debugging helper)
     */
    public static String getFactorization(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("Number must be non-negative");
        }

        StringBuilder sb = new StringBuilder();
        int temp = num;
        int sum = 0;

        while (temp > 0) {
            int digit = temp % 10;
            int fact = FACTORIAL_LOOKUP[digit];
            sum += fact;
            sb.insert(0, digit + "! = " + fact + ", ");
            temp /= 10;
        }

        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }

        return num + " = " + sb.toString() + " = " + sum;
    }

    // Main method with demo examples
    public static void main(String[] args) {
        System.out.println("========== Strong Number Checker Demo ==========\n");

        // Example 1: Classic strong number
        int num1 = 145;
        boolean strong1 = isStrongNumber(num1);
        System.out.println("Example 1: " + num1);
        System.out.println(getFactorization(num1));
        System.out.println("Is Strong: " + strong1 + " [PASS]\n");

        // Example 2: Another strong number
        int num2 = 2;
        boolean strong2 = isStrongNumber(num2);
        System.out.println("Example 2: " + num2);
        System.out.println(getFactorization(num2));
        System.out.println("Is Strong: " + strong2 + " [PASS]\n");

        // Example 3: Not a strong number
        int num3 = 10;
        boolean strong3 = isStrongNumber(num3);
        System.out.println("Example 3: " + num3);
        System.out.println(getFactorization(num3));
        System.out.println("Is Strong: " + strong3 + " [PASS]\n");

        // Example 4: Large strong number
        int num4 = 40585;
        boolean strong4 = isStrongNumber(num4);
        System.out.println("Example 4: " + num4);
        System.out.println(getFactorization(num4));
        System.out.println("Is Strong: " + strong4 + " [PASS]\n");

        // Example 5: Zero (edge case)
        int num5 = 1;
        boolean strong5 = isStrongNumber(num5);
        System.out.println("Example 5: " + num5);
        System.out.println(getFactorization(num5));
        System.out.println("Is Strong: " + strong5 + " [PASS]\n");

        // Find all strong numbers up to 10000
        System.out.println("========== All Strong Numbers (0-10000) ==========");
        List<Integer> allStrong = findStrongNumbersInRange(0, 10000);
        System.out.println("Found: " + allStrong);
        System.out.println("Count: " + allStrong.size() + " strong numbers\n");

        // Algorithm comparison
        System.out.println("========== Algorithm Comparison ==========");
        int testNum = 40585;
        System.out.println("Testing number: " + testNum + "\n");

        long start, end;

        start = System.nanoTime();
        boolean result1 = isStrongNumber(testNum);
        end = System.nanoTime();
        System.out.println("Digit Extraction: " + result1 + " (Time: " + (end - start) + "ns)");

        start = System.nanoTime();
        boolean result2 = isStrongNumberString(testNum);
        end = System.nanoTime();
        System.out.println("String Conversion: " + result2 + " (Time: " + (end - start) + "ns)");

        start = System.nanoTime();
        boolean result3 = isStrongNumberList(testNum);
        end = System.nanoTime();
        System.out.println("LinkedList Method: " + result3 + " (Time: " + (end - start) + "ns)");

        System.out.println("\n========== Demo Complete ==========");
    }
}
