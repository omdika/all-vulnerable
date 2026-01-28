package java;

import java.io.*;
import java.nio.file.*;

/**
 * This class contains Path Traversal vulnerabilities.
 * Used for testing SAST tools.
 */
public class PathTraversal {

    /**
     * Vulnerable file read with user input
     */
    public String readFile(String fileName) throws IOException {
        // VULNERABLE: Direct path concatenation
        File file = new File("/var/www/uploads/" + fileName);

        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    /**
     * Path traversal with NIO API
     */
    public byte[] readFileNio(String fileName) throws IOException {
        // VULNERABLE: User input in path resolution
        Path path = Paths.get("/var/www/data/", fileName);
        return Files.readAllBytes(path);
    }

    /**
     * File write with path traversal
     */
    public void writeFile(String userFile, String content) throws IOException {
        // VULNERABLE: User controls file path
        File file = new File("/tmp/userfiles/" + userFile);
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(content);
        }
    }

    /**
     * Multiple path traversal vectors
     */
    public void deleteFile(String fileName) {
        // VULNERABLE: No validation
        File file = new File(System.getProperty("user.home") + "/files/" + fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Safe example with path normalization and validation
     */
    public String readFileSafe(String fileName) throws IOException {
        // SAFE: Validate and normalize path
        Path basePath = Paths.get("/var/www/uploads/").toAbsolutePath().normalize();
        Path userPath = basePath.resolve(fileName).normalize();

        if (!userPath.startsWith(basePath)) {
            throw new SecurityException("Invalid path");
        }

        return new String(Files.readAllBytes(userPath));
    }
}