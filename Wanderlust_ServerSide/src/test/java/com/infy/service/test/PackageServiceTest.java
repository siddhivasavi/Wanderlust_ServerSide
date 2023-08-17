package com.infy.service.test;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.infy.dto.DestinationDTO;
import com.infy.entity.Destination;
import com.infy.entity.Details;
import com.infy.entity.Itinerary;
import com.infy.exception.WanderLustException;
import com.infy.repository.PackageRepository;
import com.infy.service.PackageServiceImpl;

@SpringBootTest
public class PackageServiceTest {
@Mock
PackageRepository packageServiceRepository;

@InjectMocks
PackageServiceImpl p=new PackageServiceImpl();
@Test
public void InvalidPackageSearchTest()throws Exception {
	List<Destination> M=new ArrayList<Destination>();
	Mockito.when(packageServiceRepository.findByContinent("jaya")).thenReturn(M);
	Exception e=Assertions.assertThrows(WanderLustException.class,()->p.getPackagesSearch("jaya"));
	Assertions.assertEquals("PackageService.PACKAGE_UNAVAILABLE",e.getMessage());
}
@Test
public void InvalidPackageDetailSearchTest() throws Exception{
	Destination D=new Destination();
	D.setDestinationId("D1001");
	D.setContinent("Asia");
	Itinerary i=new Itinerary();
	i.setItineraryId("I1001");
	Details d=new Details();
			d.setDetailsId("DL101");
			d.setItinerary(i);
			D.setDetails(d);
			List<Destination> dest1=new ArrayList<>();
			dest1.add(D);
			Mockito.when(packageServiceRepository.findByContinent("Asia")).thenReturn(dest1);
		
			List<DestinationDTO> list=p.getPackagesSearch("Asia");
			Assertions.assertNotNull(list);
			
}
@Test
void InvalidGetItineraryTest() throws Exception {
	Destination D=new Destination();
	Mockito.when(packageServiceRepository.findById(D.getDestinationId())).thenReturn(null);
	WanderLustException exception=Assertions.assertThrows(WanderLustException.class,()->p.getItinerary("5123"));
	Assertions.assertEquals("PackageService.ITINERARY_UNAVAILABLE", exception.getMessage());
}
void validGetItineraryTest() throws Exception {
	Destination D=new Destination();
	D.setDestinationId("D1001");
	D.setContinent("Asia");
	Itinerary i=new Itinerary();
	i.setItineraryId("I1001");
	Details d=new Details();
			d.setDetailsId("DL101");
			d.setItinerary(i);
			D.setDetails(d);
			Mockito.<Optional<Destination>>when(packageServiceRepository.findById("D1001")).thenReturn(Optional.of(D));
			DestinationDTO dto=p.getItinerary("D1001");
			Assertions.assertNotNull(dto);
			
}
@Test
void validGetPackages() throws Exception{
	Destination D=new Destination();
	D.setDestinationId("D1001");
	D.setContinent("Asia");
	Itinerary i=new Itinerary();
	i.setItineraryId("I1001");
	Details d=new Details();
			d.setDetailsId("DL101");
			d.setItinerary(i);
			D.setDetails(d);
			List<Destination> destlist=new ArrayList<Destination>();
			destlist.add(D);
			Mockito.<Iterable<Destination>>when(packageServiceRepository.findAll()).thenReturn(destlist);
			List<DestinationDTO> list=p.getPackages();
			Assertions.assertNotNull(list);
}
@Test
void InvalidGetPackages() throws Exception{
	DestinationDTO D=new DestinationDTO();
	D.setDestinationId("666666");
	Mockito.when(packageServiceRepository.findAll()).thenReturn(List.of());
	WanderLustException exception=Assertions.assertThrows(WanderLustException.class,()->p.getPackages());
	Assertions.assertEquals("PackageService.PACKAGE_UNAVAILABLE", exception.getMessage());
}

}


}
