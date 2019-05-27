package com.experis.highfly.services;

import com.experis.highfly.entities.User;
import com.experis.highfly.exception.AuthenticationException;
import com.experis.highfly.exception.SaveException;
import com.experis.highfly.viewbeans.UserViewBean;

public interface UserService
{

	UserViewBean findByPrimaryKey(Long id) throws Exception;

	UserViewBean authenticate(String username, String password) throws AuthenticationException, Exception;
	
	void saveUser(UserViewBean user) throws Exception;

}