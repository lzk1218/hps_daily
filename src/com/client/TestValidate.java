package com.client;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestValidate {

	
	public static void main( String[] args ) throws Exception {
		String startDateStr = "2016-01-26";
		String endDateStr =  "2016-01-30";
		
		SimpleDateFormat yyyymmddFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = yyyymmddFormat.parse(startDateStr);
		Date endDate = yyyymmddFormat.parse(endDateStr);
		Calendar startCalendar =Calendar.getInstance();
		startCalendar.setTime(startDate);
		Calendar endCalendar =Calendar.getInstance();
		endCalendar.setTime(endDate);
		System.out.println(startCalendar.compareTo(endCalendar));
		
//		CloseableHttpClient client = HttpClients.createDefault();  
		
//		HttpPost post = new HttpPost("http://10.167.14.230:8080/webtest/validate");
//		
//		post.addHeader( "Cookie", "JSESSIONID=62A353E5D860B1627F7E03B26B074D7A" );
//		
//		CloseableHttpResponse response = client.execute( post );
//		
//		HttpEntity entity = response.getEntity();
//		
//		System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8")); 
//		
//		response.close();  
//		
//		client.close();
	}

}
