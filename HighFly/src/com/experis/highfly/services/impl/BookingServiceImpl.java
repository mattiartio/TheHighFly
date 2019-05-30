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
import com.experis.highfly.exception.BookingException;
import com.experis.highfly.exception.DateException;
import com.experis.highfly.exception.SaveException;
import com.experis.highfly.services.BookingService;
import com.experis.highfly.utils.BookingFilter;
import com.experis.highfly.viewbeans.BookingViewBean;
import com.experis.highfly.viewbeans.TransportViewBean;

@Service(value = "bookingService")
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

	public BookingViewBean createNewBooking(BookingViewBean bookingViewBean) throws Exception, BookingException, DateException {

		Booking booking = new Booking();
		
		if (bookingViewBean.getDataFrom().getTime() > bookingViewBean.getDataTo().getTime() || bookingViewBean.getDataTo().getTime() < bookingViewBean.getDataFrom().getTime())
			throw new DateException();

		// Recupero dell'user

		User user = (User) userDao.findUserByUsername(bookingViewBean.getUsername());
		booking.setClient(user);

		// Recupero del trasporto
		Transport transport = transportDao.find(bookingViewBean.getTransportViewBean().getIdTransport());
		booking.setId(bookingViewBean.getId());
		booking.setTransport(transport);
		booking.setName(bookingViewBean.getName());
		booking.setSurname(bookingViewBean.getSurname());
		booking.setDateFrom(bookingViewBean.getDataFrom());
		booking.setDateTo(bookingViewBean.getDataTo());
		booking.setPriceTot(bookingViewBean.getTransportViewBean().getNumPosti() * transport.getPrice());
		booking.setSeats(bookingViewBean.getSeats());

		bookingDao.insert(booking);

		return bookingViewBean;
	}

	@Override
	public List<BookingViewBean> findAll() throws Exception {
		List<Booking> bookings = bookingDao.findAll();
		
		if (bookings == null)
			throw new BookingException();

		List<BookingViewBean> bookingViewBeans = new ArrayList<BookingViewBean>();

		for (Booking b : bookings) {
			bookingViewBeans.add(fillBookingViewBean(b));
		}

		return bookingViewBeans;
	}

	@Override
	public List<BookingViewBean> findAllByFilter(BookingFilter bookingFilter) throws Exception {

		List<Booking> bookings = bookingDao.findBookingByFilters(bookingFilter);
		if (bookings == null)
			throw new BookingException();
		
		List<BookingViewBean> bookingViewBeans = new ArrayList<BookingViewBean>();
		for (Booking b : bookings) {
			bookingViewBeans.add(fillBookingViewBean(b));
		}

		return bookingViewBeans;
	}

	@Override
	public BookingViewBean findByBookingId(int bookingId) throws BookingException {

		Booking booking = bookingDao.find(bookingId);
		if (booking == null)
			throw new BookingException();

		BookingViewBean bookingViewBean = fillBookingViewBean(booking);

		return bookingViewBean;
	}

	@Override
	public BookingViewBean updateBooking(int bookingId, BookingViewBean bookingViewBean) throws BookingException {

		Booking booking = bookingDao.find(bookingId);
		Transport transport = transportDao.find(bookingViewBean.getTransportViewBean().getIdTransport());

		if (booking == null || transport == null)
			throw new BookingException();

		booking.setId(bookingViewBean.getId());
		booking.setName(bookingViewBean.getName());
		booking.setSurname(bookingViewBean.getSurname());
		booking.setPriceTot(bookingViewBean.getTransportViewBean().getPrice());
		booking.setSeats(bookingViewBean.getSeats());
		booking.setTransport(transport);
		booking.setDateTo(bookingViewBean.getDataTo());
		booking.setDateFrom(bookingViewBean.getDataFrom());

		bookingDao.update(booking);

		return bookingViewBean;
	}

	@Override
	public void deleteBooking(int bookingId) throws BookingException {
		Booking booking = bookingDao.find(bookingId);

		if (booking == null)
			throw new BookingException("Booking not found... delete failed");
		else
			bookingDao.delete(bookingId);
	}

	/*-----------------------------------------------------------*/

	private BookingViewBean fillBookingViewBean(Booking b) {

		BookingViewBean bvb = new BookingViewBean();
		TransportViewBean tvb = new TransportViewBean();

		tvb.setIdTransport(b.getTransport().getId());
		tvb.setMaxSeats(b.getTransport().getMaxSeats());
		tvb.setPrice(b.getTransport().getPrice());
		tvb.setVehicle(b.getTransport().getVehicle().getType());
		tvb.setName(b.getTransport().getName());

		bvb.setId(b.getId());
		bvb.setSeats(b.getSeats());
		bvb.setUsername(b.getClient().getUsername());
		bvb.setName(b.getName());
		bvb.setSurname(b.getSurname());
		bvb.setPrice(b.getPriceTot());
		bvb.setDataFrom(b.getDateFrom());
		bvb.setDataTo(b.getDateTo());
		bvb.setTransportViewBean(tvb);

		return bvb;
	}

}
