package utils;

import java.util.Random;

public class Utils {
	
	public static String zeros(int length) {
		StringBuilder builder = new StringBuilder();
		
		for( int i=0; i<length; i++) {
			builder.append('0');
		}
		
		return builder.toString();
	}
	
	
	public static int genererInt(int borneInf, int borneSup){
		   Random random = new Random();
		   int nb;
		   nb = borneInf+random.nextInt(borneSup-borneInf);
		   return nb;
		}

}
