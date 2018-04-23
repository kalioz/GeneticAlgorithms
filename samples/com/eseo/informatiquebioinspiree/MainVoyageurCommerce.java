package com.eseo.informatiquebioinspiree;

import fr.kalioz.geneticalgorithm.Laboratory;

public class MainVoyageurCommerce {
	public static void main(String[] args) throws Exception{
		long startTime = System.nanoTime();
		Laboratory<Individu> labo = new Laboratory<>(Individu.class, 1000, 30, 100);
		long t1 = System.nanoTime();
		labo.run(1);
		long t2 = System.nanoTime();
		labo.run();
		long endTime = System.nanoTime();
		System.out.println("generation : "+labo.getGeneration());
		System.out.println("fitness :"+labo.getBestFitness());
		System.out.println("Temps creation : "+(t1-startTime)/1000000);
		System.out.println("Temps 1 run : "+(t2-t1)/1000000);
		System.out.println("Temps total : "+(endTime-startTime)/1000000);
	}
}
