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

import com.experis.highfly.entities.Booking;
import com.experis.highfly.messages.ResponseMessage;
import com.experis.highfly.services.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	// -------------------Retrieve All
	// bookings--------------------------------------------------------
	@RequestMapping(value = "/listall", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessage> listAllbookings() {
		List<Booking> bookings;
		try {
			// esemipo di messaggio di risposta personalizzato
			ResponseMessage rm = new ResponseMessage();

			bookings = bookingService.findAllBookings();
			if (bookings.isEmpty()) {
				rm.setCode("KO");
				rm.setErrorMessage("List not available");
				return new ResponseEntity<ResponseMessage>(rm, HttpStatus.NO_CONTENT);
			}
			rm.setCode("OK");
			rm.setData(bookings);
			return new ResponseEntity<ResponseMessage>(rm, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseMessage>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// -------------------Retrieve All bookings By
	// Customer--------------------------------------------------------

	@RequestMapping(value = "/{id_user}/myList", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessage> getbookingsByUser(@PathVariable("id_user") int id_user) {
		System.out.println("Bookinsg of " + id_user);
		List<Booking> userBookings;
		try {
			// esemipo di messaggio di risposta personalizzato
			ResponseMessage rm = new ResponseMessage();

			userBookings = bookingService.findAllByUser(id_user);
			if (userBookings.isEmpty()) {
				rm.setCode("KO");
				rm.setErrorMessage("List not available");
				return new ResponseEntity<ResponseMessage>(rm, HttpStatus.NO_CONTENT);
			}
			rm.setCode("OK");
			rm.setData(userBookings);
			return new ResponseEntity<ResponseMessage>(rm, HttpStatus.OK);

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
		Booking booking = null;
		try {
			// esemipo di messaggio di risposta personalizzato
			ResponseMessage rm = new ResponseMessage();

			booking = bookingService.find(id_booking);
			if (booking == null) {
				rm.setCode("KO");
				rm.setErrorMessage("Booking not available");
				return new ResponseEntity<ResponseMessage>(rm, HttpStatus.NO_CONTENT);
			}

			rm.setCode("OK");
			rm.setData(booking);

			return new ResponseEntity<ResponseMessage>(rm, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseMessage>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// -------------------Create a
	// booking--------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> createbooking(@RequestBody BookingServiceDto_1 bookingDto,
			UriComponentsBuilder ucBuilder) {
		System.out.println("Creating booking " + bookingDto.getId());

//        if (bookingService.isbookingExist(bookingServiceDto.equals())) {
//            System.out.println("A booking with name " + booking.getName() + " already exist");
//            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
//        }   
		Booking currentBooking = null;

		try {
			// esemipo di messaggio di risposta personalizzato
			ResponseMessage rm = new ResponseMessage();

			currentBooking = bookingService.createNewBooking(bookingDto);

			if (currentBooking == null) {
				rm.setCode("KO");
				rm.setErrorMessage("Booking not created");
				return new ResponseEntity<ResponseMessage>(rm, HttpStatus.NO_CONTENT);
			}

			rm.setCode("OK");
			rm.setData(currentBooking);
			rm.setMessage("Booking created succesfully");
			return new ResponseEntity<ResponseMessage>(rm, HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseMessage>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(ucBuilder.path("/booking/{id}").buildAndExpand(bookingServiceDto.getUser().getId()).toUri());
//        ResponseMessage rm=new ResponseMessage();
//        rm.setData(headers);

	}

	// ------------------- Update a booking
	// --------------------------------------------------------

	@RequestMapping(value = "/update/{id_booking}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseMessage> updateBooking(@PathVariable("id_booking") int id_booking,
			BookingServiceDto_1 bookingDto) {
		System.out.println("Updating booking " + id_booking);

		Booking currentBooking;
		try {
			ResponseMessage rm = new ResponseMessage();

			currentBooking = bookingService.find(id_booking);

			if (currentBooking == null) {
				System.out.println("booking with id " + id_booking + " not found");

				rm.setCode("KO");
				rm.setMessage("booking with id " + id_booking + " not found");
				return new ResponseEntity<ResponseMessage>(rm, HttpStatus.NOT_FOUND);
			}

			bookingService.updateBooking(bookingDto);

			rm.setCode("OK");
			rm.setMessage("booking with id " + id_booking + " updated");
			rm.setData(currentBooking);

			return new ResponseEntity<ResponseMessage>(rm, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseMessage>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}