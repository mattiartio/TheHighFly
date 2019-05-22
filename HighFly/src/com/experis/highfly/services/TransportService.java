package com.experis.highfly.services;

import java.sql.Date;
import java.util.List;

import com.experis.highfly.entities.Transport;
import com.experis.highfly.viewbeans.TransportViewBean;

public interface TransportService {

	public Transport saveTransport(TransportViewBean transportVB);

	public Transport deleteTransport(int transportId);

	public List<Transport> findByTransportType(String transportType) throws Exception;

	public List<Transport> findAvailableTransport(Date dateFrom, Date dateTo) throws Exception;

}
