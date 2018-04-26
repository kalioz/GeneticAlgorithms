package com.eseo.helloworld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {
	
	private static boolean inited = false;
	
	private Utils(){
		
	}
	
	public static void init(){
		if (inited){
			return;
		}
		inited = true;
	}
	
	
	
	//random
	/**
	 * return an int included in [a,b[
	 * @param a
	 * @param b
	 * @return
	 */
	public static int randomBetween(int a, int b){
		Random r = new Random();
		return a+r.nextInt(b-a);
	}
	
	public static int randomBetweenExcluded(int a, int b, int excluded){
		int output;
		do{
			output = randomBetween(a,b);
		}while(output==excluded);
		
		return output;
	}
	
	//utils
	public static void swap(Object[] array, int id0, int id1){
		Object swap = array[id0];
		array[id0] = array[id1];
		array[id1] = swap;
	}
	
}
