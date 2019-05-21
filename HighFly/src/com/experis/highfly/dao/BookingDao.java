package com.experis.highfly.dao;

import java.util.List;

import com.experis.highfly.entities.Booking;
import com.experis.highfly.utils.BookingFilter;

public interface BookingDao extends GenericDao<Booking> {

	public List<Booking> findBookingByFilters(BookingFilter bookingFilter);

}
