package com.experis.highfly.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.experis.highfly.exception.BookingException;
import com.experis.highfly.messages.ResponseMessage;
import com.experis.highfly.messages.ResponseStatus;
import com.experis.highfly.services.BookingService;
import com.experis.highfly.utils.BookingFilter;
import com.experis.highfly.viewbeans.BookingViewBean;

@RestController
@RequestMapping("/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	// -------------------Retrieve All
	// bookings--------------------------------------------------------
	@RequestMapping(value = "/listall", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessage> listAllbookings() {
		List<BookingViewBean> bookings;
		try {
			// esemipo di messaggio di risposta personalizzato
			ResponseMessage rm = new ResponseMessage();

			bookings = bookingService.findAll();
			if (bookings.isEmpty()) {
				rm.setResponseStatus(ResponseStatus.LIST_NOT_FOUND);
				rm.setMessage(ResponseStatus.LIST_NOT_FOUND.getDescription());
				return new ResponseEntity<ResponseMessage>(rm, HttpStatus.NO_CONTENT);
			}
			rm.setResponseStatus(ResponseStatus.OK);
			rm.setMessage(ResponseStatus.OK.getDescription());
			rm.setData(bookings);
			return new ResponseEntity<ResponseMessage>(rm, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseMessage>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// -------------------Retrieve All bookings By
	// Customer--------------------------------------------------------

	@RequestMapping(value = "/myList", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> getBookingsByUser(@RequestBody BookingFilter bookingFilter) {
		System.out.println("Bookinsg of " + bookingFilter.getUserId());

		List<BookingViewBean> userBookings;
		ResponseMessage rm = new ResponseMessage();

		try {
			// controllo dei dati
			userBookings = bookingService.findAllByUserId(bookingFilter);
			rm.setResponseStatus(ResponseStatus.OK);
			rm.setMessage(ResponseStatus.OK.getDescription());
			rm.setData(userBookings);
			return new ResponseEntity<ResponseMessage>(rm, HttpStatus.OK);

		} catch (BookingException e) {
			rm.setResponseStatus(ResponseStatus.LIST_NOT_FOUND);
			rm.setMessage(ResponseStatus.LIST_NOT_FOUND.getDescription());
			return new ResponseEntity<ResponseMessage>(rm, HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseMessage>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// -------------------Retrieve Single
	// Booking--------------------------------------------------------

	@RequestMapping(value = "/details/{id_booking}", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessage> getBooking(@PathVariable("id_booking") int id_booking) {
		System.out.println("Fetching booking with id " + id_booking);
		ResponseMessage rm = new ResponseMessage();
		BookingViewBean bookingViewBean;

		try {
			// esemipo di messaggio di risposta personalizzato

			bookingViewBean = bookingService.findByBookingId(id_booking);
			rm.setResponseStatus(ResponseStatus.OK);
			rm.setMessage(ResponseStatus.OK.getDescription());
			rm.setData(bookingViewBean);
			return new ResponseEntity<ResponseMessage>(rm, HttpStatus.OK);

		} catch (BookingException e) {
			rm.setResponseStatus(ResponseStatus.BOOKING_NOT_FOUND);
			rm.setMessage(ResponseStatus.BOOKING_NOT_FOUND.getDescription());
			return new ResponseEntity<ResponseMessage>(rm, HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseMessage>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// -------------------Create a
	// booking--------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> createbooking(@RequestBody BookingViewBean bookingViewBean,
			UriComponentsBuilder ucBuilder) {
		System.out.println("Creating new booking ");

		BookingViewBean confirmedBookingViewBean = null;
		ResponseMessage rm = new ResponseMessage();

		try {
			confirmedBookingViewBean = bookingService.createNewBooking(bookingViewBean);

			rm.setResponseStatus(ResponseStatus.OK);
			rm.setMessage(ResponseStatus.OK.getDescription());
			rm.setData(confirmedBookingViewBean);
			return new ResponseEntity<ResponseMessage>(rm, HttpStatus.OK);

		} catch (BookingException e) {
			rm.setResponseStatus(ResponseStatus.BOOKING_NOT_FOUND);
			rm.setMessage(ResponseStatus.BOOKING_NOT_FOUND.getDescription());
			return new ResponseEntity<ResponseMessage>(rm, HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseMessage>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// ------------------- Update a booking
	// --------------------------------------------------------

	@RequestMapping(value = "/update/{bookingID}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseMessage> updateBooking(@PathVariable("bookingID") int bookingID,
			@RequestBody BookingViewBean bookingViewBean) {
		System.out.println("Updating booking " + bookingID);

		ResponseMessage rm = new ResponseMessage();
		BookingViewBean updatedBookingViewBean = null;

		try {
			updatedBookingViewBean = bookingService.updateBooking(bookingID, bookingViewBean);

			rm.setResponseStatus(ResponseStatus.OK);
			rm.setMessage(ResponseStatus.OK.getDescription());
			rm.setData(updatedBookingViewBean);
			return new ResponseEntity<ResponseMessage>(rm, HttpStatus.OK);

		} catch (BookingException e) {
			rm.setResponseStatus(ResponseStatus.BOOKING_NOT_FOUND);
			rm.setMessage(ResponseStatus.BOOKING_NOT_FOUND.getDescription());
			return new ResponseEntity<ResponseMessage>(rm, HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseMessage>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// -------------------Delete a
	// booking--------------------------------------------------------

	@RequestMapping(value = "/delete/{bookingID}", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessage> deleteBooking(@PathVariable int bookingID) {
		System.out.println("Deleting booking.. ");

		ResponseMessage rm = new ResponseMessage();

		try {
			bookingService.deleteBooking(bookingID);

			rm.setResponseStatus(ResponseStatus.OK);
			rm.setMessage(ResponseStatus.OK.getDescription());

			return new ResponseEntity<ResponseMessage>(rm, HttpStatus.OK);

		} catch (BookingException e) {
			rm.setResponseStatus(ResponseStatus.BOOKING_NOT_FOUND);
			rm.setMessage(ResponseStatus.BOOKING_NOT_FOUND.getDescription());
			return new ResponseEntity<ResponseMessage>(rm, HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseMessage>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// HttpHeaders headers = new HttpHeaders();
		// headers.setLocation(ucBuilder.path("/booking/{id}").buildAndExpand(bookingServiceDto.getUser().getId()).toUri());

	}
}