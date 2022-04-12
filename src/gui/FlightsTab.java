package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import exception.*;
import manager.FlightManager;
import manager.ReservationManager;
import problemdomain.*;

/**
 * Holds the components for the flights tab.
 * 
 */
public class FlightsTab extends TabBase {
	private JButton reserveButton;
	private JButton findFlightButton;

	JTextField airlineJField;
	JTextField flightJField;
	JTextField dayJField;
	JTextField timeJField;
	JTextField costJField;
	JTextField nameJField;
	JTextField citizenJField;

	JComboBox fromComboBox;
	JComboBox toComboBox;
	JComboBox dayComboBox;
	String[] dayFieldStrings = { "Any", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

	ArrayList<Flight> allFlights;
	/**
	 * Instance of flight manager.
	 */
	private FlightManager flightManager;

	/**
	 * Instance of reservation manager.
	 */
	private ReservationManager reservationManager;

	/**
	 * List of flights.
	 */
	private JList<Flight> flightsList;

	private DefaultListModel<Flight> flightsModel;

	/**
	 * Creates the components for flights tab.
	 */
	/**
	 * Creates the components for flights tab.
	 * 
	 * @param flightManager      Instance of FlightManager.
	 * @param reservationManager Instance of ReservationManager
	 */
	public FlightsTab(FlightManager flightManager, ReservationManager reservationManager) {
		this.flightManager = flightManager;
		this.reservationManager = reservationManager;

		panel.setLayout(new BorderLayout(10, 10));

		JPanel northPanel = createNorthPanel();
		panel.add(northPanel, BorderLayout.NORTH);

		JPanel westPanel = createWestPanel();
		panel.add(westPanel, BorderLayout.WEST);

		JPanel eastPanel = createEastPanel();
		panel.add(eastPanel, BorderLayout.EAST);

		JPanel southPanel = createSouthPanel();
		panel.add(southPanel, BorderLayout.SOUTH);

	}

	/**
	 * Creates the north panel.
	 * 
	 * @return JPanel that goes in north.
	 */
	private JPanel createNorthPanel() {
		JPanel panel = new JPanel();

		JLabel title = new JLabel("Flights", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		panel.add(title);

		return panel;
	}

	/**
	 * Creates the center panel.
	 * 
	 * @return JPanel that goes in center.
	 */
	private JPanel createWestPanel() {
		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout());

		flightsModel = new DefaultListModel<>();

		allFlights = flightManager.getFlights();

		for (int i = 0; i < allFlights.size(); i++) {
			flightsModel.add(i, allFlights.get(i));
		}

		flightsList = new JList<>(flightsModel);
		flightsList.setFixedCellWidth(350);
		flightsList.setFixedCellHeight(20);

		// User can only select one item at a time.
		flightsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Wrap JList in JScrollPane so it is scrollable.
		JScrollPane scrollPane = new JScrollPane(this.flightsList);

		flightsList.addListSelectionListener(new MyListSelectionListener());

		panel.add(scrollPane, BorderLayout.CENTER);

		return panel;
	}

	private JPanel createEastPanel() {
		JPanel panel = new JPanel();
		JPanel southPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel northPanel = new JPanel();

		panel.setLayout(new BorderLayout());

		southPanel.setLayout(new GridLayout(1, 1));
		reserveButton = new JButton("Reserve");
		southPanel.add(reserveButton);
		panel.add(southPanel, BorderLayout.SOUTH);

		reserveButton.addActionListener(new ReserveButtonListener());

		northPanel.setLayout(new FlowLayout());
		JLabel title = new JLabel("Reserve", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 24));
		title.setHorizontalAlignment(JLabel.RIGHT);
		northPanel.add(title);
		panel.add(northPanel, BorderLayout.NORTH);

		centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.fill = GridBagConstraints.HORIZONTAL;

		JLabel flightJLabel = new JLabel("Flight:");
		flightJLabel.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 1;
		centerPanel.add(flightJLabel, constraints);

		flightJField = new JTextField(10);
		constraints.gridx = 1;
		constraints.gridy = 1;
		flightJField.setEditable(false);
		centerPanel.add(flightJField, constraints);

		JLabel airlineJLabel = new JLabel("Airline:");
		airlineJLabel.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 2;
		centerPanel.add(airlineJLabel, constraints);

		airlineJField = new JTextField(10);
		constraints.gridx = 1;
		constraints.gridy = 2;
		airlineJField.setEditable(false);
		centerPanel.add(airlineJField, constraints);

		JLabel dayJLabel = new JLabel("Day:");
		dayJLabel.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 3;
		centerPanel.add(dayJLabel, constraints);

		dayJField = new JTextField(10);
		constraints.gridx = 1;
		constraints.gridy = 3;
		dayJField.setEditable(false);
		centerPanel.add(dayJField, constraints);

		JLabel timeJLabel = new JLabel("Time:");
		timeJLabel.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 4;
		centerPanel.add(timeJLabel, constraints);

		timeJField = new JTextField(10);
		constraints.gridx = 1;
		constraints.gridy = 4;
		timeJField.setEditable(false);
		centerPanel.add(timeJField, constraints);

		JLabel costJLabel = new JLabel("Cost:");
		costJLabel.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 5;
		centerPanel.add(costJLabel, constraints);

		costJField = new JTextField(10);
		constraints.gridx = 1;
		constraints.gridy = 5;
		costJField.setEditable(false);
		centerPanel.add(costJField, constraints);

		JLabel nameJLabel = new JLabel("Name:");
		nameJLabel.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 6;
		centerPanel.add(nameJLabel, constraints);

		nameJField = new JTextField(10);
		constraints.gridx = 1;
		constraints.gridy = 6;
		centerPanel.add(nameJField, constraints);

		JLabel citizenJLabel = new JLabel("Citizenship:");
		citizenJLabel.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 7;
		centerPanel.add(citizenJLabel, constraints);

		citizenJField = new JTextField(10);
		constraints.gridx = 1;
		constraints.gridy = 7;
		centerPanel.add(citizenJField, constraints);

		panel.add(centerPanel, BorderLayout.CENTER);

		return panel;
	}

	private JPanel createSouthPanel() {
		JPanel panel = new JPanel();

		JPanel northPanel = new JPanel();
		JLabel title = new JLabel("Flight Finder", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 25));
		northPanel.add(title);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());

		JPanel fromPanel = new JPanel();
		fromPanel.setLayout(new BoxLayout(fromPanel, BoxLayout.X_AXIS));
		JLabel fromJLabel = new JLabel("From:");
		fromComboBox = new JComboBox(new Vector(flightManager.getAirports()));
		fromPanel.add(fromJLabel);
		fromPanel.add(fromComboBox);

		centerPanel.add(fromPanel, BorderLayout.NORTH);

		JPanel toPanel = new JPanel();
		toPanel.setLayout(new BoxLayout(toPanel, BoxLayout.X_AXIS));
		JLabel toJLabel = new JLabel("    To:");
		toComboBox = new JComboBox(new Vector(flightManager.getAirports()));
		toPanel.add(toJLabel);
		toPanel.add(toComboBox);

		centerPanel.add(toPanel, BorderLayout.CENTER);

		JPanel dayPanel = new JPanel();
		dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.X_AXIS));
		JLabel dayJLabel = new JLabel("  Day:");
		dayComboBox = new JComboBox(dayFieldStrings);
		dayPanel.add(dayJLabel);
		dayPanel.add(dayComboBox);

		centerPanel.add(dayPanel, BorderLayout.SOUTH);

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1, 1));
		findFlightButton = new JButton("Find Flight");
		southPanel.add(findFlightButton);

		findFlightButton.addActionListener(new FindFlightButtonListener());

		panel.setLayout(new BorderLayout());
		panel.add(northPanel, BorderLayout.NORTH);
		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(southPanel, BorderLayout.SOUTH);

		return panel;
	}

	private class FindFlightButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				findFlight();
			} catch (NullFlightException exception) {
				JOptionPane.showMessageDialog(null, exception.getMessage());
			}

		}

		private void findFlight() throws NullFlightException {

			String fromBox = fromComboBox.getSelectedItem().toString();
			String toBox = toComboBox.getSelectedItem().toString();
			String dayBox = dayComboBox.getSelectedItem().toString();

			ArrayList<Flight> foundFlights = new ArrayList<>();

			if (dayBox.equals("Any")) {
				for (int i = 0; i < allFlights.size(); i++) {
					String from = allFlights.get(i).getFrom();
					String to = allFlights.get(i).getTo();

					if (from.equals(fromBox) && to.equals(toBox)) {
						foundFlights.add(allFlights.get(i));
					}
				}
			} else {
				for (int i = 0; i < allFlights.size(); i++) {
					String from = allFlights.get(i).getFrom();
					String to = allFlights.get(i).getTo();
					String day = allFlights.get(i).getWeekday();

					if (from.equals(fromBox) && to.equals(toBox) && day.equals(dayBox)) {
						foundFlights.add(allFlights.get(i));
					}
				}
			}

			if (foundFlights.size() == 0) {
				throw new NullFlightException("No flights found");
			} else {
				flightsModel.clear();
				airlineJField.setText("");
				flightJField.setText("");
				dayJField.setText("");
				timeJField.setText("");
				costJField.setText("");

				for (int i = 0; i < foundFlights.size(); i++) {
					flightsModel.add(i, foundFlights.get(i));
				}
			}
		}
	}

	private class MyListSelectionListener implements ListSelectionListener {
		/**
		 * Called when user selects an item in the JList.
		 */
		@Override
		public void valueChanged(ListSelectionEvent e) {
				Flight flightSelected = flightsList.getSelectedValue();
				
				try {
					flightJField.setText(flightSelected.getCode());
					airlineJField.setText(flightSelected.getAirlineName());
					dayJField.setText(flightSelected.getWeekday());

					if (flightSelected.getTime().length() == 4) {
						timeJField.setText("0" + flightSelected.getTime() + "");
					} else {
						timeJField.setText(flightSelected.getTime() + "");
					}

					costJField.setText("$" + flightSelected.getCostPerSeat());
				} catch (NullPointerException exception) {
					flightJField.setText("");	
					airlineJField.setText("");
					dayJField.setText("");
					timeJField.setText("");
				}
		}
	}

	private class ReserveButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String passengerName = nameJField.getText();
			String passengerCitizenship = citizenJField.getText();

			try {
				Flight flight = getFlight();
				Reservation reservation = reservationManager.makeReservation(flight, passengerName, passengerCitizenship);
				nameJField.setText("");
				citizenJField.setText("");
				JOptionPane.showMessageDialog(null, "Reservation Booked! Your reservation code is " + reservation.getCode());
				reservationManager.persist();
			} catch (InvalidCitizenshipException exception) {
				JOptionPane.showMessageDialog(null, exception.getMessage());
			} catch (InvalidNameException exception) {
				JOptionPane.showMessageDialog(null, exception.getMessage());
			} catch (NoMoreSeatException exception) {
				JOptionPane.showMessageDialog(null, exception.getMessage());
			} catch (NullFlightException exception) {
				JOptionPane.showMessageDialog(null, exception.getMessage());
			}

		}

		private Flight getFlight() throws NullFlightException {
			Flight flight = null;
			ArrayList<Flight> flights = flightManager.getFlights();
			String flightCode = flightJField.getText();

			for (int i = 0; i < flights.size(); i++) {
				Flight tempFlight = flights.get(i);
				if (tempFlight.getCode().equals(flightCode)) {
					flight = tempFlight;
				}
			}

			if (flight == null) {
				throw new NullFlightException("You didn't select a flight");
			}
			return flight;
		}
	}
}
