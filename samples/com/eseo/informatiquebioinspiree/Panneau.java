package com.eseo.informatiquebioinspiree;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import fr.kalioz.geneticalgorithm.Laboratory;

public class Panneau extends JPanel { 

	private static final long serialVersionUID = 1L;
	public static final int LARGEUR_PAR_DEFAUT;
	public static final int HAUTEUR_PAR_DEFAUT;
	public static final Color COULEUR_FOND_PAR_DEFAUT;
	private static final double LATITUDE_MIN;
	private static final double LONTITUDE_MIN;
	private static final double LATITUDE_MAX;
	private static final double LONGITUDE_MAX;
	
	private static final Color COLOR_DEFAULT = Color.BLACK;
	private static final Color COLOR_BEST = Color.RED;
	private static final Color COLOR_MEAN = Color.ORANGE;
	private static final Color COLOR_MUTATION = Color.GREEN;
	private static final Color COLOR_SECONDARY = Color.GRAY;

	static {
		LARGEUR_PAR_DEFAUT = 1000;
		HAUTEUR_PAR_DEFAUT = 640;
		COULEUR_FOND_PAR_DEFAUT = Color.WHITE;
		LATITUDE_MIN = 51.760989;
		LONTITUDE_MIN = -7.840743;
		LATITUDE_MAX = 41.77288;
		LONGITUDE_MAX = 13.187088;
	}
	
	private Laboratory<Individu> laboratory;

	public Panneau (Laboratory<Individu> laboratory) {
		super();
		setLaboratory(laboratory);
		this.setPreferredSize(new Dimension(LARGEUR_PAR_DEFAUT, HAUTEUR_PAR_DEFAUT));
		this.setBackground(COULEUR_FOND_PAR_DEFAUT);
	}


	public void setLaboratory (Laboratory<Individu> laboratory) {
		this.laboratory=laboratory;
		for (City c:Utils.getCities()){
			c.setAbscisse(LONTITUDE_MIN, LONGITUDE_MAX, LARGEUR_PAR_DEFAUT);
			c.setOrdonnee(LATITUDE_MIN, LATITUDE_MAX, HAUTEUR_PAR_DEFAUT);
		}
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		Font font = new Font("Arial", Font.PLAIN, 20);
		g.setFont(font);

		afficheStatsGraph(g);
		g.setColor(Color.black);
		individuPaint(g);
		afficheCurrentStats(g);
	}

	// affichage d'un individu qui est forme de 20 villes
	void individuPaint (Graphics g) {
		Font font = new Font("Arial", Font.PLAIN, 12);
		g.setFont(font);
		g.setColor(Color.black);

		int indiceX1=0;
		int indiceY1=0;
		int indiceX2=0;
		int indiceY2=0;

		Individu best = laboratory.getBest();
		
		// trace des aretes + noms + noeuds
		for (int i=0; i<best.getCities().length-1;i++) {
			indiceX1=best.getCity(i).getAbscisse();
			indiceY1=best.getCity(i).getOrdonnee();
			indiceX2=best.getCity(i+1).getAbscisse();
			indiceY2=best.getCity(i+1).getOrdonnee();
			g.drawLine(indiceX1,indiceY1,indiceX2,indiceY2);

			g.fillOval(indiceX1-2,indiceY1-2,5,5);

			indiceX1=indiceX1-best.getCity(i).getNomVille().length()*7/2;
			indiceY1=indiceY1-8;

			g.drawString(best.getCity(i).getNomVille(),indiceX1,indiceY1);

		}   
		g.fillOval(indiceX2-2,indiceY2-2,5,5);

		indiceX2=indiceX2-best.getCity(best.getCities().length-1).getNomVille().length()*7/2;
		indiceY2=indiceY2-8;

		g.drawString(best.getCity(best.getCities().length-1).getNomVille(),indiceX2,indiceY2);
	}
	
	public void afficheCurrentStats(Graphics g){
		Font font = new Font("Arial", Font.PLAIN, 12);
		g.setFont(font);
		
		int y0 = 560;
		int ecart = 16;
		
		
		//number of epoch
		g.setColor(COLOR_DEFAULT);
		g.drawString("Generation : "+laboratory.getGeneration(),10,y0);
		
		//distance
		g.setColor(COLOR_BEST);
		g.drawString("Distance : "+(int)laboratory.getBestFitness()+" kms",10,y0+ecart);
		
		//distance moyenne
		g.setColor(COLOR_MEAN);
		g.drawString("Distance Moyenne : "+(int)laboratory.getMeanFitness(),10,y0+2*ecart);
		
		//mutation actuelle
		g.setColor(COLOR_MUTATION);
		g.drawString("Mutation : "+laboratory.getCurrentMutation()+" %",10,y0+3*ecart);
		
		//population actuelle
		g.setColor(COLOR_SECONDARY);
		g.drawString("Population : "+laboratory.getPopulation().length,10,y0+4*ecart);
	}
	
	private void drawGraph(Graphics g, int x0, int y0, int x1, int y1, List<Float> liste, int min, int max){
		double ecartX = ((double)(x1 - x0)) / (double)liste.size() ;
		if (max == min){
			return;
		}
		
		if (ecartX >=1){
			for (int i = 0; i < liste.size()-1; i+= 1+ liste.size() / 1000){
				int X0 = (int) (x0 + ecartX*i);
				int Y0 = (int) (y1 - (liste.get(i) - min) * (y1-y0)/(max-min));
				int X1 = (int) (x0 + ecartX*(i+1));
				int Y1 =  (int) (y1 - (liste.get(i+1) - min) * (y1-y0)/(max-min));
				if (X0 != X1){
					g.drawLine(X0,Y0,X1,Y1);
				}
			}
		}else{
			for (int j = 0; j < (x1 - x0); j++){
				int i0 = (int)  (j*liste.size()/(x1-x0));
				int i1 = (int)  ((j+1)*liste.size()/(x1-x0));
				
				if (i1 >= liste.size()){
					break;
				}
				int X0 = (int) (x0 + ecartX*i0);
				int Y0 = (int) (y1 - ((liste.get(i0) - min) * (y1-y0))/(max-min));
				int X1 = (int) (x0 + ecartX*(i1));
				int Y1 =  (int) (y1 - ((liste.get(i1) - min) * (y1-y0))/(max-min));
				if (X0 != X1){
					g.drawLine(X0,Y0,X1,Y1);
				}
			}
		}
	}
	
	
	public void afficheStatsGraph(Graphics g){
		//Distance opti
		g.setColor(COLOR_BEST);		
		List<Float> liste = laboratory.getBestFitnessHistory();
		
		if (liste.isEmpty() || laboratory.getMeanFitnessHistory().isEmpty()){
			return;
		}
		
		int min = Collections.min(liste).intValue();
		int max = (int) Math.max(Collections.max(liste), Collections.max(laboratory.getMeanFitnessHistory()));
		
		drawGraph(g, 20, 20, 980, 200, liste, min, max);
		
		//distance moyenne
		g.setColor(COLOR_MEAN);
		liste = laboratory.getMeanFitnessHistory();
		drawGraph(g, 20, 20, 980, 200, liste, min, max);
		
		//mutation
		g.setColor(COLOR_MUTATION);
		liste = laboratory.getMutationHistory();
		drawGraph(g, 20, 220, 980, 400, liste, 0, 100);
	}
}
