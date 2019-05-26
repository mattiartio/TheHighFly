package com.experis.highfly.controllers;

import java.util.Date;
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
import com.experis.highfly.entities.User;
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

		TransportViewBean createdTransport = null;
		
		ResponseMessage rm = new ResponseMessage();

		try {
			

			if (transportVB.getNumPosti() == 0 || transportVB.getPrice() == 0 || transportVB.getVehicle() == null || transportVB.getVehicle().isEmpty()) {
				rm.setResponseStatus(ResponseStatus.JSON_ERROR);
				rm.setMessage(ResponseStatus.JSON_ERROR.getDescription());
				return new ResponseEntity<ResponseMessage>(rm, HttpStatus.NO_CONTENT);
			}
			
			createdTransport = transportService.saveTransport(transportVB);

			rm.setResponseStatus(ResponseStatus.OK);
			rm.setData(createdTransport);
			rm.setMessage("Transport successfully created.");
			return new ResponseEntity<ResponseMessage>(rm, HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace();
			rm.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
			rm.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getDescription());
			return new ResponseEntity<ResponseMessage>(rm, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// ---------------------------Delete transport-------------------------
	@RequestMapping(value = "/delete/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> deleteTransport(@RequestBody TransportViewBean transportViewBean) {

		System.out.println("Deleting Transport " + transportViewBean.getIdTransport());

		TransportViewBean currentTransport = null;
		ResponseMessage rm = new ResponseMessage();

		try {
			

			currentTransport = transportService.deleteTransport(transportViewBean.getIdTransport());

			if (currentTransport == null) {
				rm.setResponseStatus(ResponseStatus.TRANSPORT_NOT_FOUND);
				rm.setMessage("No such Transport in database");
				return new ResponseEntity<ResponseMessage>(rm, HttpStatus.NO_CONTENT);
			}

			rm.setResponseStatus(ResponseStatus.OK);
			rm.setData(currentTransport);
			rm.setMessage("Transport successfully deleted.");
			return new ResponseEntity<ResponseMessage>(rm, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			rm.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
			rm.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getDescription());
			return new ResponseEntity<ResponseMessage>(rm, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// --------------------------------- List by Type-----------------------
	@RequestMapping(value = "/listAll/{type}/", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessage> listByTransportType(@PathVariable("type") String type) {
		List<TransportViewBean> transports;

		ResponseMessage rm = new ResponseMessage();
		
		System.out.println("List of all \"" + type + "\" transports");
		try {
			transports = transportService.findByTransportType(type);
			if (transports.isEmpty() || transports == null) {
				rm.setResponseStatus(ResponseStatus.TRANSPORT_NOT_FOUND);
				rm.setMessage("Transports of type " + type + "not found");
				return new ResponseEntity<ResponseMessage>(rm, HttpStatus.NOT_FOUND);
			}
			rm.setData(transports);
			rm.setResponseStatus(ResponseStatus.OK);
			rm.setMessage(ResponseStatus.OK.getDescription());
			return new ResponseEntity<ResponseMessage>(rm, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			rm.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
			rm.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getDescription());
			return new ResponseEntity<ResponseMessage>(rm, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// --------------------------------- Find all----------------------
		@RequestMapping(value = "/listAll", method = RequestMethod.GET)
		public ResponseEntity<ResponseMessage> listByTransportType() {
			List<TransportViewBean> transports;
			ResponseMessage message = new ResponseMessage();
			
			System.out.println("List of all transports");
			try {
				transports = transportService.findAll();
				if (transports.isEmpty()) {
					message.setResponseStatus(ResponseStatus.TRANSPORT_NOT_FOUND);
					message.setMessage("No transport in database");
					return new ResponseEntity<ResponseMessage>(message, HttpStatus.NO_CONTENT);
				}
				else {
					message.setData(transports);
					message.setResponseStatus(ResponseStatus.OK);
					message.setMessage(ResponseStatus.OK.getDescription());
					return new ResponseEntity<ResponseMessage>(message, HttpStatus.OK);
				}

			} catch (Exception e) {
				e.printStackTrace();
				message.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
				message.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getDescription());
				return new ResponseEntity<ResponseMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

	// -----------------------List of available transport-------------------
	@RequestMapping(value = "/findAvailableTransport", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> listAvailableTransport(@RequestBody TransportViewBean transportViewBean) {
		List<TransportViewBean> transports;
		ResponseMessage message = new ResponseMessage();

		try {
			transports = transportService.findAvailableTransport(transportViewBean.getDateFrom(), transportViewBean.getDateTo(), transportViewBean.getVehicle(), transportViewBean.getNumPosti());
			
			
			
			if (transports.isEmpty()) {
				message.setResponseStatus(ResponseStatus.LIST_NOT_FOUND);
				message.setMessage(ResponseStatus.LIST_NOT_FOUND.getDescription());
				return new ResponseEntity<ResponseMessage>(message, HttpStatus.NO_CONTENT);
			}
			
			else {
				message.setData(transports);
				message.setResponseStatus(ResponseStatus.OK);
				message.setMessage(ResponseStatus.OK.getDescription());
				return new ResponseEntity<ResponseMessage>(message, HttpStatus.OK);
			}

			
		} catch (Exception e) {
			e.printStackTrace();
			message.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
			message.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getDescription());
			return new ResponseEntity<ResponseMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
