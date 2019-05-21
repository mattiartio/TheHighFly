package com.experis.highfly.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.experis.highfly.entities.User;
import com.experis.highfly.exception.AuthenticationException;
import com.experis.highfly.services.UserService;
import com.experis.highfly.viewbeans.UserViewBean;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
  
    //-------------------Retrieve Single User--------------------------------------------------------
      
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        System.out.println("Fetching User with id " + id);	//DA CREARE IL MESSAGGIO
        User user;
		try {
			user = userService.findByPrimaryKey(id);
			if (user == null) {
				System.out.println("User with id " + id + " not found");
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    //------------------Login-------------------------------------------------------------------
    
    @RequestMapping(value = "/login/", method = RequestMethod.POST)
    public ResponseEntity<User> login(@RequestBody UserViewBean user) {
        System.out.println("Login user " + user.getUsername());
  /*
        if (userService.isUserExist(user)) {
            System.out.println("A User with name " + user.getName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
  */
        User userLocal;
        try
		{
			userLocal = userService.authenticate(user.getUsername(), user.getPassword());
			if (userLocal == null) {
				System.out.println("User with username " + user.getUsername() + " not found");
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<User>(userLocal, HttpStatus.OK);
		} catch (AuthenticationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
        
    }
  
}