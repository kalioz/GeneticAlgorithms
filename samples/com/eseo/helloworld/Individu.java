package com.eseo.helloworld;

import java.util.List;
import java.util.Random;

import com.eseo.helloworld.Utils;

import fr.kalioz.geneticalgorithm.Individual;

public class Individu extends Individual {

	public static final String SEARCHED_STRING = "Hello World !";
	public static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!:;.,? ";

	private String currentString;

	// constructor
	
	public Individu() {
		this.currentString = "";
	}
	
	// accessors
	
	public char getChar(int i){
		return this.currentString.charAt(i);
	}
	
	public void setChar(int index, char c){
		this.setChars(index, ""+c);
	}
	
	public void setChars(int index, String replacement){
		if (index + replacement.length() > this.currentString.length()){
			System.out.println("error - the resulting string would be bigger than the created");
			return;
		}
		
		String swap = "";
		if (index != 0) {
			swap += currentString.substring(0, index);
		}
		swap += replacement;
		swap += currentString.substring(index+replacement.length()); 
		
		if (swap.length() != this.currentString.length()){
			System.out.println("ERREUR FATALE");
		}
		this.currentString = swap;
	}
	
	public String getCurrentString() {
		return currentString;
	}

	public void setCurrentString(String currentString) {
		this.currentString = currentString;
	}
	
	// functions

	private char randomChar() {
		Random rd = new Random();
		return ALLOWED_CHARS.charAt(rd.nextInt(ALLOWED_CHARS.length()));
	}

	@Override
	public void init() {
		StringBuilder s = new StringBuilder();
		while (s.length() < SEARCHED_STRING.length()) {
			s.append(randomChar());
		}
		this.currentString = s.toString();
	}

	@Override
	public float fitness() {
		int matching = 0;
		for (int i = 0; i < SEARCHED_STRING.length(); i++) {
			if (SEARCHED_STRING.charAt(i) == this.currentString.charAt(i)) {
				matching++;
			}
		}

		if (SEARCHED_STRING.length() == 0) {
			System.out.println("error - searched string is null");
			return 0;
		}

		return ((float)matching)/SEARCHED_STRING.length();
	}

	@Override
	public void mutate() {
		int index = Utils.randomBetween(0, this.currentString.length());
		char c = randomChar();

		this.setChar(index, c);
	}

	@Override
	public Individual[] createChildren(Individual other) {
		Individu otherIndividu = (Individu) other;

		Individu[] children = new Individu[2];
		children[0] = this.duplicate();
		children[1] = otherIndividu.duplicate();

		// crossover points
		int c1 = Utils.randomBetween(0, this.currentString.length());
		int c2 = Utils.randomBetweenExcluded(0, this.currentString.length(), c1);

		int swap = Math.max(c1, c2);
		c1 = Math.min(c1, c2);
		c2 = swap;

		// exchange the portion between c1 and c2
		children[0].setChars(c1, otherIndividu.getCurrentString().substring(c1, c2));
		children[1].setChars(c1, this.getCurrentString().substring(c1, c2));

		// verify both children are OK
		for (Individu i : children) {
			if (!i.isCorrectlyFormed()) {
				System.out.println("erreur - un individu a été mal formé");
			}
		}

		return children;
	}

	private Individu duplicate() {
		Individu dup = new Individu();
		dup.setCurrentString(currentString);
		return dup;
	}

	@Override
	public boolean isCorrectlyFormed() {
		return this.currentString.length() == SEARCHED_STRING.length();
	}

	@Override
	public boolean isFittestThan(Individual other) {
		return this.fitness() >= other.fitness();
	}

}
