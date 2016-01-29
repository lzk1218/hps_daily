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
import com.hpstool.core.request.ModuleRequest;
import com.hpstool.core.resolver.ModuleResolver;

public class GetModuleServlet extends HttpServlet {

	private static final long serialVersionUID = 5960437012111164282L;
	
	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		
		String stage = req.getParameter( "stage" );
		
		HttpSession session = req.getSession();
		
		String httpSession = (String)session.getAttribute( "httpSession" );
		
		String scriptSession = (String)session.getAttribute( "scriptSession" );
		
		if (session.getAttribute( "name" ) == null)
			resp.getWriter().print( "error_session" );
		
		CloseableHttpClient client = (CloseableHttpClient)session.getAttribute( "client" );
		
		ModuleRequest request = new ModuleRequest();
		
		ModuleResolver resolver = new ModuleResolver();
		
		try {
		
			Map<String, String> map = resolver.resolve( 
					request.execute( client, scriptSession, httpSession, stage ) );
			
			ObjectMapper mapper =  new ObjectMapper();
			
			String str = mapper.writeValueAsString(map);
			
			resp.getWriter().print( str );
			
		} catch (Exception e) {
			resp.getWriter().print( "error_internal" );
		}
		
		
	}
}
