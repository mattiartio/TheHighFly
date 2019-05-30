package com.experis.highfly.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.experis.highfly.exception.BookingException;
import com.experis.highfly.messages.ResponseMessage;
import com.experis.highfly.messages.ResponseStatus;
import com.experis.highfly.services.VehicleService;
import com.experis.highfly.viewbeans.VehicleViewBean;

@RestController
@RequestMapping("/vehicles")
public class VehicleController
{
	@Autowired
	private VehicleService vehicleService;
	
	@RequestMapping(value = "/listall", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessage> getBookingsByUsername() {

			List<VehicleViewBean> vehicles;
			ResponseMessage rm = new ResponseMessage();

			try {

				vehicles = vehicleService.findAll();
				rm.setResponseStatus(ResponseStatus.OK);
				rm.setMessage(ResponseStatus.OK.getDescription());
				rm.setData(vehicles);
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
}
