package com.eseo.informatiquebioinspiree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {
	
	private static boolean inited = false;
	
	private Utils(){
		
	}
	
	private static final List<City> cities = new ArrayList<>();
	
	public static void init(){
		if (inited){
			return;
		}
		initCities();
		inited = true;
	}
	
	public static void initCities(){
		cities.add(new City("NICE", 43.7101, 7.2619));
		cities.add(new City("CAEN", 49.1828, -0.3706));
		cities.add(new City("BREST", 48.3903, -4.4860));
		cities.add(new City("TOULOUSE", 43.6046, 1.4442));
		cities.add(new City("BORDEAUX", 44.8377, -0.5791));
		cities.add(new City("RENNES", 48.1172, -1.6777));
		cities.add(new City("GRENOBLE", 45.1885, 5.7245));
		cities.add(new City("ANGERS", 47.4711, -0.5518));
		cities.add(new City("NANTES", 47.2183, -1.5536));
		cities.add(new City("NANCY", 48.6920, 6.1844));
		cities.add(new City("METZ", 49.1193, 6.1757));
		cities.add(new City("LILLE", 50.6292, 3.0572));
		cities.add(new City("BIARRITZ", 43.4831, -1.5586));
		cities.add(new City("PERPIGNAN", 42.6886, 2.8948));
		cities.add(new City("STRASBOURG", 48.5734, 7.7521));
		cities.add(new City("LYON", 45.7640, 4.8356));
		cities.add(new City("LE MANS", 48.0061, 0.1995));
		cities.add(new City("PARIS", 48.8566, 2.3522));
		cities.add(new City("ROUEN", 49.4432, 1.0999));
		cities.add(new City("MARSEILLE", 43.2964, 5.3697));
	}
	
	//  Distances
	/**
	 * Get the distance in kms between the coordinates
	 * @param latitude1
	 * @param longitude1
	 * @param latitude2
	 * @param longitude2
	 * @return
	 */
	public static double distanceGPS(double latitude1, double longitude1, double latitude2, double longitude2){
		final int R = 6371; // Radius of the earth

	    double latDistance = Math.toRadians(latitude2 - latitude1);
	    double lonDistance = Math.toRadians(longitude2 - longitude1);
	    double a = Math.sin(latDistance / 2d) * Math.sin(latDistance / 2d)
	            + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
	            * Math.sin(lonDistance / 2d) * Math.sin(lonDistance / 2d);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    return  R * c;
	}
	
	// Villes
	public static City[] getCities(){		
		if (cities.isEmpty()){
			Utils.init();
		}
		return cities.toArray(new City[cities.size()]);
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
