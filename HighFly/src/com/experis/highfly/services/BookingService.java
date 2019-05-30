package com.experis.highfly.services;

import java.util.List;

import com.experis.highfly.exception.BookingException;
import com.experis.highfly.exception.DateException;
import com.experis.highfly.utils.BookingFilter;
import com.experis.highfly.viewbeans.BookingViewBean;

public interface BookingService {

	BookingViewBean createNewBooking(BookingViewBean bookingViewBean) throws Exception, BookingException, DateException;

	List<BookingViewBean> findAll() throws Exception;

	List<BookingViewBean> findAllByFilter(BookingFilter bookingFilter) throws Exception;

	BookingViewBean findByBookingId(int bookingId) throws BookingException;

	void deleteBooking(int bookingId) throws BookingException;

	BookingViewBean updateBooking(int bookingId, BookingViewBean bookingViewBean) throws BookingException;

}
