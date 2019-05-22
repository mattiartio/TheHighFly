package com.experis.highfly.services.impl;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.experis.highfly.dao.TransportDao;
import com.experis.highfly.dao.VehicleDao;
import com.experis.highfly.entities.Transport;
import com.experis.highfly.entities.Vehicle;
import com.experis.highfly.services.TransportService;
import com.experis.highfly.viewbeans.TransportViewBean;

@Service(value = "transportService")
public class TransportServiceImpl implements TransportService {

	@Autowired
	@Qualifier("transportDao")
	TransportDao transportDao;

	@Autowired
	@Qualifier("vehicleDao")
	VehicleDao vehicleDao;

	@Override
	@Transactional
	public Transport saveTransport(TransportViewBean transportVB) {

		Transport t = new Transport();

		t.setMaxSeats(transportVB.getMaxSeats());
		t.setPrice(transportVB.getPrice());

		Vehicle v = new Vehicle();
		v.setType(transportVB.getVehicle());
		v.setId(vehicleDao.findIdByType(transportVB.getVehicle()));

		t.setType(v);

		transportDao.insert(t);
		return t;
	}

	@Override
	@Transactional
	public Transport deleteTransport(int transportId) {
		Transport t = new Transport();
		t = transportDao.find(transportId);

		transportDao.delete(transportId);

		return t;
	}

	@Override
	public List<Transport> findByTransportType(String transportType) throws Exception {
		return transportDao.findByTransportType(transportType);
	}

	@Override
	public List<Transport> findAvailableTransport(Date dateFrom, Date dateTo) throws Exception {
		return transportDao.findAvailableTransport(dateFrom, dateTo);
	}

}
