package com.experis.highfly.entities.dao.impl;

import javax.persistence.EntityManager;

import com.experis.highfly.entities.User;
import com.opengest.jpa.utils.JpaUtils;


/*jesoo*/

public class UserDaoImpl {

	public User findUserById(long id) {
		
		EntityManager em = JpaUtils.getInstance().getEntityManager();
		User user = em.find(User.class, id);
		
		em.close();
		
		return user;
	}
	
	public User findUserByUsername(String username) {
		
		EntityManager em = JpaUtils.getInstance().getEntityManager();
		User user = em.find(User.class, username);
		
		em.close();
		
		return user;
	}
	
}
