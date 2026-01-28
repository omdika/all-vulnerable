/**
 * Buffer Overflow vulnerabilities for C/C++.
 * Used for testing SAST tools like Coverity, Fortify.
 */

#include <iostream>
#include <cstring>
#include <cstdio>
#include <cstdlib>

/**
 * Classic buffer overflow with strcpy
 */
void vulnerable_strcpy(char* input) {
    char buffer[64];

    // VULNERABLE: No bounds checking
    strcpy(buffer, input);
    printf("Buffer: %s\n", buffer);
}

/**
 * Buffer overflow with strcat
 */
void vulnerable_strcat(char* input) {
    char buffer[128] = "Hello ";

    // VULNERABLE: strcat can overflow buffer
    strcat(buffer, input);
    printf("Result: %s\n", buffer);
}

/**
 * Buffer overflow with sprintf
 */
void vulnerable_sprintf(char* name) {
    char buffer[100];

    // VULNERABLE: sprintf doesn't check bounds
    sprintf(buffer, "Hello, %s! Welcome to our system.", name);
    printf("%s\n", buffer);
}

/**
 * Buffer overflow with gets (extremely dangerous)
 */
void vulnerable_gets_example() {
    char buffer[256];

    // VULNERABLE: gets() is always dangerous
    printf("Enter input: ");
    gets(buffer); // Never use gets()!
    printf("You entered: %s\n", buffer);
}

/**
 * Integer overflow
 */
int vulnerable_integer_overflow(int size1, int size2) {
    // VULNERABLE: Possible integer overflow
    int total_size = size1 + size2;

    // If size1 + size2 > INT_MAX, overflow occurs
    char* buffer = (char*)malloc(total_size);

    if (buffer) {
        // Use buffer
        free(buffer);
    }

    return total_size;
}

/**
 * Heap buffer overflow
 */
void vulnerable_heap_overflow(char* input) {
    char* buffer = (char*)malloc(64);

    if (buffer) {
        // VULNERABLE: No bounds checking
        strcpy(buffer, input);

        free(buffer);
    }
}

/**
 * Format string vulnerability
 */
void vulnerable_format_string(char* input) {
    // VULNERABLE: User input as format string
    printf(input); // Never do this!
}

/**
 * Use after free
 */
void use_after_free() {
    char* buffer = (char*)malloc(64);

    if (buffer) {
        strcpy(buffer, "Hello");
        free(buffer);

        // VULNERABLE: Using memory after free
        printf("Buffer: %s\n", buffer);
    }
}

/**
 * Double free
 */
void double_free() {
    char* buffer = (char*)malloc(64);

    if (buffer) {
        free(buffer);

        // VULNERABLE: Freeing memory twice
        free(buffer);
    }
}

/**
 * Uninitialized variable
 */
void uninitialized_variable() {
    int value;

    // VULNERABLE: Using uninitialized variable
    if (value > 0) {
        printf("Value is positive\n");
    }
}

/**
 * Safe alternatives
 */
void safe_strcpy(char* input) {
    char buffer[64];

    // SAFE: Using strncpy with bounds checking
    strncpy(buffer, input, sizeof(buffer) - 1);
    buffer[sizeof(buffer) - 1] = '\0';

    printf("Buffer: %s\n", buffer);
}

void safe_sprintf(char* name) {
    char buffer[100];

    // SAFE: Using snprintf
    snprintf(buffer, sizeof(buffer), "Hello, %s! Welcome to our system.", name);

    printf("%s\n", buffer);
}

int safe_integer_overflow(int size1, int size2) {
    // SAFE: Check for integer overflow
    if (size1 > INT_MAX - size2) {
        printf("Integer overflow detected!\n");
        return -1;
    }

    int total_size = size1 + size2;
    char* buffer = (char*)malloc(total_size);

    if (buffer) {
        // Use buffer
        free(buffer);
    }

    return total_size;
}

int main() {
    // Example usage
    char test_input[256] = "Test input";

    vulnerable_strcpy(test_input);
    safe_strcpy(test_input);

    return 0;
}