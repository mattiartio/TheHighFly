package com.experis.highfly.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.experis.highfly.dao.UserDao;
import com.experis.highfly.entities.User;




@Repository(value = "userDao")
@Scope(value="prototype")
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

	@Override
	
	// TODO Auto-generated method stub
	public List<User> findByUsernameAndPassword(String username, String password) {
		// Costruzione dei filtri
	
		List<User> users =null;
		Query q = em.createQuery("Select u from User u where username=:user and password =:pass");
		q.setParameter("user", username);
		q.setParameter("pass", password);
		users =(List<User>)q.getResultList();
		return users;
	}
	

	public List<User> findUserByUsername(String username) {
		
		List<User> users =null;
		Query q = em.createQuery("Select u.username from User u where username=:user");
		q.setParameter("user", username);
		users = (List<User>)q.getResultList();
		return users;
	}
	
}
