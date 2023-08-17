package com.infy.service.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.infy.dto.UserDTO;
import com.infy.entity.User;
import com.infy.exception.WanderLustException;
import com.infy.repository.UserRepository;
import com.infy.service.UserService;
import com.infy.service.UserServiceImpl;
import com.infy.utility.HashingUtility;

@SpringBootTest
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	 private UserService userServiceImpl = new UserServiceImpl();

	@Test
	public void invalidLoginInvalidUserTest() throws Exception {
		User user = new User();

		String contactNo = "1234567890";
		String password = "abcd";

		user.setPassword("xyz");


	}}