package com.experis.highfly.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.experis.highfly.dao.UserDao;
import com.experis.highfly.entities.User;
import com.experis.highfly.exception.AuthenticationException;

@Service(value="userService")
public class UserServiceImpl implements UserService
{
	@Autowired
	@Qualifier("userDao")
	UserDao userDao;
	
	@Override
	public User authenticate(String username, String password) throws AuthenticationException {
		
		 List<User> users = userDao.findByUsernameAndPassword(username, password);
		 if(users != null && !users.isEmpty()) {
			 
			 if(users.size() > 1)
				 throw new AuthenticationException("Too many users");
			 
			 return users.get(0);
		 }
		 throw new AuthenticationException("User not found");
	}
	
	public void saveUser(User user) throws Exception {
		userDao.insert(user);
	}

	@Override
	public User findByPrimaryKey(Long id) throws Exception {
		return userDao.find(id);
	}
}
