package com.experis.highfly.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle")
public class Vehicle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7933632186287415462L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "vehicle_id")
	private int id;
	
	@Column(name = "vehicle_type", unique = true)
	private String type;

	@OneToMany(mappedBy = "type")
	private List<Vehicle> vehicles;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Vehicle> getVehicles()
	{
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles)
	{
		this.vehicles = vehicles;
	}

	public void setType(String type)
	{
		this.type = type;
	}
	
	

}
