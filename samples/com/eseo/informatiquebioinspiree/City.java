package com.eseo.informatiquebioinspiree;

import java.util.HashMap;

public class City{

	private String nomVille;
	private double longitude;
	private double latitude;
	
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
