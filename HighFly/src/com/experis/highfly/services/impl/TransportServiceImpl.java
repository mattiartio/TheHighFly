package com.experis.highfly.services.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.experis.highfly.dao.BookingDao;
import com.experis.highfly.dao.TransportDao;
import com.experis.highfly.dao.VehicleDao;
import com.experis.highfly.entities.Booking;
import com.experis.highfly.entities.Transport;
import com.experis.highfly.entities.Vehicle;
import com.experis.highfly.services.TransportService;
import com.experis.highfly.utils.BookingFilter;
import com.experis.highfly.viewbeans.TransportViewBean;

@Service(value = "transportService")
@Transactional(propagation = Propagation.REQUIRED)
public class TransportServiceImpl implements TransportService {

	@Autowired
	@Qualifier("transportDao")
	TransportDao transportDao;

	@Autowired
	@Qualifier("vehicleDao")
	VehicleDao vehicleDao;
	
	@Autowired
	@Qualifier("bookingDao")
	BookingDao bookingDao;

	@Override

	public TransportViewBean saveTransport(TransportViewBean transportVB) {

		Transport t = new Transport();
		
		t.setMaxSeats(transportVB.getMaxSeats());
		t.setPrice(transportVB.getPrice());
		t.setVehicle(vehicleDao.findByType(transportVB.getVehicle()));

		transportDao.insert(t);
		return transportVB;
	}

	@Override
	@Transactional
	public TransportViewBean deleteTransport(int transportId) {
		TransportViewBean t = new TransportViewBean();
		t = fillTransportViewBean(null, null, 0, transportDao.find(transportId));
		
		BookingFilter bookingFilter = new BookingFilter();
		bookingFilter.setTransportId(transportId);
		
		List<Booking> bookings = bookingDao.findBookingByFilters(bookingFilter);
		
		for (Booking b : bookings) {
			bookingDao.delete(b.getId());
		}

		transportDao.delete(transportId);

		return t;
	}
	
	@Override
	@Transactional(propagation = Propagation.NEVER)
	public List<TransportViewBean> findAll() throws Exception {
		List<Transport> transports = transportDao.findAll();
		List<TransportViewBean> transportViewBeans = new ArrayList<TransportViewBean>();
		
		for (Transport t : transports) {
			transportViewBeans.add(fillTransportViewBean(null, null, 0, t));
		}
		return transportViewBeans;
	}

	@Override
	public List<TransportViewBean> findByTransportType(String transportType) throws Exception {
		List<Transport> transports = transportDao.findByTransportType(transportType);
		List<TransportViewBean> transportViewBeans = new ArrayList<TransportViewBean>();
		
		for (Transport t : transports) {
			transportViewBeans.add(fillTransportViewBean(null, null, 0, t));
		}
		return transportViewBeans;
	}

	private TransportViewBean fillTransportViewBean(Date dateFrom, Date dateTo, int numPosti, Transport transport) {
		TransportViewBean transportViewBean = new TransportViewBean();
		transportViewBean.setIdTransport(transport.getId());
		transportViewBean.setMaxSeats(transport.getMaxSeats());
		transportViewBean.setPrice(transport.getPrice());
		transportViewBean.setVehicle(transport.getVehicle().getType());
		transportViewBean.setNumPosti(numPosti);
		transportViewBean.setDateFrom(dateFrom);
		transportViewBean.setDateTo(dateTo);
		return transportViewBean;
	}
	
	@Override
	public List<TransportViewBean> findAvailableTransport(Date dateFrom, Date dateTo, String type, int numPosti)  throws Exception {
		List<Transport> transports = transportDao.findAvailableTransport(dateFrom, dateTo, type, numPosti);
		List<TransportViewBean> transportViewBeans = new ArrayList<TransportViewBean>();
		
		for (Transport t : transports) {
			transportViewBeans.add(fillTransportViewBean(dateFrom, dateTo, numPosti, t));
		}
		return transportViewBeans;
	}

}
