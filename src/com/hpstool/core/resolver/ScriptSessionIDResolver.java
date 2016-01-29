package com.hpstool.core.resolver;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class ScriptSessionIDResolver {
	
	public String resolve(String str) {
		
		return resolve(new ByteArrayInputStream(str.getBytes()));
		
	}
	
	public String resolve(InputStream ins) {
		Scanner scan = new Scanner(ins);
		
		String rtn = null;
		
		while(scan.hasNextLine()) {
			String line = scan.nextLine();
			if (line.startsWith( pattern() )) {
				int s = line.indexOf( '"' );
				int e = line.lastIndexOf( '"' );
				rtn = line.substring( s+1, e );
				break;
			}
		}
		
		scan.close();
		
		return rtn;
	}
	
	private String pattern() {
		return "dwr.engine._origScriptSessionId";
	}
	
	public static void main(String[] args) {
		ScriptSessionIDResolver resolver = new ScriptSessionIDResolver();
		String session = resolver.resolve( resolver.getClass().getClassLoader().
				getResourceAsStream( "engine.js" ) );
		System.out.println(session);
	}
}
