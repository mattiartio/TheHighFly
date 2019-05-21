package com.experis.highfly.dao.impl;

import java.sql.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.experis.highfly.dao.TransportDao;
import com.experis.highfly.entities.Transport;

@Repository(value = "transportDao")
public class TransportDaoImpl extends GenericDaoImpl<Transport> implements TransportDao {

	@Override
	public List<Transport> findByTransportType(int transportType) {

		List<Transport> retList = null;

		Query q = em.createQuery("select t from Transport t where t.transportType = :transportType");
		q.setParameter("transportType", transportType);
		retList = q.getResultList();

		return retList;

	}

	public List<Transport> findAvailableTransport(int id, Date dateFrom, Date dateTo) {
		List<Transport> retList = null;

		Query q = em.createNativeQuery(
				"select t from Transport t where t.type.id = :id and t.id not in ( select t.id from Booking b, Transport t where b.transport = t.id and b.dateFrom > :dateFrom and b.dateTo < :dateTo)");
		q.setParameter("id", id);
		q.setParameter("dateFrom", dateFrom);
		q.setParameter("dateTo", dateTo);

		retList = q.getResultList();

		return retList;

	}

}
