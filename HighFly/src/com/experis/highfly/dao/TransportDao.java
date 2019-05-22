package com.experis.highfly.dao;

import java.sql.Date;
import java.util.List;

import com.experis.highfly.entities.Transport;

public interface TransportDao extends GenericDao<Transport> {

	public List<Transport> findByTransportType(int transportType);

	public List<Transport> findAvailableTransport(Date dateFrom, Date dateTo);

}
