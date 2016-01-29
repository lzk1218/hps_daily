package com.baoli.test;

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

public class GuessPass {

	public static void main( String[] args ) throws Exception {
		CloseableHttpClient client = HttpClients.createDefault();  
		
		HttpPost post = new HttpPost("http://10.167.14.206/hps/person.do?method=login");
		
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add( new BasicNameValuePair("loginAccount", "lizhengkai") );
		formparams.add( new BasicNameValuePair("password", "19851218") );
		
		UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
		
		post.setEntity( uefEntity );
		
		CloseableHttpResponse response = client.execute( post );
		
		HttpEntity entity = response.getEntity();
		
		System.out.println("login content: " + EntityUtils.toString(entity, "UTF-8")); 
		
		response.close();
	}

}
