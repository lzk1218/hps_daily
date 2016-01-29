package com.hpstool.core.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class LoginRequest {
	
	public String url() {
		return "http://10.167.14.206/hps/person.do?method=login";
	}
	
	public String[] params() {
		return new String[]{"loginAccount",
				"password"};
	}
	
	public String execute(CloseableHttpClient httpc, String... values) throws Exception {
		
		HttpPost post = new HttpPost(url());
		
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		
		for (int i=0; i < Math.min( values.length, params().length ); i++) {
			formparams.add( new BasicNameValuePair(params()[i], values[i] ));
		}
		
		UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
		
		post.setEntity( uefEntity );
		
		try (CloseableHttpResponse response = httpc.execute( post )) {
		
			Header[] header = response.getHeaders( "Set-Cookie" );
			if (header == null || header[0] == null)
				return null;
			String value = header[0].getValue();
			String cookieSessionid = value.substring(value.indexOf( "=" ) + 1, value.indexOf( ";" ));
			
			return cookieSessionid;
		}
	}
}

