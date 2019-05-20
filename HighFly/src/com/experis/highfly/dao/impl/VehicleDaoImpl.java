package com.experis.highfly.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.experis.highfly.dao.VehicleDao;
import com.experis.highfly.entities.Vehicle;

//Qua c'è repository perchè è un dao e ha il nome cambiato in "vehicleDao" perchè se no dovremmo usare "vehicleDaoImpl"
//C'è prototype perchè ogni volta che viene chiamato deve essere costruito un dao, altrimenti due chiamate userebbero
//lo stesso Dao e dopo potrebbero esserci problemi con i dati che immettiamo o eliminiamo dal database
@Repository("vehicleDao")
@Scope(value = "prototype")
public class VehicleDaoImpl extends GenericDaoImpl<Vehicle> implements VehicleDao{

	
	public List<Vehicle> listAllByType(String type) {
		
		List<Vehicle> retList = null;
		
		Query q = em.createQuery("select v.* from vehicle v where vehicle_type=" + type );
		
		retList = (List<Vehicle>)q.getResultList();
		
		return retList;
	}
}
