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
	

	@Mock
	private PackageRepository packageRepository;
	
	@InjectMocks
	private BookingServiceImpl bookingServiceImpl;
	
	
//	@Test
//	public void InvalidGetBookingRecordTest() throws Exception{
//		Integer userId=1780;
//		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());
//		WanderLustException e = Assertions.assertThrows(WanderLustException.class, ()-> bookingServiceImpl.getBooking(userId));
//		Assertions.assertEquals("BookingService.NO_BOOKING", e.getMessage());
//	}
	
	@Test
	public void InvalidGetBookingRecordTest() throws Exception {
		//Integer usersId = 1780;
		User a = new User();
		a.setUserId(1780);
		UserDTO u = new UserDTO();
		u.setUserId(1780);
		Mockito.when(userRepository.findById(1780)).thenReturn(Optional.empty());
		
		WanderLustException e =Assertions.assertThrows(WanderLustException.class, ()-> bookingServiceImpl.getBooking(u.getUserId()));
		Assertions.assertEquals("BookingService.NO_BOOKING", e.getMessage());
	}
	
	@Test
	
	public void InvalidGetBookingBookingError () throws Exception {
		
		
		//Integer userId = 1780;
		
		User a = new User();
		
		
		a.setUserId(111);
		UserDTO ad = new UserDTO();
		ad.setUserId(111);
		
		
		Mockito.when(userRepository.findById(111)).thenReturn(Optional.of(a));
		Mockito.when(bookingRepository.findByUserEntity(a)).thenReturn(List.of());
		WanderLustException e =Assertions.assertThrows(WanderLustException.class, ()-> bookingServiceImpl.getBooking(111));
		Assertions.assertEquals("BookingService.BOOKING_ERROR", e.getMessage());
	}
	
	@Test
	
	public void InvalidGetBookingTest () throws Exception {
		
		BookingDTO booking = new BookingDTO();
		booking.setCheckIn(LocalDate.of(2023, 2, 12));
		booking.setCheckOut(LocalDate.of(2023, 2, 15));
		booking.setNoOfPeople(3);
		booking.setTimeOfBooking(LocalDateTime.now());
		booking.setTotalCost(3460.0f);
		
		
		DestinationDTO dest = new DestinationDTO();
		dest.setDestinationId("S1861");
		dest.setDestinationName("A Week in Greece : Santorini, Athens & Mykonos");
		dest.setAvailability(30);
		dest.setChargePerPerson(3500.0f);
		dest.setContinent("Europe");
		dest.setDiscount(0.0f);
		dest.setNoOfNights(10);
		dest.setFlightCharge(500.0f);
		dest.setImageUrl("/assests/greece.jpg");
		
		DetailsDTO det = new DetailsDTO();
		det.setDetailsId("S2117");
		
		ItineraryDTO iti = new ItineraryDTO();
		iti.setItineraryId("S9910");
		det.setItinerary(iti);
		dest.setDetails(det);
		
		UserDTO users = new UserDTO();
		users.setUserId(102);
		booking.setDestination(dest);
		booking.setUsers(users);
		
		
		Mockito.when(packageRepository.findById("S1861")).thenReturn(Optional.empty());
		WanderLustException e = Assertions.assertThrows(WanderLustException.class, ()-> bookingServiceImpl.getBooking(102));
		Assertions.assertEquals("BookingService.NO_BOOKING", e.getMessage());
		
	}
	@Test
	
	public void addBookingInvalidDestination() {
		BookingDTO booking = new BookingDTO();
		booking.setCheckIn(LocalDate.of(2023, 2, 12));
		booking.setCheckOut(LocalDate.of(2023, 2, 15));
		booking.setNoOfPeople(3);
		booking.setTimeOfBooking(LocalDateTime.now());
		booking.setTotalCost(3460.0f);
		
		DestinationDTO dest = new DestinationDTO();
		dest.setDestinationId("D1010");
		dest.setDestinationName("A Week in Greece : Santorini, Athens & Mykonos");
		dest.setAvailability(30);
		dest.setChargePerPerson(3500.0f);
		dest.setContinent("Europe");
		dest.setDiscount(0.0f);
		dest.setNoOfNights(10);
		dest.setFlightCharge(500.0f);
		dest.setImageUrl("/assests/greece.jpg");
		
		
		DetailsDTO det = new DetailsDTO();
		det.setDetailsId("DL103");
		
		
		ItineraryDTO iti = new ItineraryDTO();
		iti.setItineraryId("I1003");
		
		det.setItinerary(iti);
		dest.setDetails(det);
		
		UserDTO users = new UserDTO();
		users.setUserId(102);
		User a= new User();
		
		booking.setDestination(dest);
		booking.setUsers(users);


		Mockito.when(userRepository.findById(102)).thenReturn(Optional.of(a));
		Mockito.when(packageRepository.findById("D1003")).thenReturn(Optional.empty());
		WanderLustException e = Assertions.assertThrows(WanderLustException.class, ()-> bookingServiceImpl.addBooking(booking, 102, "DL103"));
		Assertions.assertEquals("BookingService.NO_BOOKING", e.getMessage());
		
		
		
		
		
		
	}
	
	@Test
	public void addBookingIvalidUser () {
		BookingDTO booking = new BookingDTO();
		booking.setCheckIn(LocalDate.of(2023, 2, 12));
		booking.setCheckOut(LocalDate.of(2023, 2, 15));
		booking.setNoOfPeople(3);
		booking.setTimeOfBooking(LocalDateTime.now());
		booking.setTotalCost(3460.0f);
		
		DestinationDTO dest = new DestinationDTO();
		dest.setDestinationId("D1010");
		dest.setDestinationName("A Week in Greece : Santorini, Athens & Mykonos");
		dest.setAvailability(30);
		dest.setChargePerPerson(3500.0f);
		dest.setContinent("Europe");
		dest.setDiscount(0.0f);
		dest.setNoOfNights(10);
		dest.setFlightCharge(500.0f);
		dest.setImageUrl("/assests/greece.jpg");
		
		DetailsDTO det = new DetailsDTO();
		det.setDetailsId("DL103");
		
		
		ItineraryDTO iti = new ItineraryDTO();
		iti.setItineraryId("I1003");
		
		det.setItinerary(iti);
		dest.setDetails(det);
		UserDTO users = new UserDTO();
		users.setUserId(102);
		User a= new User();
		
		booking.setDestination(dest);
		booking.setUsers(users);
		
		Mockito.when(userRepository.findById(102)).thenReturn(Optional.of(a));
		//Mockito.when(packageRepository.findById("D1010")).thenReturn(Optional.empty());
		WanderLustException e = Assertions.assertThrows(WanderLustException.class, ()-> bookingServiceImpl.addBooking(booking, 120, "DL103"));
		Assertions.assertEquals("BookingService.BOOKING_ERROR", e.getMessage());

		
	}
	
	@Test
	
	public void InvalidDeleteBooking() throws Exception {
		Integer bookingId=111111;
		Mockito.when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());
		WanderLustException e = Assertions.assertThrows(WanderLustException.class, ()-> bookingServiceImpl.deleteBooking(bookingId));
		
		Assertions.assertEquals("BookingService.NO_BOOKING", e.getMessage());
	}
	@Test
	public void ValidDeleteBooking() throws Exception {
		
		Booking book = new Booking();
		book.setBookingId(1001);
		User u = new User();
		u.setUserId(101);
		book.setUserEntity(u);
		Destination d = new Destination();
		//d = null;
		
		d.setDestinationId("D1001");
		book.setDestinationEntity(d);
		Integer bookingId = 1001;
		
        Mockito.when(bookingRepository.findById(1001)).thenReturn(Optional.of(book));
        Booking book1=new Booking();
        book1.setBookingId(1001);
        String exp = "Deleted successully"+book.getBookingId().toString();
       

         String actual = bookingServiceImpl.deleteBooking(1001);
    Assertions.assertEquals(exp, actual);
	}
      
		
	@Test
	public void addBookingValidtest() throws WanderLustException
	{
		BookingDTO b= new BookingDTO();
		b.setCheckIn(LocalDate.of(2023, 11,12));
		b.setCheckOut(LocalDate.of(2023, 11, 23));
		b.setNoOfPeople(2);
		DestinationDTO d=new DestinationDTO();
		d.setDestinationId("D1001");
		UserDTO u=new UserDTO();
		u.setUserId(101);
		b.setDestination(d);
		b.setUsers(u);
		Booking b1=new Booking();
		b1.setCheckIn(LocalDate.of(2023, 11, 12));
		b1.setCheckOut(LocalDate.of(2023, 11, 23));
		b1.setNoOfPeople(2);
		Destination d1=new Destination();
		d1.setDestinationId("D1001");
		User u1=new User();
		u1.setUserId(101);
		Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(u1));
		Mockito.when(packageRepository.findById(Mockito.any())).thenReturn(Optional.of(d1));
		b1.setDestinationEntity(d1);
		b1.setUserEntity(u1);
		Mockito.when(bookingRepository.save(b1)).thenReturn(b1);
		Integer actual=bookingServiceImpl.addBooking(b,u1.getUserId(), d1.getDestinationId());
		Assertions.assertEquals(b1.getBookingId(), actual);
	}
	@Test	
	public void addBookingInvalidTest1() throws WanderLustException
	{
		BookingDTO b=new BookingDTO();
		b.setCheckIn(LocalDate.of(2023, 11,12));
		b.setCheckOut(LocalDate.of(2023, 11, 23));
		b.setNoOfPeople(4);
		DestinationDTO d=new DestinationDTO();
		d.setDestinationId("D1111");
		UserDTO u=new UserDTO();
		u.setUserId(105);
		b.setDestination(d);
		b.setUsers(u);
		Booking b1=new Booking();
		b1.setCheckIn(LocalDate.of(2023, 11, 12));
		b1.setCheckOut(LocalDate.of(2023, 11, 23));
		b1.setNoOfPeople(4);
		Destination d1=new Destination();
		d1.setDestinationId("D1002");
		User u1=new User();
		u1.setUserId(105);
		b1.setDestinationEntity(d1);
		b1.setUserEntity(u1);
		Mockito.when(userRepository.save(u1)).thenReturn(u1);
		Mockito.when(packageRepository.save(d1)).thenReturn(d1);
		Mockito.when(bookingRepository.save(b1)).thenReturn(b1);
		WanderLustException e=Assertions.assertThrows(WanderLustException.class, ()->bookingServiceImpl.addBooking(b, u.getUserId(), d.getDestinationId()));
		Assertions.assertEquals("BookingService.BOOKING_ERROR", e.getMessage());
	}
	
	@Test
	public void getBookingInvalid() throws WanderLustException
	{
		BookingDTO b=new BookingDTO();
		b.setBookingId(1001);
		b.setCheckIn(LocalDate.of(2023, 11,12));
		b.setCheckOut(LocalDate.of(2023, 11, 23));
		b.setNoOfPeople(2);
		DestinationDTO d=new DestinationDTO();
		d.setDestinationId("D1001");
		UserDTO u=new UserDTO();
		u.setUserId(111);
		b.setDestination(d);
		b.setUsers(u);
		Booking b1=new Booking();
		b1.setCheckIn(LocalDate.of(2023, 11, 12));
		b1.setCheckOut(LocalDate.of(2023, 11, 23));
		b1.setNoOfPeople(2);
		b1.setBookingId(1001);
		Destination d1=new Destination();
		d1.setDestinationId("D1001");
		User u1=new User();
		u1.setUserId(111);
		b1.setDestinationEntity(d1);
		b1.setUserEntity(u1);
		List<Booking> bt=bookingRepository.findByUserEntity(u1);
		Mockito.when(bookingRepository.findByUserEntity(u1)).thenReturn(bt);
		WanderLustException e=Assertions.assertThrows(WanderLustException.class, ()->bookingServiceImpl.getBooking(u1.getUserId()));
		Assertions.assertEquals("BookingService.NO_BOOKING", e.getMessage());
		
		
	}
	
	@Test
	public void getBookingInvalid2() throws WanderLustException
	{
		Booking b1=new Booking();
		Destination d1=new Destination();
		d1.setDestinationId("D1001");
		User u=new User();
		u.setUserId(101);
		b1.setDestinationEntity(d1);
		Booking b2=new Booking();
		Destination d2=new Destination();
		d2.setDestinationId("D1001");
		List<Booking> booking=new ArrayList<>();
		Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(u));
		b1.setUserEntity(u);
		b2.setUserEntity(u);
		Mockito.when(bookingRepository.save(b1)).thenReturn(b1);
		Mockito.when(bookingRepository.save(b2)).thenReturn(b2);
		WanderLustException e=Assertions.assertThrows(WanderLustException.class, ()->bookingServiceImpl.getBooking(u.getUserId()));
		Assertions.assertEquals("BookingService.BOOKING_ERROR", e.getMessage());
		
		
	}
	
	@Test
	public void deleteBooking() throws WanderLustException
	{
		Booking b1=new Booking();
		b1.setCheckIn(LocalDate.of(2023, 11,12));
		b1.setCheckOut(LocalDate.of(2023, 11, 23));
		b1.setNoOfPeople(2);
		b1.setBookingId(1001);
		Mockito.when(bookingRepository.save(b1)).thenReturn(b1);
		Mockito.when(bookingRepository.findById(b1.getBookingId())).thenReturn(Optional.of(b1)).thenReturn(null);
		
		
	}
	
	@Test
	public void getBookingvalid() throws WanderLustException
	{
		Booking b1=new Booking();
		b1.setCheckIn(LocalDate.of(2023, 11,12));
		b1.setCheckOut(LocalDate.of(2023, 11, 23));
		b1.setNoOfPeople(2);
		b1.setBookingId(1001);
		
		Destination d1=new Destination();
		d1.setDestinationId("D1001");
		Details details=new Details();
		details.setDetailsId("DL101");
		Itinerary itinerary=new Itinerary();
		itinerary.setItineraryId("I1001");
		details.setItinerary(itinerary);
		d1.setDetails(details);
		User u=new User();
		u.setUserId(101);
		b1.setDestinationEntity(d1);
		b1.setUserEntity(u);
		List<Booking> booking=new ArrayList<>();
		booking.add(b1);
		Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(u));
		Mockito.when(userRepository.save(u)).thenReturn(u);
		Mockito.when(bookingRepository.save(b1)).thenReturn(b1);
		Mockito.when(bookingRepository.findByUserEntity(u)).thenReturn(booking);
		
		List<BookingDTO> bookingDTO=bookingServiceImpl.getBooking(101);
		Assertions.assertEquals(bookingDTO.get(0).getBookingId(), booking.get(0).getBookingId());
		
		
	}
}

}