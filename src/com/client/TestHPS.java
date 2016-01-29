package com.client;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.hpstool.core.resolver.ScriptSessionIDResolver;

public class TestHPS {

	public static void main( String[] args ) throws Exception {
		CloseableHttpClient client = HttpClients.createDefault();  
		
		
		
		HttpGet get = new HttpGet("http://10.167.14.206/hps/dwr/engine.js");
		
		CloseableHttpResponse response = client.execute( get );
		
		HttpEntity entity = response.getEntity();
		
		ScriptSessionIDResolver r = new ScriptSessionIDResolver();
		String sessionid = r.resolve( entity.getContent() );
		System.out.println(sessionid);
		
//		System.out.println("Response content 1: " + EntityUtils.toString(entity, "UTF-8")); 
		
		response.close();  
		
		HttpPost post = new HttpPost("http://10.167.14.206/hps/person.do?method=login");
		
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add( new BasicNameValuePair("loginAccount", "songkaibo") );
		formparams.add( new BasicNameValuePair("password", "sunshine") );
		
		UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
		
		post.setEntity( uefEntity );
		
		response = client.execute( post );
		
		entity = response.getEntity();
		
		Header[] header = response.getHeaders( "Set-Cookie" );
		String value = header[0].getValue();
		String cookieSessionid = value.substring(value.indexOf( "=" ) + 1, value.indexOf( ";" ));
		System.out.println(cookieSessionid);
		
//		System.out.println("login content 1: " + EntityUtils.toString(entity, "UTF-8")); 
		
//		get = new HttpGet("http://10.167.14.206/hps/dailyReport.do?method=writeRenewDailyReport");
//		
//		response = client.execute( get );
//		
//		Thread.sleep( 2000 );
		
//		entity = response.getEntity();
		
//		System.out.println("writeRenewDailyReport: " + EntityUtils.toString(entity, "UTF-8")); 
		
//		get = new HttpGet("http://10.167.14.206/hps/dailyReport.do?method=viewlastweek&date=2015-10-12");
		
//		post = new HttpPost("http://10.167.14.206/hps/dwr/call/plaincall/dwrService.getProjectByPersonId.dwr");
//		
//		formparams = new ArrayList<NameValuePair>();
//		
//		formparams.add( new BasicNameValuePair("callCount", "1") );
//		
//		formparams.add( new BasicNameValuePair("httpSessionId", cookieSessionid) );
//		
//		formparams.add( new BasicNameValuePair("scriptSessionId", sessionid) );
//		
//		formparams.add( new BasicNameValuePair("c0-scriptName", "dwrService") );
//		formparams.add( new BasicNameValuePair("c0-methodName", "getProjectByPersonId") );
//		formparams.add( new BasicNameValuePair("c0-id", "0") );
//		formparams.add( new BasicNameValuePair("c0-param0", "number:344") );
//		formparams.add( new BasicNameValuePair("c0-param1", "string:2015-10-12") );
//		formparams.add( new BasicNameValuePair("batchId", "0") );
//		
//		uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
//		post.setEntity( uefEntity );
//		
//		response = client.execute( post );
//		
//		entity = response.getEntity();
//		
//		System.out.println("getProjectByPersonId: " + entity); 
		
		post = new HttpPost("http://10.167.14.206/hps/dwr/call/plaincall/dwrService.newsMainPageShow.dwr");
		
		StringBuilder sb = new StringBuilder();
		sb.append( "callCount=1\n" ).
		   append( "page=/hps/\n" ).
		   append( "httpSessionId=" ).append(cookieSessionid).append( "\n" ).
		   append( "scriptSessionId=" ).append(sessionid+"565").append( "\n" ).
		   append( "c0-scriptName=dwrService\n" ).
		   append( "c0-methodName=newsMainPageShow\n" ).
		   append( "c0-id=0\n" ).
		   append( "batchId=0\n" );
		StringEntity se = new StringEntity(sb.toString(),"UTF-8");
//		se.setChunked( true );
		
		post.setEntity( se );
		
//		post.addHeader( "Host", "10.167.14.206" );
//		post.addHeader( "Cookie", "JSESSIONID=" + cookieSessionid);
//		post.addHeader( "User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0)" );
//		post.addHeader( "Accept-Encoding", "gzip, deflate" );
//		post.addHeader( "Referer", "http://10.167.14.206/hps/" );
//		post.addHeader( "Content-Type", "text/plain" );
		
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//	    params.add( new BasicNameValuePair("scriptSessionId", sessionid+ "432") );
//	    params.add( new BasicNameValuePair("httpSessionId", cookieSessionid) );
//	    params.add(new BasicNameValuePair("callCount", "1"));  
//	    params.add(new BasicNameValuePair("c0-scriptName", "dwrService"));
//	    params.add(new BasicNameValuePair("c0-methodName", "newsMainPageShow"));
//	    params.add(new BasicNameValuePair("c0-id", "0"));
//	    params.add(new BasicNameValuePair("batchId", "0"));
//		
//	    HttpEntity httpentity = new UrlEncodedFormEntity(params, "utf-8");
//		
//	    post.setEntity( httpentity );
	    post.addHeader( "Content-Type", "text/plain" );
	    
		for (Header head : post.getAllHeaders() ) {
			System.out.println(head);
		}
		
		System.out.println(post.getEntity());
		
		response = client.execute( post );
		
		entity = response.getEntity();
		
		response.close();
		
//		System.out.println("newsMainPageShow: " + EntityUtils.toString(entity)); 
		
		
		post = new HttpPost("http://10.167.14.206/hps/dwr/call/plaincall/"
	    		+ "dwrService.getProjectByPersonId.dwr");
	    
	    sb = new StringBuilder();
		sb.append( "callCount=1\n" ).
		   append( "page=/hps/\n" ).
		   append( "httpSessionId=" ).append(cookieSessionid).append( "\n" ).
		   append( "scriptSessionId=" ).append(sessionid+"565").append( "\n" ).
		   append( "c0-scriptName=dwrService\n" ).
		   append( "c0-methodName=getProjectByPersonId\n" ).
		   append( "c0-id=0\n" ).
		   append( "c0-param0=number:344\n" ).
		   append( "c0-param1=string:2015-10-07\n" ).
		   append( "batchId=0\n" );
		se = new StringEntity(sb.toString(),"UTF-8");
		
		post.setEntity( se );
		post.addHeader( "Content-Type", "text/plain" );
		
		response = client.execute(post);
		
		response.close();
		
//		System.out.println(EntityUtils.toString(response.getEntity()));
		
		post = new HttpPost("http://10.167.14.206/hps/dwr/call/"
				+ "plaincall/dwrService.getStageIdForDaily.dwr");
	    
	    sb = new StringBuilder();
		sb.append( "callCount=1\n" ).
		   append( "page=/hps/\n" ).
		   append( "httpSessionId=" ).append(cookieSessionid).append( "\n" ).
		   append( "scriptSessionId=" ).append(sessionid+"565").append( "\n" ).
		   append( "c0-scriptName=dwrService\n" ).
		   append( "c0-methodName=getStageIdForDaily\n" ).
		   append( "c0-id=0\n" ).
		   append( "c0-param0=number:344\n" ).
		   append( "c0-param1=string:P618\n" ).
		   append( "batchId=1\n" );
		se = new StringEntity(sb.toString(),"UTF-8");
		
		post.setEntity( se );
		post.addHeader( "Content-Type", "text/plain" );
		
		response = client.execute(post);
		
//		System.out.println(URLDecoder.decode( EntityUtils.toString(response.getEntity()), "UTF-8" ));
		
//		System.out.println(new String(EntityUtils.toString(response.getEntity()).getBytes("utf-8"), "utf-8"));
		
		response.close();
		
//		post = new HttpPost("http://10.167.14.206/hps/dwr/call/"
//				+ "plaincall/dwrService.saveDailyReportSubitemAndsendEmail.dwr");
//		
//		sb = new StringBuilder();
//		sb.append( "callCount=1\n" ).
//		   append( "page=/hps/\n" ).
//		   append( "httpSessionId=" ).append(cookieSessionid).append( "\n" ).
//		   append( "scriptSessionId=" ).append(sessionid+"565").append( "\n" ).
//		   append( "c0-scriptName=dwrService\n" ).
//		   append( "c0-methodName=saveDailyReportSubitemAndsendEmail\n" ).
//		   append( "c0-id=0\n" ).
//		   append( "c0-e2=string:08:30\n" ).
//		   append( "c0-e3=string:11:30\n" ).
//		   append( "c0-e4=string:P618\n" ).
//		   append( "c0-e5=string:4872\n" ).
//		   append( "c0-e6=string:3375\n" ).
//		   append( "c0-e7=string:4\n" ).
//		   append( "c0-e8=string:完了\n" ).
//		   append( "c0-e1=Array:[reference:c0-e2,reference:c0-e3,reference:c0-e4,reference:c0-e5,reference:c0-e6,reference:c0-e7,reference:c0-e8]\n" ).
//		   append( "c0-e10=string:12:30\n" ).
//		   append( "c0-e11=string:17:00\n" ).
//		   append( "c0-e12=string:P618\n" ).
//		   append( "c0-e13=string:4872\n" ).
//		   append( "c0-e14=string:3375\n" ).
//		   append( "c0-e15=string:4\n" ).
//		   append( "c0-e16=string:完了\n" ).
//		   append( "c0-e9=Array:[reference:c0-e10,reference:c0-e11,reference:c0-e12,reference:c0-e13,reference:c0-e14,reference:c0-e15,reference:c0-e16]\n" ).
//		   append( "c0-param0=Array:[reference:c0-e1,reference:c0-e9]\n" ).
//		   append( "c0-param1=number:344\n" ).
//		   append( "c0-param2=string:\n" ).
//		   append( "c0-param3=string:\n" ).
//		   append( "c0-param4=string:2015-10-09\n" ).
//		   append( "c0-param5=boolean:false\n" ).
//		   append( "batchId=1\n" );
//		se = new StringEntity(sb.toString(),"UTF-8");
//		
//		post.setEntity( se );
//		post.addHeader( "Content-Type", "text/plain" );
//		
//		response = client.execute(post);
//		
//		System.out.println(EntityUtils.toString(response.getEntity()));
	}

}
