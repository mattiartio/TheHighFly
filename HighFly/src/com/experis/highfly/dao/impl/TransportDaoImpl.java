package com.experis.highfly.dao.impl;

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

}
