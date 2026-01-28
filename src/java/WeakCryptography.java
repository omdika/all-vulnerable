package java;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.*;
import java.util.Base64;

/**
 * This class contains Weak Cryptography vulnerabilities.
 * Used for testing SAST tools.
 */
public class WeakCryptography {

    // VULNERABLE: Weak encryption algorithm
    private static final String ALGORITHM = "DES";

    // VULNERABLE: Hardcoded encryption key
    private static final String SECRET_KEY = "12345678";

    // VULNERABLE: Weak IV
    private static final String IV = "12345678";

    /**
     * Vulnerable DES encryption
     */
    public String encryptWithDES(String plaintext) throws Exception {
        // VULNERABLE: Using DES (weak algorithm)
        DESKeySpec keySpec = new DESKeySpec(SECRET_KEY.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey key = keyFactory.generateSecret(keySpec);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encrypted = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * ECB mode encryption (vulnerable)
     */
    public String encryptWithECB(String plaintext) throws Exception {
        // VULNERABLE: Using ECB mode
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        byte[] encrypted = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * MD5 hash (cryptographically weak)
     */
    public String hashWithMD5(String input) throws NoSuchAlgorithmException {
        // VULNERABLE: Using MD5
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(input.getBytes());

        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * SHA-1 hash (weak)
     */
    public String hashWithSHA1(String input) throws NoSuchAlgorithmException {
        // VULNERABLE: Using SHA-1
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digest = md.digest(input.getBytes());

        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * Weak random number generation
     */
    public int generateWeakRandom() {
        // VULNERABLE: Using java.util.Random for security purposes
        Random rand = new Random();
        return rand.nextInt();
    }

    /**
     * Hardcoded password in code
     */
    public boolean checkPassword(String input) {
        // VULNERABLE: Hardcoded password
        String hardcodedPassword = "Admin@123";
        return input.equals(hardcodedPassword);
    }

    /**
     * Safe encryption example
     */
    public String safeEncrypt(String plaintext) throws Exception {
        // SAFE: Using AES-GCM with proper key management
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // Strong key size
        SecretKey key = keyGen.generateKey();

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        byte[] iv = new byte[12]; // Proper IV size
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(128, iv));
        byte[] encrypted = cipher.doFinal(plaintext.getBytes());

        // Combine IV and ciphertext
        byte[] combined = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

        return Base64.getEncoder().encodeToString(combined);
    }
}