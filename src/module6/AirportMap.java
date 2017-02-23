package module6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.geo.Location;
import parsing.ParseFeed;
import processing.core.PApplet;

/** An applet that shows airports (and routes)
 * on a world map.
 * @author Callie Brooks
 * MOOC team
 *
 */
public class AirportMap extends PApplet {
	
	UnfoldingMap map;
	private List<Marker> airportList;
	List<Marker> routeList;
	List<Marker> countryList;
	
	public void setup() {
		// setting up PApplet
		size(1000,800, OPENGL);
		
		// setting up map and default events
		map = new UnfoldingMap(this, 50, 50, 750, 550);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// get features from airport data
		List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");
		
		// list for markers, hashmap for quicker access when matching with routes
		airportList = new ArrayList<Marker>();
		HashMap<Integer, Location> airports = new HashMap<Integer, Location>();
		
		// create markers from features
		for(PointFeature feature : features) {
			AirportMarker m = new AirportMarker(feature);

			m.setRadius(5);
			airportList.add(m);

			// put airport in hashmap with OpenFlights unique id for key
			airports.put(Integer.parseInt(feature.getId()), feature.getLocation());

		}
		
		// parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeList = new ArrayList<Marker>();
		for(ShapeFeature route : routes) {
			
			// get source and destination airportIds
			int source = Integer.parseInt((String)route.getProperty("source"));
			int dest = Integer.parseInt((String)route.getProperty("destination"));
			
			// get locations for airports on route
			if(airports.containsKey(source) && airports.containsKey(dest)) {
				route.addLocation(airports.get(source));
				route.addLocation(airports.get(dest));
			}
			
			SimpleLinesMarker sl = new SimpleLinesMarker(route.getLocations(), route.getProperties());
		
			//System.out.println(sl.getProperties());
			
			//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
			//routeList.add(sl);
		}
		
		
		
		//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		//map.addMarkers(routeList);
		
		map.addMarkers(airportList);
		//map.addMarkers(countryList);
		
	}
	
	public void draw() {
		background(0);
		map.draw();
		addTitle();
		addFranceButton();
		addSeychellesButton();
		addRomaniaButton();
		addAllButton();
		addAltOver1000Button();
		addAltOver2000Button();
		addAltOver3000Button();
	}

	//adds all airports back to map
	public void addAllButton() {
		int bckgrnd = color(113, 69, 69);
		fill(bckgrnd);
		rect(50,700,50,25);
		textSize(15);
		text("Reset", 55,740);
	}

	public void addTitle() {
		int txt = color(228,232,235);
		fill(txt);
		textSize(25);
		text("Airport Map", 825,50);
	}

	public void addFranceButton() {
		int bckgrnd = color(49, 58, 75);
		fill(bckgrnd);
		rect(50,650,50,25);
		textSize(15);
		text("France", 53,695);
	}

	public void addSeychellesButton() {
		int bckgrnd = color(99, 109, 127);
		fill(bckgrnd);
		rect(110,650,50,25);
		textSize(15);
		text("Seychelles", 115,695);
	}

	public void addRomaniaButton() {
		int bckgrnd = color(135, 142, 156);
		fill(bckgrnd);
		rect(200,650,50,25);
		textSize(15);
		text("Romania", 200,695);
	}

	public void addAltOver1000Button() {
		int bckgrnd = color(120,176,186);
		fill(bckgrnd);
		rect(400,650,50,25);
		textSize(15);
		text("Altitude over 1000", 400,695);
	}

	public void addAltOver2000Button() {
		int bckgrnd = color(72,140,152);
		fill(bckgrnd);
		rect(400,700,50,25);
		textSize(15);
		text("Altitude over 2000", 400,740);
	}

	public void addAltOver3000Button() {
		int bckgrnd = color(25,97,110);
		fill(bckgrnd);
		rect(575,650,50,25);
		textSize(15);
		text("Altitude over 3000", 575,695);
	}

	//shows airports in France
	public void showFrance() {
		for (Marker m : airportList) {
			String country = "France";
			String currentCountry = m.getStringProperty("country");
			if (!currentCountry.contains(country)) {
				m.setHidden(true);
			}
		}
	}

	// shows airports in Seychelles
	public void showSeychelles() {
		for (Marker m : airportList) {
			String country = "Seychelles";
			String currentCountry = m.getStringProperty("country");
			if (!currentCountry.contains(country)) {
				m.setHidden(true);
			}
		}
	}

	// shows airports in Romania
	public void showRomania() {
		for (Marker m : airportList) {
			String country = "Romania";
			String currentCountry = m.getStringProperty("country");
			if (!currentCountry.contains(country)) {
				m.setHidden(true);
			}
		}
	}

	// shows all airports over 1000 altitude
	public void showAltitudeOver1000() {
		for (Marker m : airportList) {
			String altitude = m.getStringProperty("altitude");
			Integer alt = Integer.parseInt(altitude);
			if (!(alt >1000)) {
				m.setHidden(true);
			}
		}
	}

	// shows all airports over 2000 altitude
	public void showAltitudeOver2000() {
		for (Marker m : airportList) {
			String altitude = m.getStringProperty("altitude");
			Integer alt = Integer.parseInt(altitude);
			if (!(alt >2000)) {
				m.setHidden(true);
			}
		}
	}

	// shows all airports over 3000 altitude
	public void showAltitudeOver3000() {
		for (Marker m : airportList) {
			String altitude = m.getStringProperty("altitude");
			Integer alt = Integer.parseInt(altitude);
			if (!(alt >3000)) {
				m.setHidden(true);
			}
		}
	}

	// loop over and unhide all markers
	private void unhideMarkers() {
		for(Marker marker : airportList) {
			marker.setHidden(false);
		}
	}

	//checks to see if mouse is over country button
	public boolean overCountry(int x, int y, int width, int height) {
		if (mouseX >= x && mouseX <= x+width && mouseY >= y && mouseY <= y+height) {
			return true;
		} else {
			return false;
		}
	}

	//checks to see if over altitude button
	public boolean overAltitude(int x, int y, int width, int height) {
		if (mouseX >= x && mouseX <= x+width && mouseY >= y && mouseY <= y+height) {
			return true;
		} else {
			return false;
		}
	}

	//checks to see if mouse is over the addAll button
	public boolean overAll(int x, int y, int width, int height) {
		if (mouseX >= x && mouseX <= x+width && mouseY >= y && mouseY <= y+height) {
			return true;
		} else {
			return false;
		}
	}

	//checks to see if mousePressed is over what button
	public void mousePressed() {
		if (overCountry(50,650,50,25)) {  // France
			showFrance();
		}
		if (overCountry(110,650,50,25)) {  // Seychelles
			showSeychelles();
		}
		if (overCountry(200,650,50,25)) { // Romania
			showRomania();
		}
		if (overAltitude(400,650,50,25)) { // over 1000 altitude
			showAltitudeOver1000();
		}
		if (overAltitude(400,700,50,25)) { //over 2000 altitude
			showAltitudeOver2000();
		}
		if (overAltitude(575,650,50,25)) { // over 3000 altitude
			showAltitudeOver3000();
		}
		if (overAll(50,700,50,25)) {
			unhideMarkers();
		}
	}

	public static void main(String args[]) {
		PApplet.main(new String[] {"module6.AirportMap"});
	}
}
