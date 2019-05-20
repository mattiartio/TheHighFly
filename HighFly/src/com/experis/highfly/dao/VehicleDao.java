package com.experis.highfly.dao;

import java.util.List;

import com.experis.highfly.entities.Vehicle;

public interface VehicleDao extends GenericDao<Vehicle>  {
	
	public List<Vehicle> listAllByType(String type);

}