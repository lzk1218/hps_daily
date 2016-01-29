package com.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.hpstool.core.resolver.ScriptSessionIDResolver;

public class TestDwr {

	public static void main( String[] args ) throws Exception {
		
		CloseableHttpClient httpc = HttpClients.createDefault();  
		
		HttpGet get = new HttpGet("http://10.167.14.206/hps/dwr/engine.js");
		
		CloseableHttpResponse response = httpc.execute( get );
		
		HttpEntity entity = response.getEntity();
		
		ScriptSessionIDResolver r = new ScriptSessionIDResolver();
		String sessionid = r.resolve( entity.getContent() );
		System.out.println("script=" + sessionid);
		
		HttpPost post = new HttpPost("http://10.167.14.206/hps/person.do?method=login");
		
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add( new BasicNameValuePair("loginAccount", "songkaibo") );
		formparams.add( new BasicNameValuePair("password", "sunshine") );
		
		UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
		
		post.setEntity( uefEntity );
		
		response = httpc.execute( post );
		
		entity = response.getEntity();
		
		Header[] header = response.getHeaders( "Set-Cookie" );
		String value = header[0].getValue();
		String cookieSessionid = value.substring(value.indexOf( "=" ) + 1, value.indexOf( ";" ));
		System.out.println("session=" + cookieSessionid);
		
		String httpUrl = "http://10.167.14.206/hps/dwr/call/plaincall/dwrService.newsMainPageShow.dwr";         
	   //HttpPost连接对象         
	   HttpPost httpRequest = new HttpPost(httpUrl);  
	   List<NameValuePair> params = new ArrayList<NameValuePair>();
	   params.add( new BasicNameValuePair("scriptSessionId", sessionid+ "543") );
	   params.add(new BasicNameValuePair("callCount", "1"));  
	   params.add(new BasicNameValuePair("c0-scriptName", "dwrService"));
	   params.add(new BasicNameValuePair("c0-methodName", "newsMainPageShow"));
	   params.add(new BasicNameValuePair("c0-id", "0"));
	   params.add(new BasicNameValuePair("batchId", "0"));
	   
//	   Header[] headers = {new BasicHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; " + 
//	             "Windows NT 5.1; SV1; .NET CLR 2.0.50727; CIBA)"),  
//	             new BasicHeader("Accept-Language", "zh-cn"), 
//	             new BasicHeader("Content-Type", "text/plain"),  
//	             new BasicHeader("Accept-Encoding", "gzip, deflate")}; 
	   
	   //设置字符集            
	    HttpEntity httpentity = new UrlEncodedFormEntity(params, "utf-8");             
//	    httpRequest.setHeaders(headers);
	   //请求httpRequest            
	    httpRequest.setEntity(httpentity);             
	   
//	   HttpClient httpc= HttpClients.createDefault();  
	   //取得HttpResponse            
	    HttpResponse httpResponse = httpc.execute(httpRequest);              
	      
	    //取得返回的字符串                 
	    String strResult = EntityUtils.toString(httpResponse.getEntity());
	    
//	    System.out.println(strResult);
	    
	    httpRequest = new HttpPost("http://10.167.14.206/hps/dailyReport.do?method=viewnextweek&date=2015-10-12");  
	    
	    httpRequest.addHeader( "Content-Type", 
	    		"multipart/form-data; boundary=---------------------------7df33a1a1a0424" );
	    httpRequest.addHeader( "Cookie", "JSESSIONID=" + cookieSessionid);
	    
	    StringBuilder sb = new StringBuilder();
	    sb.append( "-----------------------------7df33a1a1a0424\r\n" );
	    sb.append( "Content-Disposition: form-data; name=\"jsbutianstartdate\"\r\n\r\n" );
	    sb.append( "2015-10-12\r\n" );
	    sb.append( "-----------------------------7df33a1a1a0424\r\n" );
	    sb.append( "Content-Disposition: form-data; name=\"jsbutianenddate\"\r\n\r\n" );
	    sb.append( "2015-10-19\r\n" );
	    sb.append( "-----------------------------7df33a1a1a0424\r\n" );
	    sb.append( "Content-Disposition: form-data; name=\"querysdate\"\r\n\r\n" );
	    sb.append( "2015-10-12\r\n" );
	    sb.append( "-----------------------------7df33a1a1a0424\r\n" );
	    sb.append( "Content-Disposition: form-data; name=\"queryedate\"\r\n\r\n" );
	    sb.append( "2015-10-19\r\n" );
	    sb.append( "-----------------------------7df33a1a1a0424--\r\n" );
	    
	    StringEntity se = new StringEntity(sb.toString(),"UTF-8");
	    httpRequest.setEntity( se );
	    
	    httpResponse = httpc.execute(httpRequest);
	    
	    
//	    System.out.println(EntityUtils.toString(httpResponse.getEntity()));
	    
	    
	    httpRequest = new HttpPost("http://10.167.14.206/hps/dwr/call/plaincall/"
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
		
		httpRequest.setEntity( se );
		
		httpResponse = httpc.execute(httpRequest);
		    
		System.out.println(EntityUtils.toString(httpResponse.getEntity()));
	}

}
