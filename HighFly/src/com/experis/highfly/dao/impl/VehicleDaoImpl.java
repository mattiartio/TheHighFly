package com.experis.highfly.dao.impl;

import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.experis.highfly.dao.VehicleDao;
import com.experis.highfly.entities.Vehicle;

//Qua c'� repository perch� � un dao e ha il nome cambiato in "vehicleDao" perch� se no dovremmo usare "vehicleDaoImpl"
//C'� prototype perch� ogni volta che viene chiamato deve essere costruito un dao, altrimenti due chiamate userebbero
//lo stesso Dao e dopo potrebbero esserci problemi con i dati che immettiamo o eliminiamo dal database
@Repository("vehicleDao")
@Scope(value = "prototype")
public class VehicleDaoImpl extends GenericDaoImpl<Vehicle> implements VehicleDao {

	public int findIdByType(String type) {

		int id;

		Query q = em.createQuery("select v.id from Vehicle v where v.type=:tipo");

		q.setParameter("tipo", type);

		id = (int) q.getSingleResult();

		return id;
	}

}
