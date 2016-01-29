package com.hpstool.core.resolver;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import com.hpstool.core.Util;

public class DailyResolver {
	public boolean resolve(String str) throws Exception {
		return resolve(new ByteArrayInputStream(str.getBytes()));
	}
	
	public boolean resolve(InputStream ins) throws Exception {
		try (Scanner scan = new Scanner(ins)) {
		
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				if (line.contains( "dwr.engine._remoteHandleCallback" )) {
					int s = line.lastIndexOf( ',' );
					int e = line.lastIndexOf( '"' );
					String result = line.substring( s+2, e );
					result = Util.unicodeToUtf8(result);
					if (result.equals( "保存日报成功" )) {
						return true;
					} else {
						System.out.println(result);
					}
					break;
				}
			}
			
			return false;
		}
	}
	
	public static void main(String[] args) throws Exception {
		DailyResolver resolver = new DailyResolver();
		boolean s = resolver.resolve( resolver.getClass().getClassLoader().
				getResourceAsStream( "Daily.txt" ) );
		System.out.println("isOK=" + s);
		
		String str = new String( new String("\u4FDD\u5B58\u65E5\u62A5\u6210\u529F").getBytes("UTF-8"), 
				"UTF-8");
		System.out.println(str);
	}
}
