package com.eseo.informatiquebioinspiree;

import java.util.ArrayList;
import java.util.List;

import fr.kalioz.geneticalgorithm.Individual;

public class Individu extends Individual{
	public static int NUMBER_CITIES = 20;
	
	private City[] cities;

	//constructor
	public Individu(){
		this(NUMBER_CITIES);
	}
	public Individu(int n){
		setCities(new City[n]);
	}
	public Individu(City[] cities){
		this(cities.length);
		for (int i=0; i<cities.length;i++){
			this.cities[i] = cities[i];
		}
	}
	
	//accessors
	
	public City[] getCities() {
		return cities;
	}
	public void setCities(City[] cities) {
		this.cities = cities;
	}
	public City getCity(int i){
		return this.cities[i];
	}
	public void setCity(int i, City city){
		this.cities[i] = city;
	}
	
	//functions
	public Individu duplicate(){
		return new Individu(this.cities.clone());
	}
	
	@Override
	public void init() {
		this.cities = Utils.getCities();
		fr.kalioz.geneticalgorithm.Utils.shuffle(cities);
	}

	@Override
	public float fitness() {
		return (float) this.distance();
	}

	@Override
	public void mutate() {
		int i1 = Utils.randomBetween(0, cities.length);
		int i2 = Utils.randomBetweenExcluded(0, cities.length, i1);
		Utils.swap(cities, i1, i2);
	}

	private List<City> findMissingCities(City[] liste){
		List<City> output = new ArrayList<>();
		
		for (City c1:liste){
			boolean present = false;
			for (City c2:cities){
				if (c1.equals(c2)){
					present = true;
					break;
				}
			}
			if (!present){
				output.add(c1);
			}
		}
		
		return output;
	}
	
	@Override
	public Individual[] createChildren(Individual other) {
		Individu otherIndividu = (Individu) other;
		
		Individu[] children = new Individu[2];
		children[0] = this.duplicate();
		children[1] = otherIndividu.duplicate();
		
		// crossover points
		int c1 = Utils.randomBetween(0, this.cities.length);
		int c2 = Utils.randomBetweenExcluded(0, this.cities.length, c1);
		
		int swap = Math.max(c1, c2);
		c1 = Math.min(c1, c2);
		c2 = swap;
		
		// exchange the portion between c1 and c2
		for (int i=c1 ; i<=c2; i++){
			children[0].setCity(i, otherIndividu.getCity(i));
			children[1].setCity(i, this.getCity(i));
		}
		
		// delete cities already existing before and after [c1:c2]
		for (int i=0; i<this.cities.length; i++){
			if (i>=c1 && i<= c2){
				i = c2;
				continue;
			}else{
				for (int j = c1; j <= c2; j++){
					if (children[0].getCity(j).equals(children[0].getCity(i))){
						children[0].setCity(i, null);
					}
					if (children[1].getCity(j).equals(children[1].getCity(i))){
						children[1].setCity(i, null);
					}
				}
			}
		}
		
		// replace non-existing cities
		for (Individu ind:children){
			List<City> missingTemp = ind.findMissingCities(this.cities);
			City[] missing = missingTemp.toArray(new City[missingTemp.size()]);
			fr.kalioz.geneticalgorithm.Utils.shuffle(missing);
			int position=0;
			for (int i = 0; i<ind.cities.length; i++){
				if (ind.getCity(i) == null){
					if (missing.length <= position){
						System.out.println("hu ho");
					}
					ind.setCity(i, missing[position]);
					position++;
				}
			}
		}
		
		//verify both children are OK
		for (Individu i:children){
			if (! i.isCorrectlyFormed()){
				System.out.println("erreur - un individu a été mal formé");
			}
		}
		
		return children;
	}

	@Override
	public boolean isCorrectlyFormed() {
		if (this.cities == null){
			return false;
		}
		
		for (int i=0;i<cities.length;i++){
			if (cities[i] == null){
				System.out.println("city is NULL");
				return false;
			}
			for (int j=i+1;j<cities.length;j++){
				if (cities[i].equals(cities[j])){
					System.out.println("2 same cities in individu");
					return false;
				}
			}
		}
		
		return true;
	}
	

	@Override
	public boolean isFittestThan(Individual other) {
		return other.fitness() > this.fitness();
	}
	
	public double distance(){
		double d = 0;
		for (int i=0; i<cities.length-1; i++){
			d+=cities[i].distanceTo(cities[i+1]);
		}
		return d;
	}
}
