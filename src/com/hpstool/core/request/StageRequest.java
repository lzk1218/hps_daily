package com.hpstool.core.request;

import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

public class StageRequest {
	public String url() {
		return "http://10.167.14.206/hps/dwr/call/"
				+ "plaincall/dwrService.getStageIdForDaily.dwr";
	}
	
	public String execute(CloseableHttpClient httpc, String sessionid, 
			String cookieSessionid, String persionId, String project) throws Exception {
		
		HttpPost post = new HttpPost(url());
		
		StringBuilder sb = new StringBuilder();
		sb.append( "callCount=1\n" ).
		   append( "page=/hps/\n" ).
		   append( "httpSessionId=" ).append(cookieSessionid).append( "\n" ).
		   append( "scriptSessionId=" ).append(sessionid+"565").append( "\n" ).
		   append( "c0-scriptName=dwrService\n" ).
		   append( "c0-methodName=getStageIdForDaily\n" ).
		   append( "c0-id=0\n" ).
		   append( "c0-param0=number:").append(persionId).append("\n" ).
		   append( "c0-param1=string:" ).append( project ) .append( "\n" ).
		   append( "batchId=0\n" );
		StringEntity se = new StringEntity(sb.toString(),"UTF-8");
		
		post.setEntity( se );
		post.addHeader( "Content-Type", "text/plain" );
		
		try (CloseableHttpResponse response = httpc.execute( post )) {
			
			HttpEntity entity = response.getEntity();
			
			return EntityUtils.toString(entity);
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(URLEncoder.encode( "学习及QM维护C++", "utf-8" ));
	}
}
