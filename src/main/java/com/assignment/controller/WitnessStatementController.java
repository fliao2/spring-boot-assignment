package com.assignment.controller;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.assignment.entity.WitnessStatement;
import com.assignment.util.IEncryptor;
import com.assignment.dao.WitnessStatementRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Controller
@RequestMapping("assignment")
public class WitnessStatementController {
	private static Log logger = LogFactory.getLog(WitnessStatementController.class);
	
	@Autowired
	private WitnessStatementRepository witnessStatementRepository;
	
	@Autowired
    private IEncryptor aesEncryptor;
	
	@GetMapping(path="/witness-statement")
	public @ResponseBody Iterable<WitnessStatement> getAllWitnessStatements(@RequestHeader("Authorization") String authorization) {
		List<WitnessStatement> list = new ArrayList<WitnessStatement>();
		try {			
			String password = getPasswordFromAuthorization(authorization);
			
			for (WitnessStatement witnessStatementDb : witnessStatementRepository.findAll()) {
				WitnessStatement witnessStatement = new WitnessStatement();
				
				try {
					witnessStatement.setFirstName(aesEncryptor.Decrypt(password, witnessStatementDb.getFirstName()));
					witnessStatement.setMiddleName(aesEncryptor.Decrypt(password, witnessStatementDb.getMiddleName()));
					witnessStatement.setLastName(aesEncryptor.Decrypt(password, witnessStatementDb.getLastName()));
					witnessStatement.setGender(aesEncryptor.Decrypt(password, witnessStatementDb.getDateOfBirth()));
					witnessStatement.setDateOfBirth(aesEncryptor.Decrypt(password, witnessStatementDb.getDateOfBirth()));
					witnessStatement.setAddress(aesEncryptor.Decrypt(password, witnessStatementDb.getAddress()));
					witnessStatement.setEmail(aesEncryptor.Decrypt(password, witnessStatementDb.getEmail()));
					witnessStatement.setPhone(aesEncryptor.Decrypt(password, witnessStatementDb.getPhone()));
					witnessStatement.setWitnessStatement(aesEncryptor.Decrypt(password, witnessStatementDb.getWitnessStatement()));
					witnessStatement.setWitnessStatementId(witnessStatementDb.getWitnessStatementId());
					
					list.add(witnessStatement);
				} catch (IllegalStateException ex) {
					if (ex.getMessage().equals("Unable to invoke Cipher due to bad padding")) {
						// This is the error thrown when an incorrect password is passed into the decrypt function.
						// Just catch the exception and move on.
						continue;
					} else {
						logger.debug(ex.getMessage());
						break;
					}
				}	
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return null;
		}
		return list;
	}
	
	@GetMapping(path="/witness-statement/{id}")
	public @ResponseBody WitnessStatement getWitnessStatement(@RequestHeader("Authorization") String authorization,
			@PathVariable("id") Long id) {
		WitnessStatement witnessStatement = new WitnessStatement();
		
		try {		
			WitnessStatement witnessStatementDb = witnessStatementRepository.findOne(id);
			if (witnessStatementDb == null) {
				return null;
			}
			
			String password = getPasswordFromAuthorization(authorization);
			
			witnessStatement.setWitnessStatementId(witnessStatementDb.getWitnessStatementId());
			witnessStatement.setFirstName(aesEncryptor.Decrypt(password, witnessStatementDb.getFirstName()));
			witnessStatement.setMiddleName(aesEncryptor.Decrypt(password, witnessStatementDb.getMiddleName()));
			witnessStatement.setLastName(aesEncryptor.Decrypt(password, witnessStatementDb.getLastName()));
			witnessStatement.setGender(aesEncryptor.Decrypt(password, witnessStatementDb.getDateOfBirth()));
			witnessStatement.setDateOfBirth(aesEncryptor.Decrypt(password, witnessStatementDb.getDateOfBirth()));
			witnessStatement.setAddress(aesEncryptor.Decrypt(password, witnessStatementDb.getAddress()));
			witnessStatement.setEmail(aesEncryptor.Decrypt(password, witnessStatementDb.getEmail()));
			witnessStatement.setPhone(aesEncryptor.Decrypt(password, witnessStatementDb.getPhone()));
			witnessStatement.setWitnessStatement(aesEncryptor.Decrypt(password, witnessStatementDb.getWitnessStatement()));
		} catch (IllegalStateException ex) {
			if (ex.getMessage().equals("Unable to invoke Cipher due to bad padding")) {
				// This is the error thrown when an incorrect password is passed into the decrypt function.
				// Just catch the exception and move on.
				logger.debug(ex.getMessage());
				return null;
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return null;
		}
		
		return witnessStatement;
	}
		
	@PostMapping(path="/witness-statement")
	public @ResponseBody String addWitnessStatement (@RequestHeader("Authorization") String authorization,
			@RequestBody WitnessStatement witnessStatement) {
		try {		
	        String password = getPasswordFromAuthorization(authorization);
	        
	        WitnessStatement witnessStatementDb = new WitnessStatement();
			witnessStatementDb.setFirstName(aesEncryptor.Encrypt(password, witnessStatement.getFirstName()));
			witnessStatementDb.setMiddleName(aesEncryptor.Encrypt(password, witnessStatement.getMiddleName()));
			witnessStatementDb.setLastName(aesEncryptor.Encrypt(password, witnessStatement.getLastName()));
			witnessStatementDb.setGender(aesEncryptor.Encrypt(password, witnessStatement.getDateOfBirth()));
			witnessStatementDb.setDateOfBirth(aesEncryptor.Encrypt(password, witnessStatement.getDateOfBirth()));
			witnessStatementDb.setAddress(aesEncryptor.Encrypt(password, witnessStatement.getAddress()));
			witnessStatementDb.setEmail(aesEncryptor.Encrypt(password, witnessStatement.getEmail()));
			witnessStatementDb.setPhone(aesEncryptor.Encrypt(password, witnessStatement.getPhone()));
			witnessStatementDb.setWitnessStatement(aesEncryptor.Encrypt(password, witnessStatement.getWitnessStatement()));
			
			witnessStatementRepository.save(witnessStatementDb);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return "Error";
		}
		
		return "Saved";
	}
	
	private String getPasswordFromAuthorization(String authorization) {
		// The basic authorization string is "Basic <Base64_string>", so take the 2nd word.
		String[] words = authorization.split(" ");
		String usernamePassword = words[1];
		
		// Next, decode the Base64 string.
		String decoded = new String(Base64.decodeBase64(usernamePassword.getBytes()));
		
		// The plain text Base64 string is in the form of username:password, so split the colon and take the 2nd word.
		words = decoded.split(":");
		String password = words[1];
		
		return password;
	}
}
