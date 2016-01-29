package com.hpstool.core;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	
	/**
    *
    * @param theString
    * @return String
    */
   public static String unicodeToUtf8(String theString) {
       char aChar;
       int len = theString.length();
       StringBuffer outBuffer = new StringBuffer(len);
       for (int x = 0; x < len;) {
           aChar = theString.charAt(x++);
           if (aChar == '\\') {
               aChar = theString.charAt(x++);
               if (aChar == 'u') {
                   // Read the xxxx
                   int value = 0;
                   for (int i = 0; i < 4; i++) {
                       aChar = theString.charAt(x++);
                       switch (aChar) {
                       case '0':
                       case '1':
                       case '2':
                       case '3':
                       case '4':
                       case '5':
                       case '6':
                       case '7':
                       case '8':
                       case '9':
                           value = (value << 4) + aChar - '0';
                           break;
                       case 'a':
                       case 'b':
                       case 'c':
                       case 'd':
                       case 'e':
                       case 'f':
                           value = (value << 4) + 10 + aChar - 'a';
                           break;
                       case 'A':
                       case 'B':
                       case 'C':
                       case 'D':
                       case 'E':
                       case 'F':
                           value = (value << 4) + 10 + aChar - 'A';
                           break;
                       default:
                           throw new IllegalArgumentException(
                                   "Malformed   \\uxxxx   encoding.");
                       }
                   }
                   outBuffer.append((char) value);
               } else {
                   if (aChar == 't')
                       aChar = '\t';
                   else if (aChar == 'r')
                       aChar = '\r';
                   else if (aChar == 'n')
                       aChar = '\n';
                   else if (aChar == 'f')
                       aChar = '\f';
                   outBuffer.append(aChar);
               }
           } else
               outBuffer.append(aChar);
       }
       return outBuffer.toString();
   }
   
   public static String toDate(Date date) {
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	   return format.format( date );
   }
   
   public static Date parseDate(String date) {
	   try {
		   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		   return format.parse( date );
	   } catch (Exception e) {
		   e.printStackTrace();
		   return null;
	   }
   }
   
   public static boolean isBefore(String date1, String date2) {
	   return parseDate(date1).before( parseDate(date2) );
   }
   
   public static boolean isAfter(String date1, String date2) {
	   return parseDate(date1).after( parseDate(date2) );
   }
}
