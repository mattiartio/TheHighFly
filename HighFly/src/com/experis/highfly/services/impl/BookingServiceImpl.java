/**
 * UserServiceImpl.java
 *
 * robgion
 * www.2clever.it
 * 
 * 05 lug 2017
 * For further information please write to info@2clever.it
 */
package com.experis.highfly.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.experis.highfly.dao.BookingDao;
import com.experis.highfly.dao.TransportDao;
import com.experis.highfly.dao.UserDao;
import com.experis.highfly.entities.Booking;
import com.experis.highfly.entities.Transport;
import com.experis.highfly.entities.User;
import com.experis.highfly.services.BookingService;
import com.experis.highfly.utils.BookingFilter;
import com.experis.highfly.viewbeans.BookingViewBean;
import com.experis.highfly.viewbeans.TransportViewBean;

@Service(value = "orderService")
//@Transactional(propagation=Propagation.MANDATORY)
//@Transactional(propagation=Propagation.REQUIRES_NEW)
//@Transactional(propagation=Propagation.NEVER)
@Transactional(propagation = Propagation.REQUIRED)
//@RequiredTx

public class BookingServiceImpl implements BookingService {

	@Autowired
	@Qualifier("userDao")
	UserDao userDao;

	@Autowired
	@Qualifier("transportDao")
	TransportDao transportDao;

	@Autowired
	@Qualifier("bookingDao")
	BookingDao bookingDao;

	@Override
	// @RequiredTx

	public BookingViewBean createNewBooking(BookingViewBean bookingViewBean) throws Exception {

		Booking booking = new Booking();

		// Recupero dell'user

		User user = (User) userDao.findUserByUsername(bookingViewBean.getUsername());
		booking.setClient(user);

		// Recupero del trasporto
		Transport transport = transportDao.find(bookingViewBean.getTransportViewBean().getIdTransport());
		booking.setTransport(transport);

		booking.setDateFrom(bookingViewBean.getDataFrom());
		booking.setDateTo(bookingViewBean.getDataTo());
		booking.setPriceTot(transport.getPrice());

		bookingDao.insert(booking);

		transport.setMaxSeats(transport.getMaxSeats() - 1);

		transportDao.update(transport);

		return bookingViewBean;
	}

	@Override
	public List<BookingViewBean> findAll() throws Exception {
		List<Booking> bookings = bookingDao.findAll();

		List<BookingViewBean> bookingViewBeans = new ArrayList<BookingViewBean>();

		for (Booking b : bookings) {
			bookingViewBeans.add(fillBookingViewBean(b));
		}

		return bookingViewBeans;
	}

	@Override
	public List<BookingViewBean> findAllByUserId(BookingFilter bookingFilter) throws Exception {

		List<Booking> bookings = bookingDao.findBookingByFilters(bookingFilter);
		List<BookingViewBean> bookingViewBeans = new ArrayList<BookingViewBean>();

		for (Booking b : bookings) {
			bookingViewBeans.add(fillBookingViewBean(b));
		}

		return bookingViewBeans;
	}

	private BookingViewBean fillBookingViewBean(Booking b) {

		BookingViewBean bvb = new BookingViewBean();
		TransportViewBean tvb = new TransportViewBean();

		tvb.setIdTransport(b.getTransport().getId());
		tvb.setMaxSeats(b.getTransport().getMaxSeats());
		tvb.setPrice(b.getTransport().getPrice());
		tvb.setVehicle(b.getTransport().getType().getType());

		bvb.setName(b.getClient().getName());
		bvb.setSurname(b.getClient().getSurname());
		bvb.setPrice(b.getTransport().getPrice());
		bvb.setDataFrom(b.getDateFrom());
		bvb.setDataTo(b.getDateTo());
		bvb.setTransportViewBean(tvb);

		return bvb;
	}

}
