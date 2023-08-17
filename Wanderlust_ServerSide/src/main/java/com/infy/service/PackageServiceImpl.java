package com.infy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.infy.dto.DestinationDTO;
import com.infy.dto.DetailsDTO;
import com.infy.dto.ItineraryDTO;
import com.infy.entity.Destination;
import com.infy.exception.WanderLustException;
import com.infy.repository.PackageRepository;

@Service(value="PackageService")
@Transactional
public class PackageServiceImpl implements PackageService{
	
	
	private PackageRepository packageRepo;
	
	
	@Override
	public List<DestinationDTO> getPackagesSearch(String continent) throws WanderLustException{
		List<Destination> destination= packageRepo.findByContinent(continent);
		if(destination.isEmpty())
		throw new WanderLustException("PackageService.PACKAGE_UNAVAILABLE") ;
		List<DestinationDTO> destinationDTO= new ArrayList<>();
		for(Destination a:destination) {
			ItineraryDTO itinerary1= new ItineraryDTO();
		    itinerary1.setFirstDay(a.getDetails().getItinerary().getFirstDay());
		    itinerary1.setItineraryId(a.getDetails().getItinerary().getItineraryId());
		    itinerary1.setLastDay(a.getDetails().getItinerary().getLastDay());
		    itinerary1.setRestOfDays(a.getDetails().getItinerary().getLastDay());
		    
		    DetailsDTO detail1= new DetailsDTO();
		    detail1.setAbout(a.getDetails().getAbout());
		    detail1.setDetailsId(a.getDetails().getDetailsId());
		    detail1.setPackageInclusion(a.getDetails().getPackageInclusion());
		    detail1.setHighlights(a.getDetails().getHighlights());
		    detail1.setPace(a.getDetails().getPace());
		    detail1.setItinerary(itinerary1);
		    
		    DestinationDTO destinationDTO1 = new DestinationDTO();
		    destinationDTO1.setAvailability(a.getAvailability());
		    destinationDTO1.setChargePerPerson(a.getChargePerPerson());
		    destinationDTO1.setContinent(a.getContinent());
		    destinationDTO1.setDestinationId(a.getDestinationId());
		    destinationDTO1.setDestinationName(a.getDestinationName());
		    destinationDTO1.setDetails(detail1);
		    destinationDTO1.setDiscount(a.getDiscount());
		    destinationDTO1.setFlightCharge(a.getFlightCharge());
		    destinationDTO1.setImageUrl(a.getImageUrl());
		    destinationDTO1.setNoOfNights(a.getNoOfNights());
		    
		    destinationDTO.add(destinationDTO1);
		 
		}
		
		return destinationDTO;
		
		
		
		//return null;
		
	}
	
	@Override
	public DestinationDTO getItinerary(String destinationId) throws WanderLustException{
		
		Optional<Destination> optional= packageRepo.findById(destinationId);
		Destination dest = optional.orElseThrow(()-> new WanderLustException("PackageService.ITINERARY_UNAVAILABLE"));
		DestinationDTO destDTO= new DestinationDTO();
		
		ItineraryDTO itinerary1= new ItineraryDTO();
		itinerary1.setItineraryId(dest.getDetails().getItinerary().getFirstDay());
		itinerary1.setFirstDay(dest.getDetails().getItinerary().getFirstDay());
		itinerary1.setRestOfDays(dest.getDetails().getItinerary().getRestOfDays());
		itinerary1.setRestOfDays(dest.getDetails().getItinerary().getRestOfDays());
		
		DetailsDTO details= new DetailsDTO();
		details.setAbout(dest.getDetails().getAbout());
		details.setDetailsId(dest.getDetails().getDetailsId());
		details.setHighlights(dest.getDetails().getHighlights());
		details.setItinerary(itinerary1);
		details.setPace(dest.getDetails().getPace());
		details.setPackageInclusion(dest.getDetails().getPackageInclusion());
		
		destDTO.setAvailability(dest.getAvailability());
		destDTO.setChargePerPerson(dest.getChargePerPerson());
		destDTO.setContinent(dest.getContinent());
		destDTO.setDestinationId(dest.getDestinationId());
		destDTO.setDestinationName(dest.getDestinationName());
		destDTO.setDetails(details);
		destDTO.setDiscount(dest.getDiscount());
		destDTO.setFlightCharge(dest.getFlightCharge());
		destDTO.setImageUrl(dest.getImageUrl());
		destDTO.setNoOfNights(dest.getNoOfNights());
		
	//return destDTO;	
			return null;
	}
	
	@Override
	public List<DestinationDTO> getPackages() throws WanderLustException {
		Iterable<Destination> destination= packageRepo.findAll();
		List<DestinationDTO> dest1= new ArrayList<>();
		
		destination.forEach(a->{
			ItineraryDTO itinerary= new ItineraryDTO();
			itinerary.setFirstDay(a.getDetails().getItinerary().getFirstDay());
			itinerary.setItineraryId(a.getDetails().getItinerary().getItineraryId());
			itinerary.setLastDay(a.getDetails().getItinerary().getLastDay());
			itinerary.setRestOfDays(a.getDetails().getItinerary().getRestOfDays());
			
			DetailsDTO details= new DetailsDTO();
			details.setAbout(a.getDetails().getAbout());
			details.setDetailsId(a.getDetails().getDetailsId());
			details.setHighlights(a.getDetails().getHighlights());
			details.setItinerary(itinerary);
			details.setPace(a.getDetails().getPace());
			details.setPackageInclusion(a.getDetails().getPackageInclusion());
			
			DestinationDTO dest =new DestinationDTO();
			dest.setAvailability(a.getAvailability());
			dest.setChargePerPerson(a.getChargePerPerson());
			dest.setContinent(a.getContinent());
			dest.setDestinationId(a.getDestinationId());
			dest.setDestinationName(a.getDestinationName());
			dest.setDetails(details);
			dest.setDiscount(a.getDiscount());
			dest.setFlightCharge(a.getFlightCharge());
			dest.setImageUrl(a.getImageUrl());
			dest.setNoOfNights(a.getNoOfNights());
			
		    dest1.add(dest);
		    });
		if(dest1.isEmpty()) throw new WanderLustException("PackageService.PACKAGE_UNAVAILABLE");
		
		 
		return dest1;
	}
		
		//return null;
	}
	
