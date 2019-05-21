package com.experis.highfly.services;

import java.util.List;

import com.experis.highfly.dto.BookingServiceDto;
import com.experis.highfly.entities.Booking;

public interface BookingService {

	Booking createNewBooking(BookingServiceDto bookingServiceDto) throws Exception;


}
