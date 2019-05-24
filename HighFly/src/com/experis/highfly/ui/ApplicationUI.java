package com.experis.highfly.ui;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.experis.highfly.config.ConfigurationBean;
import com.experis.highfly.entities.User;
import com.experis.highfly.exception.AuthenticationException;
import com.experis.highfly.services.UserService;
import com.experis.highfly.viewbeans.UserViewBean;


public class ApplicationUI
{
	static AbstractApplicationContext  context = null;
	
	UserService userService;
	
	public static void main(String[] args)
	{
		try {

			// Caricamento del contesto e lettura delle configurazioni.
			context = new AnnotationConfigApplicationContext(ConfigurationBean.class);
			
			ApplicationUI ui = new ApplicationUI();
			
			ui.authenticateUser();

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			/*
			 * Spegnimento controllato del contesto;
			 * In questo modo, prima di eliminare tutti i bean controllati,
			 * vengono chiamati tutti i metodi dei bean annotati con @PreDestroy.
			 */
			if(context != null)
				context.registerShutdownHook();
			
			System.exit(0);
		}

	}

	private void authenticateUser() throws AuthenticationException
	{
		System.out.println("\nAutenticazione utente in corso..");
		
		userService = (UserService)context.getBean("userService");
		
		UserViewBean userViewBean = userService.authenticate("admin", "admin");
		
		if(userViewBean != null) {
			System.out.println("\n" + userViewBean + "\n");
			System.out.println("\nUtente correttamente autenticato\n");
		}
		
	}

}
