package java;

import java.nio.*;
import java.util.*;

/**
 * This class contains Buffer-related vulnerabilities.
 * Used for testing SAST tools.
 */
public class BufferVulnerabilities {

    /**
     * Integer overflow vulnerability
     */
    public int calculateSize(int width, int height) {
        // VULNERABLE: Possible integer overflow
        return width * height; // May overflow for large values
    }

    /**
     * Array index out of bounds
     */
    public String getElement(String[] array, int index) {
        // VULNERABLE: No bounds checking
        return array[index];
    }

    /**
     * Heap inspection vulnerability
     */
    public boolean checkPassword(char[] input) {
        // VULNERABLE: Using String for passwords
        String password = "secret123";
        String inputStr = new String(input);
        return inputStr.equals(password); // Password stays in memory
    }

    /**
     * Memory leak simulation
     */
    private static List<byte[]> memoryLeakList = new ArrayList<>();

    public void causeMemoryLeak() {
        // VULNERABLE: Adding to static list without cleanup
        byte[] largeArray = new byte[1024 * 1024]; // 1MB
        memoryLeakList.add(largeArray);
    }

    /**
     * Uninitialized variable usage
     */
    public int useUninitialized() {
        int value;
        // VULNERABLE: Using uninitialized variable
        if (Math.random() > 0.5) {
            value = 10;
        }
        // Compiler won't catch this in some cases
        return value; // Error: variable might not have been initialized
    }

    /**
     * Division by zero possibility
     */
    public int divideNumbers(int a, int b) {
        // VULNERABLE: No check for zero
        return a / b;
    }

    /**
     * Safe array access with bounds checking
     */
    public String safeGetElement(String[] array, int index) {
        // SAFE: Bounds checking
        if (index >= 0 && index < array.length) {
            return array[index];
        }
        return null;
    }

    /**
     * Safe password checking
     */
    public boolean safeCheckPassword(char[] input, char[] expected) {
        // SAFE: Constant-time comparison with char arrays
        if (input.length != expected.length) {
            return false;
        }
        int result = 0;
        for (int i = 0; i < input.length; i++) {
            result |= input[i] ^ expected[i];
        }
        return result == 0;
    }
}