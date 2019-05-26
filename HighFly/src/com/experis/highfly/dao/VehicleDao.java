package com.experis.highfly.dao;

import com.experis.highfly.entities.Vehicle;

public interface VehicleDao extends GenericDao<Vehicle> {

	public Vehicle findByType(String type);

}