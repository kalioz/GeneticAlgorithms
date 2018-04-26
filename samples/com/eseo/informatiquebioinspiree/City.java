package com.eseo.informatiquebioinspiree;

import java.util.HashMap;

public class City{

	private String nomVille;
	private double longitude;
	private double latitude;

	private Integer abscisse;
	private Integer ordonnee;

	private HashMap<City, Double> distanceCache;

	// constructor

	public City(String nomVille, double latitude, double longitude) {
		super();
		this.nomVille = nomVille;
		this.longitude = longitude;
		this.latitude = latitude;

		this.distanceCache = new HashMap<>();
	}

	// accessors

	public String getNomVille() {
		return nomVille;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public int setAbscisse(double longitude0, double longitudeMax, int width){

		double distance1 = Utils.distanceGPS(0,longitude0,0,this.longitude);
		double distance2 = Utils.distanceGPS(0,longitude0,0,longitudeMax);
		this.abscisse =  (int) ((distance1*width)/distance2);

		return this.abscisse;
	}

	public int getAbscisse(){
		return this.abscisse;
	}

	public int setOrdonnee(double latitude0, double latitudeMax, int height){

		double distance1 = Utils.distanceGPS(latitude0,0,this.latitude,0);
		double distance2 = Utils.distanceGPS(latitude0,0,latitudeMax,0);
		this.ordonnee =  (int) ((distance1*height)/distance2);

		return this.ordonnee;
	}

	public int getOrdonnee(){
		return this.ordonnee;
	}

	// functions
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((nomVille == null) ? 0 : nomVille.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		if (nomVille == null) {
			if (other.nomVille != null)
				return false;
		} else if (!nomVille.equals(other.nomVille))
			return false;
		return true;
	}

	public double distanceTo(City other){
		if (! this.distanceCache.containsKey(other)){
			this.distanceCache.put(other, Utils.distanceGPS(latitude, longitude, other.getLatitude(), other.getLongitude()));
		}
		return this.distanceCache.get(other);
	}
}
