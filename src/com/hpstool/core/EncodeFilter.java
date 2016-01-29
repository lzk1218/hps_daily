package com.hpstool.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EncodeFilter implements Filter {
	
	private static final Logger LOG = LogManager.getLogger(EncodeFilter.class);
	
	private String encoding;
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )
			throws IOException, ServletException {
		
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}

	@Override
	public void init( FilterConfig cfg ) throws ServletException {
		encoding = cfg.getInitParameter( "encoding" );
		LOG.info( "EncodeFilter init [encoding=" + encoding + "]" );
	}

}
