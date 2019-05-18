package com.experis.highfly.dao.impl;

import javax.persistence.EntityManager;

import com.experis.highfly.entities.User;
import com.experis.highfly.utils.JpaUtils;


public class UserDaoImpl  extends GenericDaoImpl<User>{

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
