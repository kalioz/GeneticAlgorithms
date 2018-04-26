package com.eseo.informatiquebioinspiree;

import fr.kalioz.geneticalgorithm.Laboratory;

public class MainVoyageurCommerce {
	public static void main(String[] args) throws Exception{
		Laboratory<Individu> labo = new Laboratory<>(Individu.class, 10000, 30, 50);
		Fenetre fen = new Fenetre(labo);

		fen.repaint();
		while (! labo.shouldStop()){
			labo.nextGeneration();
			fen.getPanneau().repaint();
		}
		System.out.println("calculations completed");
	}
}
