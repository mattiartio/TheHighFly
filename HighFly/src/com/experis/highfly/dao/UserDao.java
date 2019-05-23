package com.experis.highfly.dao;

import java.util.List;

import com.experis.highfly.entities.User;

public interface UserDao extends GenericDao<User>{
	
	public List<User> findByUsernameAndPassword(String username, String password);
	
	public User findUserByUsername(String username) throws Exception;
}
