package com.hpstool.core;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpstool.core.request.StageRequest;
import com.hpstool.core.resolver.StageResolver;

public class GetStageServlet extends HttpServlet {
	
	private static final long serialVersionUID = -5674278889076295672L;
	private static final Logger LOG = LogManager.getLogger(GetStageServlet.class);
	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		
		String project = req.getParameter( "project" );
		
		HttpSession session = req.getSession();
		
		String httpSession = (String)session.getAttribute( "httpSession" );
		
		String scriptSession = (String)session.getAttribute( "scriptSession" );
		
		String personId = (String)session.getAttribute( "personId" );
		
		if (session.getAttribute( "name" ) == null)
			resp.getWriter().print( "error_session" );
		
		CloseableHttpClient client = (CloseableHttpClient)session.getAttribute( "client" );
		
		StageRequest request = new StageRequest();
		
		StageResolver resolver = new StageResolver();
		LOG.info( String.format( "[httpSession=%s][scriptSession=%s]", httpSession, scriptSession ) );
		try {
			
			Map<String, String> map = resolver.resolve( 
					request.execute( client, scriptSession, httpSession, personId, project ) );
			
			ObjectMapper mapper =  new ObjectMapper();
			
			String str = mapper.writeValueAsString(map);
			
			resp.getWriter().print( str );
			
		} catch (Exception e) {
			resp.getWriter().print( "error_internal" );
		}
	}
}
