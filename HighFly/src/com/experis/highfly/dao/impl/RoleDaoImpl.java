package com.experis.highfly.dao.impl;

import javax.persistence.EntityManager;

import com.experis.highfly.entities.Role;

public class RoleDaoImpl {

	public Role findRoleByType(String type) {
		EntityManager em = null;
		
		Role role = em.find(Role.class, type);
		
		em.close();
		
		return role;
	}
	
}
