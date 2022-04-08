package sait.frms.manager;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import sait.frms.exception.InvalidFlightCodeException;
import sait.frms.problemdomain.Flight;

public class FlightManager {
	private ArrayList<Flight> flights = new ArrayList<>();
	private ArrayList<String> airports = new ArrayList<>();

	private final String AIRPORTS_FILENAME = "res/airports.csv";
	private final String FLIGHTS_FILENAME = "res/flights.csv";

	public FlightManager() throws FileNotFoundException {
		populateAirports();
		populateFlights();
	}

	private void populateAirports() throws FileNotFoundException {
		Scanner airportFileReader = loadFileIntoScanner(AIRPORTS_FILENAME);

		while (airportFileReader.hasNext()) {
			String airportCode = airportFileReader.next();
			String airportName = airportFileReader.next();
			airports.add(airportCode);
		}

		airportFileReader.close();
	}

	private void populateFlights() throws FileNotFoundException {
		Scanner flightFileReader = loadFileIntoScanner(FLIGHTS_FILENAME);

		while (flightFileReader.hasNext()) {
			String code = flightFileReader.next();
			String airlineName = flightFileReader.next();
			String from = flightFileReader.next();
			String to = flightFileReader.next();
			String weekday = flightFileReader.next();
			String time = flightFileReader.next();
			int seats = flightFileReader.nextInt();
			double costPerSeat = flightFileReader.nextDouble();

			try {
				Flight flight = new Flight(code, airlineName, from, to, weekday, time, seats, costPerSeat);
				flights.add(flight);
			} catch (InvalidFlightCodeException e) {
				System.out
						.println("Flight file contains the flight code: " + code + ", this is an invalid flight code");
			}
		}

		flightFileReader.close();
	}

	public static Scanner loadFileIntoScanner(String fileName) throws FileNotFoundException {

		File file = new File(fileName);
		Scanner fileReader = new Scanner(file).useDelimiter(",|\r\n");

		return fileReader;
	}

	public String findAirportByCode(String code) {
		String airportName = "";

		for (int i = 1; i < airports.size(); i++) {
			if (airports.get(i).equals(code)) {
				return airports.get(i + 1);
			}
		}

		airportName = "No Airport found with that code";
		return airportName;
	}

	public String findFlightByCode(String code) {
		String flightName = "";

		for (int i = 1; i < flights.size(); i++) {
			if (flights.get(i).getCode().equals(code)) {
				return flights.get(i).toString();
			}
		}

		flightName = "No flight found with that code";
		return flightName;
	}

	public ArrayList<Flight> findFlights(String from, String to, String weekday) {
		ArrayList<Flight> specficFlights = new ArrayList<>();

		for (int i = 0; i < flights.size(); i++) {
			Flight flight = flights.get(i);
			String flightFrom = flight.getFrom();
			String flightTo = flight.getTo();
			String flightWeekday = flight.getWeekday();

			if (flightFrom.equals(from) && flightTo.equals(to) && flightWeekday.equals(weekday)) {
				specficFlights.add(flight);
			}
		}

		return specficFlights;
	}

	public ArrayList<String> getAirports() {
		return airports;
	}

	public ArrayList<Flight> getFlights() {
		return flights;
	}
}
