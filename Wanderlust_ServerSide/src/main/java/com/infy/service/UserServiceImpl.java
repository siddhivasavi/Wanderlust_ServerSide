package com.infy.service;

import java.security.NoSuchAlgorithmException;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.dto.UserDTO;
import com.infy.entity.User;
import com.infy.exception.WanderLustException;
import com.infy.repository.UserRepository;
import com.infy.utility.HashingUtility;


@Service(value = "userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;


	@Override
	public UserDTO authenticateUser(String contactNumber, String password) throws WanderLustException {

		User optionalUser = userRepository.findByContactNumber(contactNumber);
		if (optionalUser == null) {
			throw new WanderLustException("UserService.INVALID_CREDENTIALS");
		}

		String passwordFromDB = optionalUser.getPassword();

		if (passwordFromDB != null) {
			try {
				String hashedPassword = HashingUtility.getHashValue(password);
				if (hashedPassword.equals(passwordFromDB)) {
					UserDTO userObject = new UserDTO();
					userObject.setContactNumber(optionalUser.getContactNumber());
					userObject.setEmailId(optionalUser.getEmailId());
					userObject.setUserId(optionalUser.getUserId());
					userObject.setUserName(optionalUser.getUserName());
					return userObject;
				} else
					throw new WanderLustException("UserService.INVALID_CREDENTIALS");
			} catch (NoSuchAlgorithmException e) {
				throw new WanderLustException("UserService.HASH_FUNCTION_EXCEPTION");
			}

		} else
			throw new WanderLustException("UserService.INVALID_CREDENTIALS");

	}

	@Override
	public Integer registerUser(UserDTO user) throws WanderLustException {
		User newuser = userRepository.findByContactNumber(user.getContactNumber());
		if(newuser!=null)
			throw new WanderLustException("UserService.CONTACT_NUMBER_ALREADY_EXISTS");
		User users = new User();
		users.setContactNumber(user.getContactNumber());
		users.setEmailId(user.getEmailId());
		users.setUserName(user.getUserName());
	
		try {
		String hashedPassword = HashingUtility.getHashValue(user.getPassword());
	users.setPassword(hashedPassword);
			
		} catch(NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			throw new WanderLustException("UserService.HASH_FUNCTION_EXCEPTION");
			
		}
	userRepository.save(users);
		

		
     return users.getUserId();
	}

}
