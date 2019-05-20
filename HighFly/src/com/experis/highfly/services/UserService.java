package com.experis.highfly.services;

import java.util.List;

import com.experis.highfly.entities.User;
import com.experis.highfly.exception.AuthenticationException;

public interface UserService
{

	List<User> findAllUsers() throws Exception;

	User findByPrimaryKey(Long id) throws Exception;

	User authenticate(String username, String password) throws AuthenticationException;

}