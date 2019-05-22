package com.experis.highfly.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.experis.highfly.entities.Transport;
import com.experis.highfly.services.TransportService;

@RestController
@RequestMapping("/transport")
public class TransportController {

	@Autowired
	private TransportService transportService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<Void> createTransport(@RequestBody TransportViewBean transportVB) {

		System.out.println("Creating Transport " + transport.getType().getType());
		transportService.saveTransport(transport);

		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/delete/", method = RequestMethod.POST)
	public ResponseEntity<Void> deleteTransport(@RequestBody int transportId) {

		System.out.println("Deleting Transport " + transportId);
		transportService.deleteTransport(transportId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	// --------------------------------- List by Type
	@RequestMapping(value = "/listAll/{type}/", method = RequestMethod.GET)
	public ResponseEntity<List<Transport>> listByTransportType(@PathVariable("type") int type) {
		List<Transport> transports;

		System.out.println("List of all \"" + type + "\" transports");
		try {
			transports = transportService.findByTransportType(type);
			if (transports.isEmpty()) {
				return new ResponseEntity<List<Transport>>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<List<Transport>>(transports, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Transport>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/findAvailableTransport/{date-from}/{date-to}", method = RequestMethod.GET)
	public ResponseEntity<List<Transport>> listAvailableTransport(@PathVariable("date-from") Date dateFrom,
			@PathVariable("date-to") Date dateTo) {
		List<Transport> transports;

		try {
			transports = transportService.findAvailableTransport(dateFrom, dateTo);
			if (transports.isEmpty()) {
				return new ResponseEntity<List<Transport>>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<List<Transport>>(transports, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Transport>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
