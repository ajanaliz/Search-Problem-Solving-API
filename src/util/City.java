package util;

import java.awt.geom.Point2D;

/**
 * City class that represents a city by a point and a name
 */
public class City extends Point2D.Double {
	private String name;

	/**
	 * Constructs a city by point data and name
	 *
	 * @param name The city name
	 * @param x The x-coordinate
	 * @param y The y-coordinate
	 */
	public City(String name, double x, double y) {
		super(x, y);
		this.name = name;
	}

	/**
	 * Gets the city's name
	 *
	 * @return The city's name
	 */
	public String getName() {
		return name;
	}

	// Gets the distance to given city
	public double distanceTo(City city){
		int xDistance = (int) Math.abs(getX() - city.getX());
		int yDistance = (int) Math.abs(getY() - city.getY());
		double distance = Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );

		return distance;
	}

	@Override
	public String toString(){
		return getX()+", "+getY();
	}
}
