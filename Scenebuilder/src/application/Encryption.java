package application;

import javax.crypto.Cipher; 
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;


public class Encryption {
	// Key for encryption (AES-256)
	final static int KEYLENGTH = 256;
	// Interval for key rotation policy, 90 days in milliseconds
	private final static long ROTATION_INTERVAL = 90 * 24 * 60 * 60 * 1000;
	private static SecretKey key;
	private static long lastRotationTimeStamp;
	
	// Constructor: Initialize a new key
	public Encryption() throws NoSuchAlgorithmException  {
		// Initialize key rotation with a new key
		generateAESkey(KEYLENGTH);
	}
	
	public static SecretKey getCurrentKey() throws NoSuchAlgorithmException {
		// Check if it's time to rotate the key
		long currentTime = new Date().getTime();
		if (currentTime - lastRotationTimeStamp >= ROTATION_INTERVAL) {
			generateAESkey(KEYLENGTH);
		}
		return key;
	}
	
	// Method to generate a random AES key for encryption
	private static SecretKey generateAESkey(int keyLength) throws NoSuchAlgorithmException {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(keyLength);
		key = keyGen.generateKey();
		lastRotationTimeStamp = new Date().getTime();
		System.out.println("Key rotated at " + new Date(lastRotationTimeStamp));
		return key;
	}

	// Encrypts the input
	public static byte[] encrypt(String input, SecretKey key) throws Exception {
		Key secretKey = new SecretKeySpec(key.getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(input.getBytes());
	}
	
	// Decrypts the input
	public static String decrypt(byte[] encryptedData, SecretKey key) throws Exception {
		Key secretKey = new SecretKeySpec(key.getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedData);
        return new String(decryptedBytes);
	}
	
	// Store the Secret Key in a key store (in this case a file)
	public static void storeKey(SecretKey key, String path, String pw, String alias) throws Exception {
		KeyStore keystore = KeyStore.getInstance("JCEKS");
		char[] password = pw.toCharArray();
		
		// Load an existing key store or create a new one
		try (FileInputStream keystoreFile = new FileInputStream(path)) {
			keystore.load(keystoreFile, password);
		} catch (Exception e) {
			keystore.load(null, password);
		}
		
		// store the secret key in the key store
		KeyStore.SecretKeyEntry keyEntry = new KeyStore.SecretKeyEntry(key);
		keystore.setEntry(alias, keyEntry, new KeyStore.PasswordProtection(password));
		
		// save the key store to a file
		try (FileOutputStream keystoreFile = new FileOutputStream(path)) {
			keystore.store(keystoreFile, password);
		}	catch (Exception e) {
			e.printStackTrace();
		}
	}
}
