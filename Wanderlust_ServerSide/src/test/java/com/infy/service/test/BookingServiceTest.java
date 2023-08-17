package com.infy.service.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.infy.dto.BookingDTO;
import com.infy.dto.DestinationDTO;
import com.infy.dto.DetailsDTO;
import com.infy.dto.ItineraryDTO;
import com.infy.dto.UserDTO;
import com.infy.entity.Booking;
import com.infy.entity.Destination;
import com.infy.entity.Details;
import com.infy.entity.Itinerary;
import com.infy.entity.User;
import com.infy.exception.WanderLustException;
import com.infy.repository.BookingRepository;
import com.infy.repository.PackageRepository;
import com.infy.repository.UserRepository;
import com.infy.service.BookingServiceImpl;

@SpringBootTest
public class BookingServiceTest {
	@Mock
	private BookingRepository bookingRepository;
	
	@Mock
	private UserRepository userRepository;

}