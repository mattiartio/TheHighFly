package com.experis.highfly.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.experis.highfly.exception.AuthenticationException;
import com.experis.highfly.exception.SaveException;
import com.experis.highfly.messages.ResponseMessage;
import com.experis.highfly.messages.ResponseStatus;
import com.experis.highfly.services.UserService;
import com.experis.highfly.viewbeans.UserViewBean;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
  
    //-------------------Retrieve Single User--------------------------------------------------------
      
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ResponseMessage> getUser(@PathVariable("id") long id) {
        System.out.println("Fetching User with id " + id);	//DA CREARE IL MESSAGGIO
        UserViewBean user;
        ResponseMessage message = new ResponseMessage();
		try {
			user = userService.findByPrimaryKey(id);
			
			message.setData(user);
			
			message.setResponseStatus(ResponseStatus.OK);
			message.setMessage(ResponseStatus.OK.getDescription());
			return new ResponseEntity<ResponseMessage>(message, HttpStatus.OK);
			
		} catch (AuthenticationException e)
		{
			System.out.println("User with id " + id + " not found");
			message.setResponseStatus(ResponseStatus.USER_NOT_FOUND);
			message.setMessage(ResponseStatus.USER_NOT_FOUND.getDescription());
			return new ResponseEntity<ResponseMessage>(message, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseMessage>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    //------------------Login-------------------------------------------------------------------
    
    @RequestMapping(value = "/login/", method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<ResponseMessage> login(@RequestBody UserViewBean user) {
        System.out.println("Login user " + user.getUsername());
        UserViewBean userLocal;
        ResponseMessage message = new ResponseMessage();
        try
		{
        	if (user.getUsername() == null || user.getPassword() == null) {
        		message.setResponseStatus(ResponseStatus.JSON_ERROR);
    			message.setMessage(ResponseStatus.JSON_ERROR.getDescription());
    			return new ResponseEntity<ResponseMessage>(message, HttpStatus.UNPROCESSABLE_ENTITY);
        	}
        	
        	
			userLocal = userService.authenticate(user.getUsername(), user.getPassword());
			message.setData(userLocal);
			
			message.setResponseStatus(ResponseStatus.OK);
			message.setMessage(ResponseStatus.OK.getDescription());
			return new ResponseEntity<ResponseMessage>(message, HttpStatus.OK);
		} catch (AuthenticationException e)
		{
			System.out.println("User with username " + user.getUsername() + " not found");
			message.setResponseStatus(ResponseStatus.USER_NOT_FOUND);
			message.setMessage(ResponseStatus.USER_NOT_FOUND.getDescription());
			return new ResponseEntity<ResponseMessage>(message, HttpStatus.NOT_FOUND);
		}
        catch (Exception e)
        {
        	e.printStackTrace();
			message.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
			message.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getDescription());
			return new ResponseEntity<ResponseMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        
    }
    
//------------------Registrazione-------------------------------------------------------------------
    
    @RequestMapping(value = "/signin/", method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<ResponseMessage> signIn(@RequestBody UserViewBean user) {
        System.out.println("SignIn user " + user.getUsername());
        UserViewBean userLocal;
        ResponseMessage message = new ResponseMessage();
        try
		{
        	if (user.getUsername() == null || user.getPassword() == null || user.getNome() == null || user.getCognome() == null || user.getEmail() == null || user.getRole() == null) {
        		message.setResponseStatus(ResponseStatus.JSON_ERROR);
    			message.setMessage(ResponseStatus.JSON_ERROR.getDescription());
    			return new ResponseEntity<ResponseMessage>(message, HttpStatus.UNPROCESSABLE_ENTITY);
        	}
        	
        	
			userService.saveUser(user);
			
			message.setResponseStatus(ResponseStatus.OK);
			message.setMessage(ResponseStatus.OK.getDescription());
			return new ResponseEntity<ResponseMessage>(message, HttpStatus.OK);
		}
        catch (SaveException e)
        {
        	e.printStackTrace();
			message.setResponseStatus(ResponseStatus.DUPLICATE_RECORD);
			message.setMessage(ResponseStatus.DUPLICATE_RECORD.getDescription());
			return new ResponseEntity<ResponseMessage>(message, HttpStatus.NOT_ACCEPTABLE);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
			message.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
			message.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getDescription());
			return new ResponseEntity<ResponseMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        
    }
  
}