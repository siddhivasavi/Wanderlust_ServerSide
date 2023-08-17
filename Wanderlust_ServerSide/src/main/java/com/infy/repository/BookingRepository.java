package com.infy.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.infy.entity.Booking;
import com.infy.entity.User;

public interface BookingRepository extends CrudRepository<Booking,Integer> {

   List<Booking> findByUserEntity(User user);
}
