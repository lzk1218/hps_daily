package com.hpstool.core.resolver;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class DateResolver {
	public Map<String,Boolean> resolve(String str) {
		return resolve(new ByteArrayInputStream(str.getBytes()));
	}
	
	/**
	 * 已填写为true
	 * 未填写为false
	 * @param ins
	 * @return
	 */
	public Map<String,Boolean> resolve(InputStream ins) {
		Scanner scan = new Scanner(ins);
		
		boolean flag = false;
		
		Map<String,Boolean> map = new TreeMap<String,Boolean>();
		String date = null;
		while(scan.hasNextLine()) {
			String line = scan.nextLine();
			if (line.contains( "onclick=\"link" )) {
				int s = line.indexOf( '>' );
				int e = line.lastIndexOf( "</a>" );
				date = line.substring( s+1, e );
				flag = true;
			}
			if (line.contains("填写") && flag) {
				map.put( date, !line.contains( "未填写" ) );
				flag = false;
			}
		}
		
		scan.close();
		
		return map;
	}
	
	public static void main(String[] args) {
		DateResolver resolver = new DateResolver();
		Map<String,Boolean> map = resolver.resolve( resolver.getClass().getClassLoader().
				getResourceAsStream( "repairDaily.html" ) );
		
		for (Map.Entry<String,Boolean> entry : map.entrySet()) {
			System.out.println(entry);
		}
		
	}
}
