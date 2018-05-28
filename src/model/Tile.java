package model;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * A Tile object stores its zoom level and x and y coordinates in the Tile set at the
 * indicated zoom level. 
 * 
 * See: http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames for details on map tiles
 * and computing x and y.
 */
public class Tile {
	int z;
	int x;
	int y;

	public Tile(int zoom, int x, int y) {
		this.z=zoom;
		this.x=x;
		this.y=y;
	}

	/**
	 * Useful accessor methods
	 * 
	 * These are stubbed out - define them as simple accessor methods for the instances
	 * variables holding the zoom level and the x and y tile coordinates.
	 */
	public int getZoom() { return z; }
	public int getX() { return this.x; }
	public int getY() { return this.y; }
		
    /**
     * Given a tile, return a new URL at the link for the tile using the openstreetmap.org tile server a:
     * The prefix should be "http://a.tile.openstreetmap.org/"
     *
     * See: http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames for details on map tiles and constructing URLs
     *
     * @param tile The tile to be retrieved from the API
     * @return The URL corresponding to the requested tile
     */
    public URL getURL() {
    	String s="http://a.tile.openstreetmap.org/"+this.z+"/"+this.x+"/"+this.y+".png";
    	URL u=null;
		try {
			u = new URL(s);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return u;
    }
    

    
	/*
	 * DO NOT MODIFY METHODS BELOW THIS COMMENT
	 * 
	 * These are methods we are providing to you.
	 * 
	 * These method definitions are provided for a reason: you will need 
	 * to call them in your code.
	 */


    /**
     * Returns a Tile object for a tile containing the provided city at the provided zoom level. The
     * city contains a latitude and longitude which can be used in conjunction with the zoom level
     * to compute the x and y values for the tile containing that latitude and longitude.
     *
     * See: http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames for details on map tiles and computing x and y
     *
     * @param city The destination that must be contained in the tile
     * @param zoom The zoom level for the tile
     * @return The tile containing the destination at this zoom level
     */
    public static Tile getTile(City city, int zoom){
        double longitudeInRadians = Math.PI * city.getLongitude() / 180.0;
        double latitudeInRadians = Math.PI * city.getLatitude() / 180.0;
    
        double howManyTilesAtThisZoomLevel = Math.pow(2.0, zoom);

        double x = longitudeInRadians;
        double y = Math.log(Math.tan(latitudeInRadians) + (1.0 / Math.cos(latitudeInRadians)));

        x = (1 + (x / Math.PI)) / 2.0;
        y = (1 - (y / Math.PI)) / 2.0;

        x *= howManyTilesAtThisZoomLevel;
        y *= howManyTilesAtThisZoomLevel;
        return new Tile(zoom, (int) x, (int) y);
    }

}
