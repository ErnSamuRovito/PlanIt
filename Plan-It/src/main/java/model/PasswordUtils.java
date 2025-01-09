package model;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;

public class PasswordUtils {
    private static final int ITERATIONS = 10000; // Numero di iterazioni per rendere l'hash più sicuro
    private static final int KEY_LENGTH = 256; // La lunghezza della chiave in bit
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256"; // Algoritmo di hashing

    // Metodo per generare un hash sicuro per la password
    public static String hashPassword(String plainPassword) {
        try {
            // Crea un salt casuale per l'utente
            byte[] salt = new byte[16]; // Dimensione del salt
            new java.security.SecureRandom().nextBytes(salt);

            // Crea la chiave derivata dalla password usando PBKDF2
            KeySpec spec = new PBEKeySpec(plainPassword.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = skf.generateSecret(spec).getEncoded();

            // Restituisce l'hash come una stringa Base64, concatenando salt e hash
            return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        try {
            // Decodifica l'hash salvato nel database
            String[] parts = hashedPassword.split(":");
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] storedHash = Base64.getDecoder().decode(parts[1]);

            // Crea la chiave derivata dalla password fornita
            KeySpec spec = new PBEKeySpec(plainPassword.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = skf.generateSecret(spec).getEncoded();

            // Confronta l'hash generato con quello memorizzato nel database
            for (int i = 0; i < storedHash.length; i++) {
                if (storedHash[i] != hash[i]) {
                    return false; // La password non è corretta
                }
            }
            return true; // La password è corretta
        } catch (Exception e) {
            throw new RuntimeException("Error verifying password", e);
        }
    }
}
