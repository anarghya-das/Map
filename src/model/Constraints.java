package model;

/**
 * A Constraint object stores the values on which filtering/searching
 * takes place.
 * 
 * DO NOT MODIFY THIS FILE
 */
public class Constraints {

	public static final Constraints EMPTY_CONSTRAINTS = new Constraints("", "", "", "", "");

	private String _countryAbbr;
	private String _regionName;
	private String _cityName;
	private String _minPop;
	private String _maxPop;

	public Constraints(String t1, String t2, String t3, String t4, String t5) {
		_countryAbbr = t1;
		_regionName = t2;
		_cityName = t3;
		_minPop = t4;
		_maxPop = t5;
	}

	public String getCountry() { return _countryAbbr; }
	public String getRegion() { return _regionName; }
	public String getCity() { return _cityName; }
	public String getMinPop() { return _minPop; }
	public String getMaxPop() { return _maxPop; }

	public boolean hasCountry() { return _countryAbbr != null && _countryAbbr.length() > 0; }
	public boolean hasRegion() { return _regionName != null && _regionName.length() > 0; }
	public boolean hasCity() { return _cityName != null && _cityName.length() > 0; }
	public boolean hasMinPop() { return _minPop != null && _minPop.length() > 0; }
	public boolean hasMaxPop() { return _maxPop != null && _maxPop.length() > 0; }

	@Override public String toString() {
		return "["+getCountry()+","+getRegion()+","+getCity()+","+getMinPop()+","+getMaxPop()+"]";
	}
	
}
