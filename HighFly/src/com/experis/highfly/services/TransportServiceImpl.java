package com.experis.highfly.services;

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
	public void saveTransport(TransportViewBean transportVB) {

		Transport t = new Transport();

		t.setMaxSeats(transportVB.getMaxSeats());
		t.setPrice(transportVB.getPrice());

		Vehicle v = new Vehicle();
		v.setType(transportVB.getVehicle());
		v.setId(vehicleDao.findIdByType(transportVB.getVehicle()));

		t.setType(v);

		transportDao.insert(t);
	}

	@Override
	@Transactional
	public void deleteTransport(int transportId) {
		transportDao.delete(transportId);
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
