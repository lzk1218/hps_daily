package com.hpstool.core.resolver;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class PersonIdResolver {
	public String resolve(String str) throws Exception {
		return resolve(new ByteArrayInputStream(str.getBytes()));
	}
	
	public String resolve(InputStream ins) throws Exception {
		Scanner scan = new Scanner(ins);
		
		String rtn = null;
		
		while(scan.hasNextLine()) {
			String line = scan.nextLine();
			
			if (line.contains( "type=\"hidden\"" ) && line.contains( "personId" )) {
				int s = line.lastIndexOf( '=' );
				int e = line.lastIndexOf( '\'' );
				rtn = line.substring( s+2, e );
				break;
			}
			
		}
		
		scan.close();
		
		return rtn;
	}
	
	public static void main(String[] args) throws Exception {
		PersonIdResolver resolver = new PersonIdResolver();
		String id = resolver.resolve( resolver.getClass().getClassLoader().
				getResourceAsStream( "personid.html" ) );
		System.out.println(id);
	}
}
