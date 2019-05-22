package com.experis.highfly.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.experis.highfly.dto.TransportControllerDto;
import com.experis.highfly.entities.Transport;
import com.experis.highfly.services.TransportService;

@RestController
@RequestMapping("/transport")
public class TransportController {

	@Autowired
	private TransportService transportService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<Void> createTransport(@RequestBody Transport transport, UriComponentsBuilder ucBuilder) {

		System.out.println("Creating Transport " + transport.getType().getType());
		transportService.saveTransport(transport);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/transport/{id}").buildAndExpand(transport.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{type}", method = RequestMethod.GET)
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

	@RequestMapping(value = "/findAvailableTransport", method = RequestMethod.POST)
	public ResponseEntity<List<Transport>> listAvailableTransport(@RequestBody TransportControllerDto tcdto,
			UriComponentsBuilder ucBuilder) {
		List<Transport> transports;

		try {
			transports = transportService.findAvailableTransport(tcdto.getId(), tcdto.getDateFrom(), tcdto.getDateTo());
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
