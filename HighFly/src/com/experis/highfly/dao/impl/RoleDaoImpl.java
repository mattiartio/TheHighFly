package com.experis.highfly.dao.impl;

import javax.persistence.EntityManager;

import com.experis.highfly.entities.Role;
import com.experis.highfly.utils.JpaUtils;

public class RoleDaoImpl {

	public Role findRoleById(long id) {
		EntityManager em = JpaUtils.getInstance().getEntityManager();
		
		Role role = em.find(Role.class, id);
		
		em.close();
		
		return role;
	}
	
	public Role findRoleByType(String type) {
		EntityManager em = JpaUtils.getInstance().getEntityManager();
		
		Role role = em.find(Role.class, type);
		
		em.close();
		
		return role;
	}
	
}
