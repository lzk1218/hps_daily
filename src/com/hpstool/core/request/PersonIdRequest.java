package com.hpstool.core.request;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

public class PersonIdRequest {
	public String url() {
		return "http://10.167.14.206/hps/dailyReport.do?method=writeDailyReport&date=";
	}
	
	
	public String execute(CloseableHttpClient httpc, String date, String cookieSessionid) 
			throws Exception {
		
		HttpGet httpRequest = new HttpGet(url() + date);
		
		try (CloseableHttpResponse response = httpc.execute( httpRequest )) {
			
			HttpEntity entity = response.getEntity();
			
			return EntityUtils.toString(entity);
		}
	}
}
