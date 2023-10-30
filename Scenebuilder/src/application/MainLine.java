package application;

import java.io.File;  // Import the File class 
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileOutputStream; 
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.SecretKey;

public class MainLine {
	
	public static void main(String[] args) throws Exception {

		String keystorePath = "C:\\Users\\duy67\\git\\EffortLoggerV2Rep\\Scenebuilder\\src\\application\\keystore.jks";
		String keystorePassword = "3StInliWS633Z/QlHxaKLA==";
		String keyalias = "mykeyalias";
		
		Encryption encrypt = new Encryption();
		
		try {
			File readFile = new File("C:\\Users\\duy67\\git\\EffortLoggerV2Rep\\Scenebuilder\\src\\application\\input.txt");
			Scanner scan = new Scanner(readFile);
			FileOutputStream dataFile = new FileOutputStream("C:\\Users\\duy67\\git\\EffortLoggerV2Rep\\Scenebuilder\\src\\application\\encryptData.txt");
			
            // Rotate key if 90 days have passed
			SecretKey key = encrypt.getCurrentKey();
            
            encrypt.storeKey(key, keystorePath, keystorePassword, keyalias);
            
            // Read inputs from a file
            while (scan.hasNextLine()) {
            	// Plain text to be encrypted
                String plainText = scan.nextLine();
                
                // Encrypt the data
                byte[] encryptedData = encrypt.encrypt(plainText, key);
                
                // Store the encrypted data in a file
                for (int i = 0; i < encryptedData.length; i++) {
                	dataFile.write(encryptedData[i]);
                }
                dataFile.write(',');
                
                // Show the byte string as alpha characters
                System.out.println("Encrypted Text: " + Base64.getEncoder().encodeToString(encryptedData));
                
                // Decrypt the data
                String decryptedText = encrypt.decrypt(encryptedData, key);
                System.out.println("Decrypted Text: " + decryptedText);
            }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
