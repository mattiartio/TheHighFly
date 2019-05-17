package com.experis.highfly.entities.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.envers.EntityTrackingRevisionListener;

import com.experis.highfly.entities.User;
import com.experis.highfly.entities.Vehicle;
import com.opengest.jpa.utils.JpaUtils;

public class VehicleDaoImpl {

	public Vehicle findVehicleById(long id) {

		EntityManager em = JpaUtils.getInstance().getEntityManager();
		Vehicle vehicle = em.find(Vehicle.class, id);

		em.close();

		return vehicle;
	}

	public Vehicle findVehicleByType(String type) {

		EntityManager em = JpaUtils.getInstance().getEntityManager();
		Vehicle vehicle = em.find(Vehicle.class, type);

		em.close();

		return vehicle;
	}

	/*
	 * public void update(long id, String type) { EntityManager em =
	 * JpaUtils.getInstance().getEntityManager(); Vehicle vehicle =
	 * findVehicleById(id); em.getTransaction().begin(); vehicle.setType(type);
	 * em.getTransaction().commit(); }
	 */

	public void insert(Vehicle vehicle) {
		EntityManager em = null;
		EntityTransaction trx = null;
		try {
			
			em = JpaUtils.getInstance().getEntityManager();
			trx = em.getTransaction();
			trx.begin();
			em.persist(vehicle);
			em.flush();
			trx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			trx.rollback();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void delete(long id) {
		EntityManager em = null;
		EntityTransaction trx = null;
		try {
			
			em = JpaUtils.getInstance().getEntityManager();
			trx = em.getTransaction();
			trx.begin();
			Vehicle vehicle = findVehicleById(id);
			em.remove(vehicle);
			em.flush();
			trx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			trx.rollback();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	
	public void update(Vehicle vehicle) {
		EntityManager em = null;
		EntityTransaction trx = null;
		try {
			
			em = JpaUtils.getInstance().getEntityManager();
			trx = em.getTransaction();
			trx.begin();
			em.merge(vehicle);
			em.flush();
			trx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			trx.rollback();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
}
