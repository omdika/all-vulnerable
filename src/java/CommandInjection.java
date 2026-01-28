package java;

import java.io.*;

/**
 * This class contains Command Injection vulnerabilities.
 * Used for testing SAST tools.
 */
public class CommandInjection {

    /**
     * Vulnerable command execution using Runtime.exec()
     */
    public void executeCommand(String userInput) throws IOException {
        // VULNERABLE: Direct command concatenation
        String command = "ping -c 1 " + userInput;
        Runtime.getRuntime().exec(command);
    }

    /**
     * Command injection with ProcessBuilder
     */
    public void executeWithProcessBuilder(String fileName) throws IOException {
        // VULNERABLE: User input in command arguments
        ProcessBuilder pb = new ProcessBuilder("cat", fileName);
        pb.start();
    }

    /**
     * Command injection with shell execution
     */
    public void executeShellCommand(String hostname) throws IOException {
        // VULNERABLE: Using shell with user input
        String[] cmd = {"/bin/sh", "-c", "nslookup " + hostname};
        Runtime.getRuntime().exec(cmd);
    }

    /**
     * Multiple command injection vectors
     */
    public void complexCommandInjection(String input) throws IOException {
        // VULNERABLE: Multiple injection points
        String cmd = "echo " + input + " | grep 'test'";
        Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", cmd});
    }

    /**
     * Safe example using whitelist validation
     */
    public void safeCommandExecution(String ipAddress) throws IOException {
        // SAFE: Validate input before use
        if (ipAddress.matches("^[0-9.]+$")) {
            String command = "ping -c 1 " + ipAddress;
            Runtime.getRuntime().exec(command);
        }
    }
}