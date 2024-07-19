package com.hello.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.googlecode.objectify.ObjectifyService;
import com.hello.shared.model.User;
import com.hello.shared.model.User;

@WebListener
public class SoictContextListener implements ServletContextListener {


	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ObjectifyService.register(User.class);
	}
}
