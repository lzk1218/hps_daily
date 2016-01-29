package com.hpstool.core.request;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

public class DateRequest {
	public String url() {
		return "http://10.167.14.206/hps/dailyReport.do?method=viewlastweek&date=";
	}
	
	public String execute(CloseableHttpClient httpc, String date, String cookieSessionid) 
			throws Exception {
		
		HttpPost httpRequest = new HttpPost(url() + date);
		
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String nextWeek = format.format( new Date(format.parse( date ).getTime() + 
				7 * 24 * 60 * 60 * 1000) );
		
		httpRequest.addHeader( "Content-Type", 
	    		"multipart/form-data; boundary=---------------------------7df33a1a1a0424" );
	    httpRequest.addHeader( "Cookie", "JSESSIONID=" + cookieSessionid);
	    
	    StringBuilder sb = new StringBuilder();
	    sb.append( "-----------------------------7df33a1a1a0424\r\n" );
	    sb.append( "Content-Disposition: form-data; name=\"jsbutianstartdate\"\r\n\r\n" );
	    sb.append( date + "\r\n" );
	    sb.append( "-----------------------------7df33a1a1a0424\r\n" );
	    sb.append( "Content-Disposition: form-data; name=\"jsbutianenddate\"\r\n\r\n" );
	    sb.append( nextWeek + "\r\n" );
	    sb.append( "-----------------------------7df33a1a1a0424\r\n" );
	    sb.append( "Content-Disposition: form-data; name=\"querysdate\"\r\n\r\n" );
	    sb.append( date + "\r\n" );
	    sb.append( "-----------------------------7df33a1a1a0424\r\n" );
	    sb.append( "Content-Disposition: form-data; name=\"queryedate\"\r\n\r\n" );
	    sb.append( nextWeek + "\r\n" );
	    sb.append( "-----------------------------7df33a1a1a0424--\r\n" );
	    
	    StringEntity se = new StringEntity(sb.toString(),"UTF-8");
	    httpRequest.setEntity( se );
	    
	    try (CloseableHttpResponse response = httpc.execute( httpRequest )) {
	    
	    	HttpEntity entity = response.getEntity();
			
	    	return EntityUtils.toString(entity);
	    }
		
	}
}
