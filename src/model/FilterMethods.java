package model;

import java.util.ArrayList;

/**
 * The filter methods from part 2 of the homework.
 * They are defined here as static methods, meaning other code should
 * call these methods on the FilterMethods class, as in:
 * 
 * FilterMethods.filterByCountry(_constraints.getCountry(), cities)
 *
 * Complete the filterByCity method.
 */
public class FilterMethods {
	
	
	/**
	 * Given an ArrayList<City> and the name of a city, return a new ArrayList<City>
	 * containing those City objects from the given ArrayList that have the indicated
	 * city name.
	 * 
	 * @param city - the indicated city name
	 * @param cities - the ArrayList<City> of input cities
	 * @return a new ArrayList<City> of output cities
	 */
	public static ArrayList<City> filterByCity(String city, ArrayList<City> cities){
		ArrayList<City> a=new ArrayList<City>();
		for(City c: cities) {
			if(c.getCity().equalsIgnoreCase(city)) {
				a.add(c);
			}
		}
		return a;
	}

	/**
	 * Given an ArrayList<City> and the abbreviation of a country, return a new ArrayList<City>
	 * containing those City objects from the given ArrayList that have the indicated
	 * country.
	 * 
	 * @param country - the indicated country abbreviation
	 * @param cities - the ArrayList<City> of input cities
	 * @return a new ArrayList<City> of output cities
	 */
	public static ArrayList<City> filterByCountry(String country, ArrayList<City> cities){
		ArrayList<City> accepted = new ArrayList<City>();
		for(City city: cities) {
			if(city.getCountry().equalsIgnoreCase(country)) {
				accepted.add(city);
			}
		}
		return accepted;
	}
	
	/**
	 * Given an ArrayList<City> and a region, return a new ArrayList<City>
	 * containing those City objects from the given ArrayList that have the indicated
	 * region.
	 * 
	 * @param region - the indicated region
	 * @param cities - the ArrayList<City> of input cities
	 * @return a new ArrayList<City> of output cities
	 */
	public static ArrayList<City> filterByRegion(String region, ArrayList<City> cities){
		ArrayList<City> accepted = new ArrayList<City>();
		for(City city: cities) {
			if(city.getRegion().equalsIgnoreCase(region)) {
				accepted.add(city);
			}
		}
		return accepted;
	}

	/**
	 * Given an ArrayList<City> and a population, return a new ArrayList<City>
	 * containing those City objects from the given ArrayList that have a population 
	 * of at least the indicated population.
	 * 
	 * @param minimumPopulation - the minimum population
	 * @param cities - the ArrayList<City> of input cities
	 * @return a new ArrayList<City> of output cities
	 */
	public static ArrayList<City> filterByMinimumPopulation(int minimumPopulation, ArrayList<City> cities) {
		ArrayList<City> accepted = new ArrayList<City>();
		for(City city: cities) {
			if(city.getPopulation() >= minimumPopulation) {
				accepted.add(city);
			}
		}
		return accepted;
	}
	
	/**
	 * Given an ArrayList<City> and a population, return a new ArrayList<City>
	 * containing those City objects from the given ArrayList that have a population 
	 * of at most the indicated population.
	 * 
	 * @param maximumPopulation - the maximum population
	 * @param cities - the ArrayList<City> of input cities
	 * @return a new ArrayList<City> of output cities
	 */
	public static ArrayList<City> filterByMaximumPopulation(int maximumPopulation, ArrayList<City> cities) {
		ArrayList<City> accepted = new ArrayList<City>();
		for(City city: cities) {
			if(city.getPopulation() <= maximumPopulation) {
				accepted.add(city);
			}
		}
		return accepted;
	}
	
}

