/**
 * Memory-related vulnerabilities for C/C++.
 * Used for testing SAST tools.
 */

#include <iostream>
#include <cstring>
#include <cstdlib>
#include <vector>
#include <string>

/**
 * Stack buffer overflow with read
 */
void stack_buffer_overflow_read() {
    char buffer[32];

    // VULNERABLE: Reading more than buffer size
    std::cin.read(buffer, 100); // May overflow buffer
}

/**
 * Heap corruption
 */
void heap_corruption() {
    char* buffer = new char[64];

    // VULNERABLE: Writing beyond allocated memory
    for (int i = 0; i < 100; i++) {
        buffer[i] = 'A'; // Corrupts heap
    }

    delete[] buffer;
}

/**
 * Type confusion vulnerability
 */
class Base {
public:
    virtual void print() {
        std::cout << "Base class" << std::endl;
    }
};

class Derived : public Base {
public:
    void print() override {
        std::cout << "Derived class" << std::endl;
    }

    void secret() {
        std::cout << "Secret method" << std::endl;
    }
};

void type_confusion(Base* obj) {
    // VULNERABLE: Unsafe downcast
    Derived* derived = static_cast<Derived*>(obj);
    derived->secret(); // Could crash if obj is not Derived
}

/**
 * Race condition in multi-threaded code
 */
#include <thread>

int shared_counter = 0;

void increment_counter() {
    // VULNERABLE: Not thread-safe
    for (int i = 0; i < 1000; i++) {
        shared_counter++; // Race condition
    }
}

void race_condition_example() {
    std::thread t1(increment_counter);
    std::thread t2(increment_counter);

    t1.join();
    t2.join();

    std::cout << "Counter: " << shared_counter << std::endl;
}

/**
 * NULL pointer dereference
 */
void null_pointer_dereference(char* ptr) {
    // VULNERABLE: Dereferencing without NULL check
    if (ptr[0] == 'A') { // Could crash if ptr is NULL
        std::cout << "Starts with A" << std::endl;
    }
}

/**
 * Division by zero
 */
int division_by_zero(int a, int b) {
    // VULNERABLE: No check for zero
    return a / b;
}

/**
 * Signed/unsigned mismatch
 */
void signed_unsigned_mismatch() {
    int signed_val = -1;
    unsigned int unsigned_val = 100;

    // VULNERABLE: Comparison between signed and unsigned
    if (signed_val < unsigned_val) {
        std::cout << "Unexpected result due to conversion" << std::endl;
    }
}

/**
 * Incorrect sizeof usage
 */
void incorrect_sizeof() {
    char* buffer = new char[100];

    // VULNERABLE: sizeof(pointer) instead of sizeof(array)
    memset(buffer, 0, sizeof(buffer)); // Only clears sizeof(char*) bytes

    delete[] buffer;
}

/**
 * Memory leak
 */
void memory_leak() {
    // VULNERABLE: Allocating memory without freeing
    char* buffer = new char[1024 * 1024]; // 1MB leak

    // Forgot to delete[] buffer;
}

/**
 * Safe alternatives
 */
void safe_buffer_operations() {
    const size_t BUFFER_SIZE = 64;
    char buffer[BUFFER_SIZE];

    // SAFE: Check bounds before read
    size_t bytes_to_read = 100;
    if (bytes_to_read > BUFFER_SIZE) {
        bytes_to_read = BUFFER_SIZE;
    }

    std::cin.read(buffer, bytes_to_read);
}

void safe_memory_allocation(size_t size) {
    // SAFE: Check for allocation failure
    char* buffer = new (std::nothrow) char[size];

    if (!buffer) {
        std::cerr << "Memory allocation failed!" << std::endl;
        return;
    }

    // SAFE: Use RAII or smart pointers
    std::unique_ptr<char[]> safe_buffer(buffer);

    // Memory automatically freed when safe_buffer goes out of scope
}

void safe_pointer_dereference(char* ptr) {
    // SAFE: Check for NULL before dereferencing
    if (ptr != nullptr && ptr[0] == 'A') {
        std::cout << "Starts with A" << std::endl;
    }
}

int safe_division(int a, int b) {
    // SAFE: Check for zero
    if (b == 0) {
        std::cerr << "Division by zero!" << std::endl;
        return 0;
    }

    return a / b;
}

/**
 * Thread-safe counter with mutex
 */
#include <mutex>

std::mutex counter_mutex;
int safe_counter = 0;

void safe_increment_counter() {
    std::lock_guard<std::mutex> lock(counter_mutex);
    safe_counter++;
}

int main() {
    // Example usage
    char test[] = "Test";

    safe_buffer_operations();
    safe_memory_allocation(100);
    safe_pointer_dereference(test);

    return 0;
}