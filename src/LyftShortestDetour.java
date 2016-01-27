/**
 * 
 * @author aakashranga
 * 
 *This is an attempt to solve the Lyft Programming Challenge found on the Software engineering application portal. 
 *The question is as follows:
 *Calculate the detour distance between two different rides. 
 *Given four latitude / longitude pairs, where driver one is traveling from point A to point B and driver two 
 *is traveling from point C to point D, write a function (in your language of choice) to calculate the shorter of
 *the detour distances the drivers would need to take to pick-up and drop-off the other driver.
 *
 *
 *Sources used to understand Latitude/Longitude based calculations were:
 *	http://stackoverflow.com/questions/27928/calculate-distance-between-two-latitude-longitude-points-haversine-formula
 *	http://www.movable-type.co.uk/scripts/latlong.html
 *	https://en.wikipedia.org/wiki/Haversine_formula
 */
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class LyftShortestDetour {
	Point A, B, C, D;
	static double[] validDistances = new double[2];;
	final static String[] detours = new String[2];
	HashMap<String, Point> points = new HashMap<String, Point>();
	
	public LyftShortestDetour(int lat1, int lon1, int lat2, int lon2, int lat3, int lon3, int lat4, int lon4) {
		A = new Point(lat1, lon1);
		B = new Point(lat2, lon2);
		C = new Point(lat3, lon3);
		D = new Point(lat4, lon4);
		points.put("A", A);
		points.put("B", B);
		points.put("C", C);
		points.put("D", D);
	}

	/*
	 * Calculates the Distance between two latitude/longitude pairs using the
	 * Haversine formula. Resources used: http://www.movable-type.co.uk/scripts/latlong.html
	 *									  https://en.wikipedia.org/wiki/Haversine_formula
	 */
	public static double haversineDistanceCalculator(Point start, Point destination) {
		double radius = 6371;
		double lat1 = Math.toRadians(start.getX());
		double lat2 = Math.toRadians(destination.getX());
		double lon1 = start.getY();
		double lon2 = destination.getY();
		
		double deltaLatitude = Math.toRadians(lat2 - lat1);
		double deltaLongitude = Math.toRadians(lon2 - lon1);
		
		double a = Math.sin(deltaLatitude / 2) * Math.sin(deltaLatitude / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(deltaLongitude / 2) * Math.sin(deltaLongitude / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double distance = radius * c;
		
		return distance;		
	}
	/*
	 * This is the main calculator function. It checks both the possibilities and returns an answer for which driver should
	 * take the detour based on what detour distance is shorter
	 */
	public void shortestDetour() {
		double distance = 0;
		for (int i = 0; i < detours.length; i++) {
			String currentRoute = detours[i];
			for (int j = 0; j < currentRoute.length() - 1; j++) {
				distance += haversineDistanceCalculator(points.get(""+currentRoute.charAt(j)), points.get(""+currentRoute.charAt(j+1)));
			}
			validDistances[i] = distance;
			distance = 0;
		}
		
		if (validDistances[0] > validDistances[1]) {
			System.out.printf("The shorter distance is %.2f miles.\nThe first driver should make the detour.\n", validDistances[0]/1.6);
		}
		else {
			System.out.printf("The shorter distance is %.2f miles.\nThe second driver should make the detour.\n", validDistances[1]/1.6);
		}
		
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		System.out.println("Enter latitude and longitude for point A:");
		int lat1 = s.nextInt();
		int lon1 = s.nextInt();
		
		System.out.println("Enter latitude and longitude for point B:");
		int lat2 = s.nextInt();
		int lon2 = s.nextInt();
		
		System.out.println("Enter latitude and longitude for point C:");
		int lat3 = s.nextInt();
		int lon3 = s.nextInt();
		
		System.out.println("Enter latitude and longitude for point D:");
		int lat4 = s.nextInt();
		int lon4 = s.nextInt();
		
		LyftShortestDetour lsd = new LyftShortestDetour(lat1, lon1, lat2, lon2, lat3, lon3, lat4, lon4);
		
		/*
		 * Next step is adding the possible detours to a list so that it can be accessed later on
		 * The possible detours are : Driver 1 takes a detour i.e ACDB
		 * 							  Driver 2 takes a detour i.e CABD
		 */
		detours[0] = "ACDB";
		detours[1] = "CABD";
		lsd.shortestDetour();
		
		s.close();
	}
	
	
	
	
	
	
	
	
	
}
