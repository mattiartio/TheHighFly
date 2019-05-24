package com.experis.highfly.services.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.experis.highfly.dao.TransportDao;
import com.experis.highfly.dao.VehicleDao;
import com.experis.highfly.entities.Transport;
import com.experis.highfly.entities.Vehicle;
import com.experis.highfly.services.TransportService;
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

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
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
	// @Transactional
	public Transport deleteTransport(int transportId) {
		Transport t = new Transport();
		t = transportDao.find(transportId);

		transportDao.delete(transportId);

		return t;
	}

	private TransportViewBean fillTransportViewBean(Transport transport) {

		TransportViewBean transportViewBean = new TransportViewBean();

		transportViewBean.setIdTransport(transport.getId());
		transportViewBean.setMaxSeats(transport.getMaxSeats());
		transportViewBean.setPrice(transport.getPrice());
		transportViewBean.setVehicle(transport.getVehicle().getType());

		return transportViewBean;

	}

	@Override
	public List<TransportViewBean> findByTransportType(String transportType) throws Exception {

		List<Transport> transports = transportDao.findByTransportType(transportType);

		List<TransportViewBean> transportViewBeans = new ArrayList<TransportViewBean>();

		for (Transport t : transports) {
			transportViewBeans.add(fillTransportViewBean(t));
		}

		return transportViewBeans;
	}

	@Override
	public List<TransportViewBean> findAvailableTransport(Date dateFrom, Date dateTo) throws Exception {

		List<Transport> transports = transportDao.findAvailableTransport(dateFrom, dateTo);

		List<TransportViewBean> transportViewBeans = new ArrayList<TransportViewBean>();

		for (Transport t : transports) {
			transportViewBeans.add(fillTransportViewBean(t));
		}

		return transportViewBeans;

	}

}
