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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.experis.highfly.dao.BookingDao;
import com.experis.highfly.dao.TransportDao;
import com.experis.highfly.dao.UserDao;
import com.experis.highfly.dto.BookingServiceDto;
import com.experis.highfly.entities.Booking;
import com.experis.highfly.entities.Transport;
import com.experis.highfly.entities.User;
import com.experis.highfly.services.BookingService;

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
//	@RequiredTx
	@Transactional(propagation = Propagation.REQUIRED)

	public Booking createNewBooking(BookingServiceDto bookingServiceDto) throws Exception {

		Booking booking = new Booking();

		// Recupero dell'user

		User user = (User) userDao.findByUsernameAndPassword(bookingServiceDto.getUsername(),
				bookingServiceDto.getPassword());
		booking.setClient(user);

		// Recupero del trasporto
		Transport transport = transportDao.find(bookingServiceDto.getTransportId());
		booking.setTransport(transport);

		booking.setDateFrom(bookingServiceDto.getDateFrom());
		booking.setDateTo(bookingServiceDto.getDateTo());
		booking.setPriceTot(transport.getPrice());
		// Recupero prezzo

		// Salvataggio dell'ordine.
		bookingDao.insert(booking);

		// Aggiornamento del numero di ordini effettuati dal cliente.
		// customer.setNumOrders(1 + customer.getNumOrders());

		transport.setMaxSeats(transport.getMaxSeats() - 1);
		// ATTENZIONE!!! Settaggio di un campo not nullable in modo da
		// scatenare una eccezione.
		// customer.setRagSoc(null);

		// customerDao = null;

		transportDao.update(transport);

		return booking;
	}

}
