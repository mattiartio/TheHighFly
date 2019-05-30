package com.experis.highfly.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.experis.highfly.dao.TransportDao;
import com.experis.highfly.entities.Transport;

@Repository(value = "transportDao")
@Scope(value = "prototype")
public class TransportDaoImpl extends GenericDaoImpl<Transport> implements TransportDao
{

	@Override
	public List<Transport> findByTransportType(String transportType)
	{

		List<Transport> retList = null;

		Query q = em.createQuery("select t from Transport t join t.vehicle v where v.type = :transportType");
		q.setParameter("transportType", transportType);
		retList = (List<Transport>) q.getResultList();

		return retList;

	}

	public List<Transport> findAvailableTransport(Date dateFrom, Date dateTo, String type, int numPosti) {
		List<Transport> retList = null;
		Query q;
		
		if (type.equals("car"))
		{
//			q = em.createQuery("select t from Transport t where t.id not in "
//					+ "("
//					+ "select tr.id from Booking b join b.transport tr where"
//					+ "((b.dateFrom >= :dataInizio and b.dateTo <= :dataFine) or"
//					+ "(b.dateFrom <= :dataInizio and b.dateTo >= :dataFine)) "
//					+ ") "
//					+ "and t.vehicle.type = :type");
			q = em.createNativeQuery("SELECT * "
					+ "FROM Transport t join vehicle v on t.transport_type = v.vehicle_id WHERE t.transport_id NOT IN"
					+ "( "
					+ "SELECT tr.transport_id FROM booking b join transport tr on b.transport_id = tr.transport_id "
					+ "WHERE ( ( b.booking_date_from >= :dataInizio AND b.booking_date_to <= :dataFine ) "
					+ "OR( b.booking_date_from <= :dataInizio AND b.booking_date_to >= :dataFine ) ) "
					+ ") "
					+ "AND v.vehicle_type = :type", Transport.class);
			q.setParameter("type", type);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			q.setParameter("dataInizio", sdf.format(dateFrom));
			q.setParameter("dataFine", sdf.format(dateTo));
		}
		else {
//			q = em.createQuery("select t from Transport t where t.id not in "
//					+ "("
//					+ "select tr.id from Booking b join b.transport tr "
//					+ "where ((b.dateFrom >= :dataInizio and b.dateTo <= :dataFine) "
//					+ "or (b.dateFrom <= :dataInizio and b.dateTo >= :dataFine)) "
//					+ "group by tr.id "
//					+ "having sum (b.seats) >= t.maxSeats - :numPosti"
//					+ ") "
//					+ "and t.vehicle.type = :type "
//					+ "and t.maxSeats >= :numPosti");
			q = em.createNativeQuery("SELECT * "
					+ "FROM Transport t join vehicle v on t.transport_type = v.vehicle_id WHERE t.transport_id NOT IN"
					+ "( "
					+ "SELECT tr.transport_id FROM booking b join transport tr on b.transport_id = tr.transport_id "
					+ "WHERE ( ( b.booking_date_from >= :dataInizio AND b.booking_date_to <= :dataFine ) "
					+ "OR( b.booking_date_from <= :dataInizio AND b.booking_date_to >= :dataFine ) ) "
					+ "group by tr.transport_id "
					+ "having sum(b.number_seats) >= t.max_seats - :numPosti"
					+ ") "
					+ "AND v.vehicle_type = :type "
					+ "and t.max_seats >= :numPosti", Transport.class);
			q.setParameter("type", type);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			q.setParameter("dataInizio", sdf.format(dateFrom));
			q.setParameter("dataFine", sdf.format(dateTo));
			q.setParameter("numPosti", numPosti);
		}

		retList = (List<Transport>)q.getResultList();
	
		return retList;

	}

}
