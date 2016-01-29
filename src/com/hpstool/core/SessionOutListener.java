package com.hpstool.core;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.http.impl.client.CloseableHttpClient;

public class SessionOutListener implements HttpSessionListener{

	@Override
	public void sessionCreated( HttpSessionEvent event ) {
		
	}

	@Override
	public void sessionDestroyed( HttpSessionEvent event ) {
		CloseableHttpClient client = (CloseableHttpClient)event.getSession().getAttribute( "client" );
		
		if (client != null) {
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
}
