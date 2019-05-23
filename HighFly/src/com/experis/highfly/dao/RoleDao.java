package com.experis.highfly.dao;

import java.util.List;

import com.experis.highfly.entities.Role;

public interface RoleDao
{
	
	Role findByType(String type);

}