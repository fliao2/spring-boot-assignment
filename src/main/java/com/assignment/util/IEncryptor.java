package com.assignment.util;

public interface IEncryptor {
	String Encrypt(String password, String text);
	String Decrypt(String password, String text);
}
