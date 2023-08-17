package com.infy.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.infy.entity.Destination;

public interface PackageRepository extends CrudRepository<Destination,String>  {
	
List<Destination> findByContinent(String content);
}