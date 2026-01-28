package java;

import java.io.*;

/**
 * This class contains Race Condition vulnerabilities.
 * Used for testing SAST tools.
 */
public class RaceCondition {

    private static int counter = 0;
    private static boolean initialized = false;

    /**
     * Check-then-act race condition
     */
    public void checkThenAct(File file) throws IOException {
        // VULNERABLE: TOCTOU race condition
        if (!file.exists()) {
            // Between check and create, another process may create the file
            file.createNewFile();
        }
    }

    /**
     * Non-atomic increment
     */
    public void incrementCounter() {
        // VULNERABLE: Non-atomic operation
        counter = counter + 1;
    }

    /**
     * Double-checked locking (broken in some Java versions)
     */
    public static RaceCondition getInstance() {
        // VULNERABLE: Double-checked locking anti-pattern
        if (instance == null) {
            synchronized (RaceCondition.class) {
                if (instance == null) {
                    instance = new RaceCondition();
                }
            }
        }
        return instance;
    }
    private static RaceCondition instance;

    /**
     * Lazy initialization without synchronization
     */
    public void lazyInit() {
        // VULNERABLE: Unsafe publication
        if (!initialized) {
            initialized = true;
            // Initialization code here
        }
    }

    /**
     * File race condition
     */
    public void processFile(String fileName) throws IOException {
        // VULNERABLE: File operations without locking
        File file = new File(fileName);
        if (file.length() > 0) {
            // File may change between length check and read
            FileInputStream fis = new FileInputStream(file);
            fis.read();
            fis.close();
        }
    }

    /**
     * Safe synchronized method
     */
    public synchronized void safeIncrement() {
        // SAFE: Synchronized method
        counter++;
    }

    /**
     * Safe file handling with locking
     */
    public void safeProcessFile(String fileName) throws IOException {
        // SAFE: Using try-with-resources and proper file handling
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "r")) {
            if (raf.length() > 0) {
                raf.read();
            }
        }
    }

    /**
     * Thread-safe singleton with holder class
     */
    private static class Holder {
        static final RaceCondition INSTANCE = new RaceCondition();
    }

    public static RaceCondition getSafeInstance() {
        // SAFE: Initialization-on-demand holder idiom
        return Holder.INSTANCE;
    }
}