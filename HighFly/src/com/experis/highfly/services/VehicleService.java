package com.experis.highfly.services;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.experis.highfly.viewbeans.VehicleViewBean;

public interface VehicleService
{

	List<VehicleViewBean> findAll() throws Exception;

}