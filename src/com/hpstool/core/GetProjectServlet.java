package com.hpstool.core;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.impl.client.CloseableHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpstool.core.request.ProjectRequest;
import com.hpstool.core.resolver.ProjectResolver;

public class GetProjectServlet extends HttpServlet {

	private static final long serialVersionUID = 5932654212055721429L;
	
	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		
		String date = req.getParameter( "date" );
		
		HttpSession session = req.getSession();
		
		String httpSession = (String)session.getAttribute( "httpSession" );
		
		String scriptSession = (String)session.getAttribute( "scriptSession" );
		
		String personId = (String)session.getAttribute( "personId" );
		
		if (session.getAttribute( "name" ) == null)
			resp.getWriter().print( "error_session" );
		
		CloseableHttpClient client = (CloseableHttpClient)session.getAttribute( "client" );
		
		ProjectRequest request = new ProjectRequest();
		
		ProjectResolver resolver = new ProjectResolver();
		
		try {
		
			Map<String, String> map = resolver.resolve( 
					request.execute( client, scriptSession, httpSession, personId, date ) );
			
			ObjectMapper mapper =  new ObjectMapper();
			
			String str = mapper.writeValueAsString(map);
			
			resp.getWriter().print( str );
			
		} catch (Exception e) {
			resp.getWriter().print( "error_internal" );
		}
	}
}
