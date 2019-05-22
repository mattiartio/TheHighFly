package com.experis.highfly.services;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.experis.highfly.dao.TransportDao;
import com.experis.highfly.entities.Transport;

@Service(value = "transportService")
public class TransportServiceImpl implements TransportService {

	@Autowired
	@Qualifier("transportDao")
	TransportDao transportDao;

	@Override
	@Transactional
	public void saveTransport(Transport transport) {
		transportDao.insert(transport);
	}

	@Override
	@Transactional
	public void deleteTransport(int transportId) {
		transportDao.delete(transportId);
	}

	@Override
	public List<Transport> findByTransportType(int transportType) throws Exception {
		return transportDao.findByTransportType(transportType);
	}

	@Override
	public List<Transport> findAvailableTransport(Date dateFrom, Date dateTo) throws Exception {
		return transportDao.findAvailableTransport(dateFrom, dateTo);
	}

}
