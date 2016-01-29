package com.hpstool.core;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.impl.client.CloseableHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpstool.core.request.DateRequest;
import com.hpstool.core.resolver.DateResolver;

public class GetDateServlet extends HttpServlet {

	private static final long serialVersionUID = -3712586856535994877L;
	
	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		
		String start = req.getParameter( "startDate" );
		String end = req.getParameter( "endDate" );
		
		HttpSession session = req.getSession();
		
		if (session.getAttribute( "name" ) == null) {
			resp.getWriter().print( "error_session" );
			return;
		}
		
		CloseableHttpClient client = (CloseableHttpClient)session.getAttribute( "client" );
		
		String httpSession = (String)session.getAttribute( "httpSession" );
		
		DateRequest request = new DateRequest();
		
		Date startDate = Util.parseDate( start );
		
		Date endDate = Util.parseDate( end );
		
		if (startDate == null || endDate == null || startDate.after( endDate )) {
			resp.getWriter().print( "error_params." );
			return;
		}
		
		Date lastWeek = new Date(endDate.getTime() + 
				7 * 24 * 60 * 60 * 1000);
		
		DateResolver resolver = new DateResolver();
		
		Map<String, Boolean> map = new TreeMap<String, Boolean>();
		
		do {
			try {
				Map<String, Boolean> innerMap = resolver.resolve( 
						request.execute( client, Util.toDate( lastWeek ), httpSession ) );
				
				for (Map.Entry<String, Boolean> entry :  innerMap.entrySet() ){
					
					if (!Util.isBefore( entry.getKey(), start ) && !Util.isAfter( entry.getKey(), end )) {
						map.put( entry.getKey(), entry.getValue() );
					}
					
				}
			} catch (Exception e) {
				resp.getWriter().print( "error_internal" );
				return ;
			}
			lastWeek = new Date(lastWeek.getTime() - 
					7 * 24 * 60 * 60 * 1000);
		} while (lastWeek.after( startDate ));
		
		ObjectMapper mapper =  new ObjectMapper();
		
		String str = mapper.writeValueAsString(map);
		
		resp.getWriter().print( str );
	}

}
