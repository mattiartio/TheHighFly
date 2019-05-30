package com.experis.highfly.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.experis.highfly.dao.VehicleDao;
import com.experis.highfly.entities.Vehicle;
import com.experis.highfly.services.VehicleService;
import com.experis.highfly.viewbeans.VehicleViewBean;

@Service(value = "vehicleService")
@Transactional(propagation = Propagation.REQUIRED)
public class VehicleServiceImpl implements VehicleService
{

	@Autowired
	@Qualifier("vehicleDao")
	VehicleDao vehicleDao;
	
	@Override
	@Transactional(propagation = Propagation.NEVER)
	public List<VehicleViewBean> findAll() throws Exception {
		List<Vehicle> vehicles = vehicleDao.findAll();
		List<VehicleViewBean> vehicleViewBeans = new ArrayList<VehicleViewBean>();
		
		for (Vehicle v : vehicles) {
			vehicleViewBeans.add(fillVehicleViewBean(v));
		}
		return vehicleViewBeans;
	}
	
	private VehicleViewBean fillVehicleViewBean(Vehicle vehicle) {
		VehicleViewBean vehicleViewBean = new VehicleViewBean();
		vehicleViewBean.setId(vehicle.getId());
		vehicleViewBean.setType(vehicle.getType());
		return vehicleViewBean;
	}
	
}
