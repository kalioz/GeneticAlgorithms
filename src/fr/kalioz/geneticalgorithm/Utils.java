package fr.kalioz.geneticalgorithm;

import java.util.Random;

public class Utils {
	
	private Utils(){
	}
	
	public static void swap(Object[] array, int id0, int id1){
		Object swap = array[id0];
		array[id0] = array[id1];
		array[id1] = swap;
	}
	
	public static void shuffle(Object[] array){
		//shuffle using Fisher-Yates method O(n)
		Random r = new Random();
		for (int i=array.length-1; i>0; i--){
			int id = r.nextInt(i);
			swap(array, id, i);
		}
	}
	
	public static boolean randomMutate(double mutation){
		Random r = new Random();
		return r.nextInt(100)<mutation;
	}
	
}
