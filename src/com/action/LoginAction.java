package com.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hpstool.core.LoginServlet;

public class LoginAction extends HttpServlet {

	private static final long serialVersionUID = -6434128483294080524L;
	private static final Logger LOG = LogManager.getLogger(LoginServlet.class);
	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		doPost( req, resp );
	}

	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		req.getLocalAddr();
		LOG.info(String.format("[getLocalAddr=%s][endDate=%s]", req.getLocalAddr(), req.getLocalAddr()));
		System.out.println(req.getLocalAddr());
		if (req.getParameter( "name" ).equals( "root" )) {
			req.getSession().setAttribute("validate", true);
		}
		req.getRequestDispatcher( "/" ).forward( req, resp );
	}
	
	
}
