package com.infy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.dto.BookingDTO;
import com.infy.dto.DestinationDTO;
import com.infy.dto.DetailsDTO;
import com.infy.dto.ItineraryDTO;
import com.infy.dto.UserDTO;
import com.infy.entity.Booking;
import com.infy.entity.Destination;
import com.infy.entity.User;
import com.infy.exception.WanderLustException;
import com.infy.repository.BookingRepository;
import com.infy.repository.PackageRepository;
import com.infy.repository.UserRepository;

@Service(value="bookingService")
@Transactional
public class BookingServiceImpl implements BookingService{
	@Autowired
	private BookingRepository bookingRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PackageRepository packageRepo;
	

	
	@Override
	public Integer addBooking(BookingDTO booking, Integer userId, String destinationId) throws WanderLustException{
		Optional<User> optional=userRepo.findById(userId);
		User user=optional.orElseThrow(()->new WanderLustException("BookingService.BOOKING_ERROR"));
		
		Optional<Destination> opt=packageRepo.findById(destinationId);
		Destination destination=opt.orElseThrow(()->new WanderLustException("BookingService.NO_BOOKING"));
		Booking book=new Booking();
		
		book.setNoOfPeople(booking.getNoOfPeople());
		book.setCheckIn(booking.getCheckIn());
		book.setCheckOut(booking.getCheckOut());
	
		book.setTimeOfBooking(booking.getTimeOfBooking());
		book.setTotalCost(booking.getTotalCost());
		book.setDestinationEntity(destination);
		book.setUserEntity(user);
		bookingRepo.save(book);
		return book.getBookingId();
		
	
		//return null;
		
	}
	

	@Override
	public List<BookingDTO> getBooking(Integer userId) throws WanderLustException {
		Optional<User> optional=userRepo.findById(userId);
		User user=optional.orElseThrow(()->new WanderLustException("BookingService.NO_BOOKING"));
		List<Booking> bookings=bookingRepo.findByUserEntity(user);
		if(bookings.isEmpty()) {
			throw new WanderLustException("BookingService.BOOKING_ERROR");
		}
		List<BookingDTO> bookingDTO=new ArrayList<>();
		bookings.forEach(b->{
			BookingDTO book=new BookingDTO();
			book.setBookingId(b.getBookingId());
			book.setCheckIn(b.getCheckIn());
			book.setCheckOut(b.getCheckOut());
			book.setNoOfPeople(b.getNoOfPeople());
			book.setTimeOfBooking(b.getTimeOfBooking());
			book.setTotalCost(b.getTotalCost());
			
			UserDTO userDTO=new UserDTO();
			userDTO.setContactNumber(b.getUserEntity().getContactNumber());
			userDTO.setEmailId(b.getUserEntity().getEmailId());
			userDTO.setPassword(b.getUserEntity().getPassword());
			userDTO.setUserId(b.getUserEntity().getUserId());
			userDTO.setUserName(b.getUserEntity().getUserName());
			
			DestinationDTO dest=new DestinationDTO();
			dest.setAvailability(b.getDestinationEntity().getAvailability());
			dest.setChargePerPerson(b.getDestinationEntity().getChargePerPerson());
			dest.setContinent(b.getDestinationEntity().getContinent());
			dest.setDestinationId(b.getDestinationEntity().getDestinationId());
			dest.setDestinationName(b.getDestinationEntity().getDestinationName());
			dest.setImageUrl(b.getDestinationEntity().getImageUrl());
			dest.setNoOfNights(b.getDestinationEntity().getNoOfNights());
			dest.setFlightCharge(b.getDestinationEntity().getFlightCharge());
			dest.setDiscount(b.getDestinationEntity().getDiscount());
			
			DetailsDTO detail=new DetailsDTO();
			detail.setAbout(b.getDestinationEntity().getDetails().getDetailsId());
			detail.setDetailsId(b.getDestinationEntity().getDetails().getDetailsId());
			detail.setHighlights(b.getDestinationEntity().getDetails().getHighlights());
			detail.setPace(b.getDestinationEntity().getDetails().getPace());
			detail.setPackageInclusion(b.getDestinationEntity().getDetails().getPackageInclusion());
			
			ItineraryDTO itinerary=new ItineraryDTO();
			itinerary.setFirstDay(b.getDestinationEntity().getDetails().getItinerary().getFirstDay());
			itinerary.setItineraryId(b.getDestinationEntity().getDetails().getItinerary().getItineraryId());
			itinerary.setLastDay(b.getDestinationEntity().getDetails().getItinerary().getLastDay());
			itinerary.setRestOfDays(b.getDestinationEntity().getDetails().getItinerary().getRestOfDays());
			dest.setDetails(detail);
			detail.setItinerary(itinerary);
			book.setDestination(dest);
			
		bookingDTO.add(book);
		//	return a;
		});
	
	
		
			return bookingDTO;	
			//	return null;	
		
	}
	
	@Override
	 public String deleteBooking(Integer bookingId) throws WanderLustException {


	     Optional<Booking> opt=bookingRepo.findById(bookingId);
	     Booking booking=opt.orElseThrow(()->new WanderLustException("BookingService.NO_BOOKING"));
	     if(booking!=null) {
	    	 booking.setUserEntity(null);
	    	 booking.setDestinationEntity(null);
	    	 bookingRepo.delete(booking);
	     }
	        return "Deleted successully" + bookingId;
	    }	
		
		
	 //  return null;
	   }