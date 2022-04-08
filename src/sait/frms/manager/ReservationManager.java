package sait.frms.manager;

import java.io.*;
import java.io.RandomAccessFile;
import java.util.*;

import sait.frms.exception.*;
import sait.frms.problemdomain.*;

public class ReservationManager {
	private ArrayList<Reservation> reservations = new ArrayList<>();
	private RandomAccessFile raf;
	private final String RAF_RESERVATIONS_FILE = "res/reservations.bin";

	public ReservationManager() {
		populateFromBinary();
	}

	public Reservation makeReservation(Flight flight, String name, String citizenship) throws NoMoreSeatException, InvalidCitizenshipException, InvalidNameException {
		String reservationCode = generateReservationCode(flight);
		int amountOfSeatsTaken = 0;


		for (int i = 0; i < reservations.size(); i++) {
			Reservation checkReservation = reservations.get(i);
			if (checkReservation.getFlightCode().equals(flight.getCode())) {
				amountOfSeatsTaken++;
			}
		}
		if (amountOfSeatsTaken > flight.getSeats()) {
			throw new NoMoreSeatException("There's no more seat in this flight");
		} 
		if (name.equals("")) {
			throw new InvalidNameException("Name");
		} else if (citizenship.equals("")){
			throw new InvalidCitizenshipException("Citizenship");
		}
		
		
		Reservation reservation = new Reservation(reservationCode, flight.getCode(), flight.getAirlineName(), name, citizenship,
				flight.getCostPerSeat(), true);
		reservations.add(reservation);

		return reservation;
	}

	public ArrayList<Reservation> findReservations(String code, String airline, String name) {
		ArrayList<Reservation> specificReservations = new ArrayList<>();

		for (int i = 0; i < reservations.size(); i++) {
			Reservation reservation = reservations.get(i);
			String tempReservationCode = reservation.getCode();
			String tempReservationAirline = reservation.getAirline();
			String tempReservationName = reservation.getName();

			if (tempReservationCode.equals(code) || tempReservationAirline.equals(airline)
					|| tempReservationName.equals(name)) {
				specificReservations.add(reservation);
			}
		}

		return specificReservations;
	}

	public Reservation findReservationByCode(String code) {
		Reservation reservation = null;

		for (int i = 0; i < reservations.size(); i++) {
			if (reservations.get(i).getCode().equals(code)) {
				reservation = reservations.get(i);
			}
		}

		return reservation;
	}

	public void persist() {
		try {

			raf = new RandomAccessFile(RAF_RESERVATIONS_FILE, "rw");

			for (int i = 0; i < reservations.size(); i++) {
				Reservation reservation = reservations.get(i);

				String code = String.format("%-5s", reservation.getCode());
				raf.writeUTF(code);

				String flightCode = String.format("%-7s", reservation.getFlightCode());
				raf.writeUTF(flightCode);

				String airline = String.format("%-50s", reservation.getAirline());
				raf.writeUTF(airline);

				String name = String.format("%-50s", reservation.getName());
				raf.writeUTF(name);

				String citzenship = String.format("%-50s", reservation.getCitzenship());
				raf.writeUTF(citzenship);

				raf.writeDouble(reservation.getCost());

				raf.writeBoolean(reservation.isActive());
			}
			
			raf.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getAvailableSeats(Flight flight) {
		int seatsBooked = 0;

		for (int i = 0; i < reservations.size(); i++) {
			Reservation reservation = reservations.get(i);
			String airline = reservation.getAirline();
			if (flight.getAirlineName().equals(airline) && reservation.isActive()) {
				seatsBooked++;
			}

		}

		return flight.getSeats() - seatsBooked;
	}

	public String generateReservationCode(Flight flight) {
		String fromAirport = flight.getFrom();
		String toAirport = flight.getTo();
		String code = "";

		if (fromAirport.charAt(0) == 'Y' && toAirport.charAt(0) == 'Y') {
			code = "D";
		} else {
			code = "I";
		}

		int randomNumber = (1000 + (int) (Math.random() * 9000));

		return code + randomNumber;
	}

	public void populateFromBinary() {
		try {
			RandomAccessFile raf = new RandomAccessFile(RAF_RESERVATIONS_FILE, "r");

			boolean endOfFile = false;

			while (endOfFile != true) {
				try {
					
					String code = raf.readUTF().trim();
					String flightCode = raf.readUTF().trim();
					String airline = raf.readUTF().trim();
					String name = raf.readUTF().trim();
					String citzenship = raf.readUTF().trim();
					double cost = raf.readDouble();
					Boolean active = raf.readBoolean();
					
					Reservation reservation = new Reservation(code, flightCode, airline, name, citzenship, cost,
							active);
					reservations.add(reservation);
				} catch (EOFException e) {
					endOfFile = true;
					
				}
			}
			
			raf.close();

		} catch (IOException e) {
			System.out.println("Error populating reservations");
		}

	}
}
