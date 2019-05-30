package com.experis.highfly.services;

import java.util.Date;
import java.util.List;

import com.experis.highfly.entities.Transport;
import com.experis.highfly.exception.DateException;
import com.experis.highfly.viewbeans.TransportViewBean;

public interface TransportService {

	public TransportViewBean saveTransport(TransportViewBean transportVB);

	public TransportViewBean deleteTransport(int transportId);

	public List<TransportViewBean> findByTransportType(String transportType) throws Exception;

	public List<TransportViewBean> findAvailableTransport(Date dateFrom, Date dateTo, String type, int numPosti) throws Exception, DateException;

	List<TransportViewBean> findAll() throws Exception;

}
