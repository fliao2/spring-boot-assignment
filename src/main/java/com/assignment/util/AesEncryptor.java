package com.assignment.util;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Component;

@Component
public class AesEncryptor implements IEncryptor {
	private final int SALT_SIZE = 16;
	
	public String Encrypt(String password, String text) {
        final String salt = KeyGenerators.string().generateKey();

        TextEncryptor encryptor = Encryptors.text(password, salt);      
        
        String encryptedText = encryptor.encrypt(text);
        
        // Prepend the encrypted text with the salt, otherwise the salt is lost when needed for decryption.
		return salt + encryptedText;
	}
	
	public String Decrypt(String password, String text) {
		// Extract the salt from the encrypted text (it was prepended in the encrypt function).
		final String salt = text.substring(0,SALT_SIZE);
		
		TextEncryptor decryptor = Encryptors.text(password, salt);
		
		// Decrypt the encrypted text, minus the salt.
        String decryptedText = decryptor.decrypt(text.substring(SALT_SIZE));

		return decryptedText;
	}
}
