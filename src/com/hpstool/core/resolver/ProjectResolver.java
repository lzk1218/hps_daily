package com.hpstool.core.resolver;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hpstool.core.Util;

public class ProjectResolver {
	
	public Map<String,String> resolve(String str) throws Exception {
		return resolve(new ByteArrayInputStream(str.getBytes()));
	}
	
	public Map<String,String> resolve(InputStream ins) throws Exception {
		Scanner scan = new Scanner(ins);
		
		Pattern p = Pattern.compile( "s(\\d+)\\[(\\d+)\\]=\"(.*)\"" );
		
		Map<String,String> map = new TreeMap<String,String>();
		
		while(scan.hasNextLine()) {
			String line = scan.nextLine();
			
			String key = null;
			
			String value = null;
			
			
			for (String s : line.split( ";" )) {
				Matcher matcher = p.matcher( s );
				
				if (matcher.find()) {
					if (key == null) {
						key = matcher.group( 3 );
					} else {
						value = matcher.group( 3 );
						map.put( key, Util.unicodeToUtf8(value) );
					}
					if (value != null)
						break;
				}
			}
			
		}
		
		scan.close();
		
		return map;
	}
	
	public static void main(String[] args) throws Exception {
		ProjectResolver resolver = new ProjectResolver();
		Map<String,String> map = resolver.resolve( resolver.getClass().getClassLoader().
				getResourceAsStream( "project.txt" ) );
		
		for (Map.Entry<String,String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
			System.out.println(entry.getValue());
		}
		
		String str = "var s0=[];var s1=[];var s2=[];var s3=[];"
				+ "var s4=[];var s5=[];s0[0]=\"4869\";s0[1]=\"\u7F16\u7801\";";
		for (String s : str.split( ";" )) {
			Pattern p = Pattern.compile( "s(\\d+)\\[(\\d+)\\]=\"(.*)\"" );
			
			Matcher matcher = p.matcher( s );
			
			if (matcher.find()) {
			
				System.out.println(matcher.group(1));
				System.out.println(matcher.group(2));
				System.out.println(matcher.group(3));
			}
		}
		
		
	}
}
