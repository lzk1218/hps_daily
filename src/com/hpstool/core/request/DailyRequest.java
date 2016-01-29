package com.hpstool.core.request;

import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

public class DailyRequest {
	public String url() {
		return "http://10.167.14.206/hps/dwr/call/"
				+ "plaincall/dwrService.saveDailyReportSubitemAndsendEmail.dwr";
	}
	
	public String execute(CloseableHttpClient httpc, String sessionid, 
			String cookieSessionid, String project, String stage, 
				String module, String activity, String content, 
					String personID, String date) throws Exception {
		
		HttpPost post = new HttpPost(url());
		
		StringBuilder sb = new StringBuilder();
		sb.append( "callCount=1\n" ).
		   append( "page=/hps/\n" ).
		   append( "httpSessionId=" ).append(cookieSessionid).append( "\n" ).
		   append( "scriptSessionId=" ).append(sessionid+"565").append( "\n" ).
		   append( "c0-scriptName=dwrService\n" ).
		   append( "c0-methodName=saveDailyReportSubitemAndsendEmail\n" ).
		   append( "c0-id=0\n" ).
		   append( "c0-e2=string:08:30\n" ).
		   append( "c0-e3=string:11:30\n" ).
		   append( "c0-e4=string:" ).append(project).append("\n" ).
		   append( "c0-e5=string:" ).append(stage).append("\n" ).
		   append( "c0-e6=string:" ).append(module).append("\n" ).
		   append( "c0-e7=string:" ).append(activity).append("\n" ).
		   append( "c0-e8=string:" ).append(URLEncoder.encode(content, "utf-8")).append("\n" ).
		   append( "c0-e1=Array:[reference:c0-e2,reference:c0-e3,reference:c0-e4,reference:c0-e5,reference:c0-e6,reference:c0-e7,reference:c0-e8]\n" ).
		   append( "c0-e10=string:12:30\n" ).
		   append( "c0-e11=string:17:00\n" ).
		   append( "c0-e12=string:" ).append(project).append("\n" ).
		   append( "c0-e13=string:" ).append(stage).append("\n" ).
		   append( "c0-e14=string:" ).append(module).append("\n" ).
		   append( "c0-e15=string:" ).append(activity).append("\n" ).
		   append( "c0-e16=string:" ).append(URLEncoder.encode(content, "utf-8")).append("\n" ).
		   append( "c0-e9=Array:[reference:c0-e10,reference:c0-e11,reference:c0-e12,reference:c0-e13,reference:c0-e14,reference:c0-e15,reference:c0-e16]\n" ).
		   append( "c0-param0=Array:[reference:c0-e1,reference:c0-e9]\n" ).
		   append( "c0-param1=number:" ).append(personID).append("\n" ).
		   append( "c0-param2=string:\n" ).
		   append( "c0-param3=string:\n" ).
		   append( "c0-param4=string:" ).append(date).append("\n" ).
		   append( "c0-param5=boolean:false\n" ).
		   append( "batchId=1\n" );
		StringEntity se = new StringEntity(sb.toString(),"UTF-8");
		
		post.setEntity( se );
		post.addHeader( "Content-Type", "text/plain" );
		
		try (CloseableHttpResponse response = httpc.execute( post )) {
			
			HttpEntity entity = response.getEntity();
			
			return EntityUtils.toString(entity);
		}
	}
}
