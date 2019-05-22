package com.experis.highfly.services;

import java.util.List;

import com.experis.highfly.dto.BookingDto;
import com.experis.highfly.entities.Booking;
import com.experis.highfly.utils.BookingFilter;
import com.experis.highfly.viewbeans.BookingViewBean;

public interface BookingService {

	BookingViewBean createNewBooking(BookingViewBean bookingViewBean) throws Exception;
	
	List<BookingViewBean> findAll() throws Exception;

	List<BookingViewBean> findAllByUserId(BookingFilter bookingFilter) throws Exception;

}