package com.experis.highfly.dao;

import com.experis.highfly.entities.Vehicle;

public interface VehicleDao {

	Vehicle findVehicleByType(String type);

}