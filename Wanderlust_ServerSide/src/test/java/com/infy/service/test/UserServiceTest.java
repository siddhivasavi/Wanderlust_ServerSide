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


		Mockito.when(userRepository.findByContactNumber(contactNo)).thenReturn(user);
		WanderLustException exception = Assertions.assertThrows(WanderLustException.class,
				() -> userServiceImpl.authenticateUser(contactNo, password));
		Assertions.assertEquals("UserService.INVALID_CREDENTIALS", exception.getMessage());

	}
	
	@Test
	public void validAuthenticateUser() throws Exception{
		
		UserDTO userdto= new UserDTO();
		userdto.setContactNumber("9899757623");
		userdto.setEmailId("Martin@gmail.com");
		userdto.setPassword("Martin@123");
		userdto.setUserName("Martin");
		userdto.setUserId(301);
		
		
		User user= new User();
	    user.setContactNumber(userdto.getContactNumber());
	    user.setEmailId(userdto.getEmailId());
	    String hashcode = HashingUtility.getHashValue("Martin@123");
	    user.setPassword(hashcode);
	    user.setUserName(userdto.getUserName());
	    user.setUserId(userdto.getUserId());
	   
	    
	    Mockito.when(userRepository.findByContactNumber(userdto.getContactNumber())).thenReturn(user);
	    UserDTO userDTO= userServiceImpl.authenticateUser(userdto.getContactNumber(),userdto.getPassword());
	    Assertions.assertEquals(userdto.getUserId(),userDTO.getUserId());
	}
	@Test
	public void noUserFoundAuthenticateUserTest() throws Exception{
		Mockito.when(userRepository.findByContactNumber("8823458890")).thenReturn(null);
	    WanderLustException e= Assertions.assertThrows(WanderLustException.class,()->userServiceImpl.authenticateUser("8823458890","Amazon@1234"));
	    Assertions.assertEquals("UserService.INVALID_CREDENTIALS",e.getMessage());
		
	}
	@Test
	public void nullUserPassword()throws Exception{
		UserDTO userdto= new UserDTO();
		userdto.setContactNumber("9899757623");
		userdto.setEmailId("Martin@gmail.com");
		userdto.setPassword("Martin@123");
		userdto.setUserId(100);
		userdto.setUserName("Martin");
		
		User user= new User();
	    user.setContactNumber(userdto.getContactNumber());
	    user.setEmailId(userdto.getEmailId());
	    user.setPassword(null);
	    user.setUserId(userdto.getUserId());
	    user.setUserName(userdto.getUserName());
	    
	    Mockito.when(userRepository.findByContactNumber(userdto.getContactNumber())).thenReturn(user);
	    Exception exp= Assertions.assertThrows(WanderLustException.class,()->userServiceImpl.authenticateUser(userdto.getContactNumber(),userdto.getPassword()));
	    Assertions.assertEquals("UserService.INVALID_CREDENTIALS",exp.getMessage());
		
	}
	@Test
	public void validRegisterUser() throws Exception{
		UserDTO userdto= new UserDTO();
		userdto.setContactNumber("8875632143");
		userdto.setEmailId("jimmy@stark.com");
		userdto.setPassword("Jimmy@123");
		userdto.setUserName("JIMMY");
		
		User user= new User();
	    user.setContactNumber(userdto.getContactNumber());
	    user.setEmailId(userdto.getEmailId());
	    user.setPassword(HashingUtility.getHashValue(userdto.getPassword()));
	    user.setUserName("JIMMY");
	    
	    
	    Mockito.when(userRepository.findByContactNumber(userdto.getContactNumber())).thenReturn(null);
	    Mockito.when(userRepository.save(user)).thenReturn(user);
	    Integer id= userServiceImpl.registerUser(userdto);
	    Assertions.assertNull(id);
	}
	@Test
	public void userAlreadyExistTest() throws Exception{
		UserDTO userdto= new UserDTO();
		userdto.setContactNumber("8765667898");
		userdto.setEmailId("jerry23@gmail.com");
		userdto.setPassword("Jerry@123");
		userdto.setUserName("Jerry");
		
		User user= new User();
	    user.setContactNumber(userdto.getContactNumber());
	    user.setEmailId(userdto.getEmailId());
	    user.setPassword(HashingUtility.getHashValue(userdto.getPassword()));
	    user.setUserName(userdto.getUserName());
	    
	    Mockito.when(userRepository.findByContactNumber(userdto.getContactNumber())).thenReturn(user);
	    WanderLustException e= Assertions.assertThrows(WanderLustException.class,()->userServiceImpl.registerUser(userdto));
	    Assertions.assertEquals("UserService.CONTACT_NUMBER_ALREADY_EXISTS",e.getMessage());	
	}
	}

		
		
	