package java;

import java.io.*;
import java.util.Base64;

/**
 * This class contains Insecure Deserialization vulnerabilities.
 * Used for testing SAST tools.
 */
public class InsecureDeserialization {

    /**
     * Vulnerable deserialization from base64 string
     */
    public Object deserializeFromBase64(String base64Data)
            throws IOException, ClassNotFoundException {
        // VULNERABLE: Deserializing untrusted data
        byte[] data = Base64.getDecoder().decode(base64Data);
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }

    /**
     * Deserialization from file
     */
    public Object deserializeFromFile(String fileName)
            throws IOException, ClassNotFoundException {
        // VULNERABLE: Reading serialized object from file
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        return ois.readObject();
    }

    /**
     * Custom deserialization with validation (still vulnerable)
     */
    public User deserializeUser(String serializedData)
            throws IOException, ClassNotFoundException {
        // VULNERABLE: Custom readObject may still be exploitable
        byte[] data = Base64.getDecoder().decode(serializedData);
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (User) ois.readObject();
    }

    /**
     * Serialization of sensitive object
     */
    public String serializeUser(User user) throws IOException {
        // VULNERABLE: Serializing sensitive data
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(user);
        oos.close();
        return Base64.getEncoder().encodeToString(bos.toByteArray());
    }

    /**
     * Safe alternative using JSON or other formats
     */
    public User safeDeserialize(String jsonData) {
        // SAFE: Using JSON parser instead of Java serialization
        // Implementation would use Jackson, Gson, etc.
        return null;
    }

    // Inner class for demonstration
    static class User implements Serializable {
        private String username;
        private String password; // VULNERABLE: Password in serializable object
        private boolean isAdmin;

        // Custom readObject - still vulnerable if not properly implemented
        private void readObject(ObjectInputStream ois)
                throws IOException, ClassNotFoundException {
            ois.defaultReadObject();
        }
    }
}