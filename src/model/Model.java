package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Model {
	private static final int MAX_ZOOM = 18;
	private static final int MIN_ZOOM = 0;

	private ArrayList<City> _allCities;

	private Tile _primaryTile;
	private City _city;
	private int _zoom=5;

	private ArrayList<Observer> _observers;
	private ArrayList<City> _searchResults;
	private Constraints _constraints;

	/**
	 * Initialize this Model object.  Read the data from the given filename.
	 * 
	 * @param filename
	 */
	public Model(String filename) {
		_observers = new ArrayList<Observer>();
		_searchResults=new ArrayList<City>();
		_allCities = readDataFromCSV(filename);
	}

	/**
	 * Define this method so that it returns an ArrayList<Tile> containing
	 * the nine Tile objects to be displayed in the map panel of the GUI.
	 * 
	 * The order of the nine tiles must be, by index in the ArrayList<Tile>:
	 * 
	 *     [0] [1] [2]
	 *     [3] [4] [5]
	 *     [6] [7] [8]
	 * 
	 * Perform appropriate wrap-around for tiles at the edges.
	 * See http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames#Zoom_levels
	 * and http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames#X_and_Y
	 * for more details.
	 * 
	 * HINT: keep track of the file in [4] as the _primaryTile.
	 * HINT: update the _primaryTile to move the map N, S, E, and W.
	 * 
	 * @return the ArrayList<Tile>
	 */
	public ArrayList<Tile> tiles() {
		ArrayList<Tile> a=new ArrayList<Tile>();
		for(int y0=-1;y0<=1;y0++) {
			for(int x0=-1;x0<=1;x0++) {
				if(_primaryTile.getZoom()==0) {
					a.add(new Tile(_primaryTile.getZoom(),_primaryTile.getX(),_primaryTile.getY()));
				}
				if(_primaryTile.getZoom()==1) {
					if(y0==-1||y0==1) {
						if(x0==1) {
						a.add(new Tile(_primaryTile.getZoom(),_primaryTile.getX()-1,_primaryTile.getY()+1));
						}
						else {
							a.add(new Tile(_primaryTile.getZoom(),_primaryTile.getX()+x0,_primaryTile.getY()+1));
						}
					}
					if(y0==0) {
						if(x0==1) {
							a.add(new Tile(_primaryTile.getZoom(),_primaryTile.getX()-1,_primaryTile.getY()));
							}
							else {
								a.add(new Tile(_primaryTile.getZoom(),_primaryTile.getX()+x0,_primaryTile.getY()));
							}
					}
				}
				if(_primaryTile.getZoom()>1){
				a.add(new Tile(_primaryTile.getZoom(),_primaryTile.getX()+x0,_primaryTile.getY()+y0));
			}
			}
		}
		_primaryTile=a.get(4);
		return a;
	}

	/**
	 * Call this method to shift the map North by one tile
	 */
	public void north() {
		int y=_primaryTile.getY()-1;
		int x=_primaryTile.getX();
//		if(y<0||y<Math.pow(2, currentZoom())) {
//			y=0;
////		}
//		System.out.println(x+","+y);
		_primaryTile= new Tile(currentZoom(),x,y);
		notifyObservers();
	}

	/**
	 * Call this method to shift the map South by one tile
	 */
	public void south() {
		int y=_primaryTile.getY()+1;
		int x=_primaryTile.getX();
		_primaryTile= new Tile(currentZoom(),x,y);
		notifyObservers();
	}

	/**
	 * Call this method to shift the map East by one tile
	 */
	public void east() {
		int y=_primaryTile.getY();
		int x=_primaryTile.getX()+1;
		_primaryTile= new Tile(currentZoom(),x,y);
		notifyObservers();
	}

	/**
	 * Call this method to shift the map West by one tile
	 */
	public void west() {
		int y=_primaryTile.getY();
		int x=_primaryTile.getX()-1;
		_primaryTile= new Tile(currentZoom(),x,y);
		notifyObservers();
	}

	/**
	 * Call this method to increase the zoom level by 1
	 */
	public void zoomIn() {
		if(_zoom>=MIN_ZOOM&&_zoom<MAX_ZOOM) {
		_zoom=_zoom+1;
		_primaryTile= Tile.getTile(getCurrentCity(), currentZoom());
		notifyObservers();
	}
	}

	/**
	 * Call this method to decrease the zoom level by 1
	 */
	public void zoomOut() {
		if(_zoom>MIN_ZOOM&&_zoom<=MAX_ZOOM) {
		_zoom=_zoom-1;
		_primaryTile= Tile.getTile(getCurrentCity(), currentZoom());
		notifyObservers();
	}
	}
	
	

	/**
	 * Apply the filters as indicated by the Constraints object.
	 * The result must be stored an an ArrayList<City> in _searchResults 
	 * The Constraints object must be stored in _constraints
	 * @param c - the Constraints object
	 */
	public void performSearch(Constraints c) { //31 cases
		_searchResults.clear();
		_constraints=c;
		boolean b=false;
//		System.out.println(_constraints);
		ArrayList<City> Country=new ArrayList<City>();
		ArrayList<City> Region=new ArrayList<City>();
		ArrayList<City> City=new ArrayList<City>();
		ArrayList<City> Min=new ArrayList<City>();

		if(c.hasCountry()) {
			b=true;
			Country=FilterMethods.filterByCountry(c.getCountry(), _allCities);
			_searchResults=FilterMethods.filterByCountry(c.getCountry(), _allCities);
//			System.out.println("country");
		}
		if(c.hasRegion()) {
			b=true;
//				System.out.println("region");
				if(Country.size()!=0) {
					Region=FilterMethods.filterByRegion(c.getRegion(), Country);
				_searchResults=FilterMethods.filterByRegion(c.getRegion(), Country);
//				System.out.println(" no ");
				}
				else if(!(c.hasCountry())) {
					Region=FilterMethods.filterByRegion(c.getRegion(), _allCities);
						_searchResults=FilterMethods.filterByRegion(c.getRegion(), _allCities);
//						System.out.println("yes");
					}
				}
		if(c.hasCity()) {
			b=true;
//				System.out.println("city");
				if(c.hasRegion()&&Region.size()==0) {
					City=new ArrayList<City>();
					_searchResults=new ArrayList<City>();
				}
				else if(Region.size()!=0) {
					City=FilterMethods.filterByCity(c.getCity(), Region);
				_searchResults=FilterMethods.filterByCity(c.getCity(), Region);
//				System.out.println("p");
				}	
				else if(Country.size()!=0) {
					City=FilterMethods.filterByCity(c.getCity(), Country);
					_searchResults=FilterMethods.filterByCity(c.getCity(), Country);
//					System.out.println("pp");
				}
				else if(!(c.hasCountry()||c.hasRegion())){
					City=FilterMethods.filterByCity(c.getCity(), _allCities);
					_searchResults=FilterMethods.filterByCity(c.getCity(), _allCities);
//					System.out.println("pppp");
				}
			}
		if(c.hasMinPop()) {
			b=true;
				System.out.println("minpop");
				if(c.hasCity()&&City.size()==0) {
					Min=new ArrayList<City>();
					_searchResults=new ArrayList<City>();
				}
				else if(c.hasRegion()&&Region.size()==0) {
					Min=new ArrayList<City>();
					_searchResults=new ArrayList<City>();
				}
				else if(c.hasCountry()&&Country.size()==0) {
					Min=new ArrayList<City>();
					_searchResults=new ArrayList<City>();
				}
				else if(City.size()!=0) {
					Min=FilterMethods.filterByMinimumPopulation(new Integer(c.getMinPop()), City);
					_searchResults=FilterMethods.filterByMinimumPopulation(new Integer(c.getMinPop()), City);
//					System.out.println(" x ");
				}
				else if(Region.size()!=0) {
					Min=FilterMethods.filterByMinimumPopulation(new Integer(c.getMinPop()), Region);
					_searchResults=FilterMethods.filterByMinimumPopulation(new Integer(c.getMinPop()), Region);
//					System.out.println(" y ");
				}
				else if(Country.size()!=0) {
					Min=FilterMethods.filterByMinimumPopulation(new Integer(c.getMinPop()), Country);
					_searchResults=FilterMethods.filterByMinimumPopulation(new Integer(c.getMinPop()), Country);
//					System.out.println(" z ");
				}
				else {
					Min=FilterMethods.filterByMinimumPopulation(new Integer(c.getMinPop()), _allCities);
					_searchResults=FilterMethods.filterByMinimumPopulation(new Integer(c.getMinPop()), _allCities);
				}
	
			}
			
		if(c.hasMaxPop()) {
			b=true;
				System.out.println("maxpop");
				if(c.hasCity()&&City.size()==0) {
					_searchResults=new ArrayList<City>();
				}
				else if(c.hasRegion()&&Region.size()==0) {
					Min=new ArrayList<City>();
					_searchResults=new ArrayList<City>();
				}
				else if(c.hasCountry()&&Country.size()==0) {
					Min=new ArrayList<City>();
					_searchResults=new ArrayList<City>();
				}
				else if(Min.size()!=0) {
					_searchResults=FilterMethods.filterByMaximumPopulation(new Integer(c.getMaxPop()), Min);
//					System.out.println(" 4 ");
				}
				else if(City.size()!=0) {
					_searchResults=FilterMethods.filterByMaximumPopulation(new Integer(c.getMaxPop()), City);
//					System.out.println(" 5 ");
				}
				else if(Region.size()!=0) {
					_searchResults=FilterMethods.filterByMaximumPopulation(new Integer(c.getMaxPop()), Region);
//					System.out.println(" 3 ");
				}
				else if(Country.size()!=0) {
					_searchResults=FilterMethods.filterByMaximumPopulation(new Integer(c.getMaxPop()), Country);
//					System.out.println("2 ");
				}
				else {
					_searchResults=FilterMethods.filterByMaximumPopulation(new Integer(c.getMaxPop()), _allCities);
				}
			}
		if(b==false) {
				_searchResults=_allCities;
//				System.out.println("t");
			}
		notifyObservers();
	}
	/*
	 * DO NOT MODIFY METHODS BELOW THIS COMMENT
	 * 
	 * These are methods we are providing to you.  Some of these you defined
	 * in an earlier part of the homework.
	 * 
	 * These method definitions are provided for a reason: you will need 
	 * to call them in your code.
	 */


	/**
	 * Reads the data from the indicated filename.
	 * 
	 * @param filename
	 * @return An ArrayList<City> of the city data from the file
	 * named filename.
	 */
	public ArrayList<City> readDataFromCSV(String filename){
		ArrayList<City> cities = new ArrayList<City>();
		try {
			List<String> lines = Files.readAllLines(Paths.get(filename));
			lines.remove(0);
			for(String line : lines) {
				String [] info = line.split(",");
				City city = new City(info[0], info[1],info[3],info[4],info[5],info[6]);
				cities.add(city);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return cities;
	}

	/**
	 * Used to add an observer to this model object.
	 * All observers must be notified whenever the internal state of the model
	 * object changes.
	 * 
	 * @param obs - the observer to be added to the list of observers.
	 */
	public void addObserver(Observer obs) {
		_observers.add(obs);
	}

	/**
	 * Used to notify all observers that the internal state of the model has
	 * changed.  Notification is via a call to each observer's update method.
	 */
	public void notifyObservers() {
		for (Observer obs : _observers) {
			obs.update();
		}
	}	

	/**
	 * Used to set the current City object.
	 * Setting the current City also gets the tile for the current City
	 * and remembers it in _primaryTile.
	 * 
	 * @param c - the (new) current City
	 */
	public void setCity(City c) {
		_city = c;
		_primaryTile = Tile.getTile(_city, currentZoom());
		notifyObservers();
	}
	
	/**
	 * Indicates whether there is a current City
	 * @return true if the current City is set, false otherwise
	 */
	public boolean hasCity() {
		return _city != null;
	}

	/**
	 * Useful accessor methods
	 */
	public City getCurrentCity() { return _city; }
	public int currentZoom() { return _zoom; }
	public String currentCity() { return _city.getCity(); }
	public String currentRegion() { return _city.getRegion(); }
	public String currentCountry() { return _city.getCountry(); }
	public int currentPopulation() { return _city.getPopulation(); }
	public ArrayList<City> getSearchResults() { return _searchResults; }
	public Constraints getConstraints() { return _constraints; }

}
