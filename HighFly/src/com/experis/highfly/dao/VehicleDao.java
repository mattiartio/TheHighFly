package com.experis.highfly.dao;

import com.experis.highfly.entities.Vehicle;

public interface VehicleDao extends GenericDao<Vehicle> {

	public int findIdByType(String type);

}