package com.experis.highfly.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.experis.highfly.dao.RoleDao;
import com.experis.highfly.dao.UserDao;
import com.experis.highfly.entities.User;
import com.experis.highfly.exception.AuthenticationException;
import com.experis.highfly.exception.SaveException;
import com.experis.highfly.services.UserService;
import com.experis.highfly.viewbeans.UserViewBean;

@Service(value = "userService")
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService
{
	@Autowired
	@Qualifier("userDao")
	UserDao userDao;

	@Autowired
	@Qualifier("roleDao")
	RoleDao roleDao;

	@Override
	@Transactional(propagation = Propagation.NEVER)
	public UserViewBean authenticate(String username, String password) throws AuthenticationException
	{

		UserViewBean userViewBean = new UserViewBean();
		List<User> users = userDao.findByUsernameAndPassword(username, password);
		if (users != null && !users.isEmpty())
		{

			if (users.size() > 1)
				throw new AuthenticationException("Too many users");

			userViewBean.setUsername(users.get(0).getUsername());
			userViewBean.setNome(users.get(0).getName());
			userViewBean.setCognome(users.get(0).getSurname());
			userViewBean.setEmail(users.get(0).getEmail());
			userViewBean.setRole(users.get(0).getRole().getType());

			return userViewBean;
		}
		throw new AuthenticationException("User not found");

	}

	@Override
	public void saveUser(UserViewBean user) throws Exception
	{
		System.out.println(userDao.findUserByUsername(user.getUsername()));
		if (userDao.findUserByUsername(user.getUsername()) != null)
			throw new SaveException("Username already existing");
		User utente = fillUser(user);

		userDao.insert(utente);
	}

	private User fillUser(UserViewBean user) throws Exception
	{
		User u = new User();

		u.setUsername(user.getUsername());
		u.setPassword(user.getPassword());
		u.setName(user.getNome());
		u.setSurname(user.getCognome());
		u.setEmail(user.getEmail());
		u.setCompany(user.getCompany());
		u.setRole(roleDao.findByType(user.getRole()));

		return u;

	}

	@Override
	public User findByPrimaryKey(Long id) throws Exception
	{
		return userDao.find(id);
	}
}
