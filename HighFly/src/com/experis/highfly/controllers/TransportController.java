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

import com.experis.highfly.entities.Transport;
import com.experis.highfly.messages.ResponseMessage;
import com.experis.highfly.messages.ResponseStatus;
import com.experis.highfly.services.TransportService;
import com.experis.highfly.viewbeans.TransportViewBean;

@RestController
@RequestMapping("/transport")
public class TransportController {

	@Autowired
	private TransportService transportService;

	// --------------------- Create transport ------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> createTransport(@RequestBody TransportViewBean transportVB) {

		System.out.println("Creating Transport " + transportVB.getVehicle());

		Transport currentTransport = null;

		try {
			ResponseMessage rm = new ResponseMessage();

			currentTransport = transportService.saveTransport(transportVB);

			if (currentTransport == null) {
				rm.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<ResponseMessage>(rm, HttpStatus.NO_CONTENT);
			}

			rm.setResponseStatus(ResponseStatus.OK);
			rm.setData(currentTransport);
			rm.setMessage("Transport successfully created.");
			return new ResponseEntity<ResponseMessage>(HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseMessage>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// ---------------------------Delete transport-------------------------
	@RequestMapping(value = "/delete/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> deleteTransport(@RequestBody int transportId) {

		System.out.println("Deleting Transport " + transportId);

		Transport currentTransport = null;

		try {
			ResponseMessage rm = new ResponseMessage();

			currentTransport = transportService.deleteTransport(transportId);

			if (currentTransport == null) {
				rm.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<ResponseMessage>(rm, HttpStatus.NO_CONTENT);
			}

			rm.setResponseStatus(ResponseStatus.OK);
			rm.setData(currentTransport);
			rm.setMessage("Transport successfully deleted.");
			return new ResponseEntity<ResponseMessage>(HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseMessage>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// --------------------------------- List by Type-----------------------
	@RequestMapping(value = "/listAll/{type}", method = RequestMethod.GET)
	public ResponseEntity<List<TransportViewBean>> listByTransportType(@PathVariable("type") String type) {
		List<TransportViewBean> transports;

		System.out.println("List of all \"" + type + "\" transports");
		try {
			transports = transportService.findByTransportType(type);
			if (transports.isEmpty()) {
				return new ResponseEntity<List<TransportViewBean>>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<List<TransportViewBean>>(transports, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<TransportViewBean>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// -----------------------List of available transport-------------------
	@RequestMapping(value = "/availableTransport", method = RequestMethod.POST)
	public ResponseEntity<List<TransportViewBean>> listAvailableTransport(
			@RequestBody TransportViewBean transportViewBean) {
		List<TransportViewBean> transports;

		try {
			transports = transportService.findAvailableTransport();
			if (transports.isEmpty()) {
				return new ResponseEntity<List<TransportViewBean>>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<List<TransportViewBean>>(transports, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<TransportViewBean>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
