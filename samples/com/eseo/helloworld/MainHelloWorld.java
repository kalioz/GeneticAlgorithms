package com.eseo.helloworld;

import fr.kalioz.geneticalgorithm.Laboratory;

public class MainHelloWorld {
	public static void main(String[] args) throws Exception{
		Laboratory<Individu> labo = new Laboratory<>(Individu.class, 10000, 30, 100);
		Individu best;
		do{
			labo.nextGeneration();
			best = labo.getBest();
			System.out.println("Generation "+labo.getGeneration()+" - result: "+best.getCurrentString()+" (fitness score : "+best.fitness()+")" + " [mean fitness : "+labo.getMeanFitness()+"]");
		}while(best.fitness() !=1);//use labo.shouldStop only when best's fitness is not known AND should be finite
		
		System.out.println("calculations completed; generation reached : "+labo.getGeneration());
		System.out.println("Searched string : "+labo.getBest().getCurrentString());
	}
}
