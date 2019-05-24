package com.experis.highfly.dao.impl;

import java.sql.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.experis.highfly.dao.TransportDao;
import com.experis.highfly.entities.Transport;

@Repository(value = "transportDao")
@Scope(value = "prototype")
public class TransportDaoImpl extends GenericDaoImpl<Transport> implements TransportDao {

	@Override
	public List<Transport> findByTransportType(String transportType) {

		List<Transport> retList = null;

		Query q = em.createQuery("Select t from Transport t join t.vehicle v where v.type = :transportType");
		q.setParameter("transportType", transportType);
		retList = (List<Transport>) q.getResultList();

		return retList;

	}

	public List<Transport> findAvailableTransport(Date dateFrom, Date dateTo) {
		List<Transport> retList = null;

		Query q = em.createNativeQuery(
				"select t from Transport t where t.id not in ( select t.id from Booking b, Transport t where b.transport = t.id and b.dateFrom > :dateFrom and b.dateTo < :dateTo)");

		q.setParameter("dateFrom", dateFrom);
		q.setParameter("dateTo", dateTo);

		retList = (List<Transport>) q.getResultList();

		return retList;

	}

}
