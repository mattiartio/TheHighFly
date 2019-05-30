package com.experis.highfly.dao.impl;

import java.util.Date;
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

		Query q = em.createQuery("select t from Transport t join t.vehicle v where v.type = :transportType");
		q.setParameter("transportType", transportType);
		retList = (List<Transport>)q.getResultList();

		return retList;

	}

	public List<Transport> findAvailableTransport(Date dateFrom, Date dateTo, String type, int numPosti) {
		List<Transport> retList = null;
		Query q;
		
		if (type == "car")
		{
			q = em.createQuery("select t from Transport t where t.id not in "
					+ "("
					+ "select tr.id from Booking b join b.transport tr where"
					+ "((b.dateFrom >= :dataInizio and b.dateTo <= :dataFine) or"
					+ "(b.dateFrom <= :dataInizio and b.dateTo >= :dataFine)) and "
					+ ")"
					+ "t.vehicle.type = :type "
					+ "and t.maxSeats > :numPosti");
			q.setParameter("numPosti", numPosti);
		}
		else {
			q = em.createQuery("select t from Transport t where t.id not in "
					+ "("
					+ "select tr.id from Booking b join b.transport tr "
					+ "where ((b.dateFrom >= :dataInizio and b.dateTo <= :dataFine) "
					+ "or (b.dateFrom <= :dataInizio and b.dateTo >= :dataFine)) "
					+ "group by tr.id "
					+ "having sum (b.seats) > t.maxSeats - :numPosti"
					+ ")"
					+ "and t.vehicle.type = :type "
					+ "and t.maxSeats > :numPosti");
			q.setParameter("numPosti", numPosti);
		}
		
		
		q.setParameter("dataInizio", dateFrom);
		q.setParameter("dataFine", dateTo);
		q.setParameter("type", type);
		

		retList = (List<Transport>)q.getResultList();

		return retList;

	}

}
