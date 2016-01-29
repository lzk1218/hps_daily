package com.hpstool.core.request;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

public class EngineRequest {
	public String url() {
		return "http://10.167.14.206/hps/dwr/engine.js";
	}
	
	public String execute(CloseableHttpClient httpc) throws Exception {
		
		HttpGet get = new HttpGet(url());
		
		try (CloseableHttpResponse response = httpc.execute( get )) {
		
			HttpEntity entity = response.getEntity();
			
			return EntityUtils.toString(entity);
		}
	}
}
