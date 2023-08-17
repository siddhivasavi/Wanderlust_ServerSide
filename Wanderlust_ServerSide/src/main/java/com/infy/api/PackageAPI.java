package com.infy.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.dto.DestinationDTO;
import com.infy.exception.WanderLustException;
import com.infy.service.PackageService;
@CrossOrigin
@RestController
@RequestMapping(value="packageAPI")

public class PackageAPI {
	
	@Autowired
	private PackageService packageService; 
	
  

	@GetMapping(value="/packages/{continent}")
	public ResponseEntity<List<DestinationDTO>> getPackagesSearch(@PathVariable String continent) throws WanderLustException{
		List<DestinationDTO> des=packageService.getPackagesSearch(continent);
		return new ResponseEntity<List<DestinationDTO>>(des,HttpStatus.OK);
		
		
	}
	@GetMapping(value="/itinerary/{destinationId}")
	public ResponseEntity<DestinationDTO> getItinerary(@PathVariable String destinationId) throws WanderLustException{	
		DestinationDTO des1=packageService.getItinerary(destinationId);
		
		return new ResponseEntity<DestinationDTO>(des1,HttpStatus.OK);
		
	
		//	return null;		
	}
	
	@GetMapping(value="/getPackages")
	public ResponseEntity<List<DestinationDTO>> getPackages() throws WanderLustException{
		List<DestinationDTO> des3=packageService.getPackages();
		return new ResponseEntity<List<DestinationDTO>>(des3,HttpStatus.OK);
		
		
		//	return null;
	}
}
