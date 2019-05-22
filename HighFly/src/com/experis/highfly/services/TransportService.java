package com.experis.highfly.services;

import java.sql.Date;
import java.util.List;

import com.experis.highfly.entities.Transport;

public interface TransportService {

	public void saveTransport(Transport transport);

	public List<Transport> findByTransportType(int transportType) throws Exception;

	public List<Transport> findAvailableTransport(int id, Date dateFrom, Date dateTo) throws Exception;

}
