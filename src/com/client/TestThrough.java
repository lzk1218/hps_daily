package com.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class TestThrough {

	public static void main( String[] args ) throws Exception {
		CloseableHttpClient client = HttpClients.createDefault();  
		
		HttpPost post = new HttpPost("http://10.167.14.230:8080/webtest/login");
		
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add( new BasicNameValuePair("name", "root") );
		
		UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
		
		post.setEntity( uefEntity );
		
		CloseableHttpResponse response = client.execute( post );
		
		HttpEntity entity = response.getEntity();
		
		System.out.println("Response content 1: " + EntityUtils.toString(entity, "UTF-8")); 
		
		response.close();  
		
		post = new HttpPost("http://10.167.14.230:8080/webtest/validate");
		
//		post.addHeader( "Set-Cookie", "JSESSIONID=C2DD79BD2FF73145AE5C34DC014B3DDC" );
		
		response = client.execute( post );
		
		entity = response.getEntity();
		
		System.out.println("Response content 2: " + EntityUtils.toString(entity, "UTF-8")); 
		
		response.close();  
	}

}
