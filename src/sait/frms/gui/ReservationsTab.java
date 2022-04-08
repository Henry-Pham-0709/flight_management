package sait.frms.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sait.frms.exception.InvalidFlightCodeException;
import sait.frms.manager.ReservationManager;
import sait.frms.problemdomain.Reservation;

/**
 * Holds the components for the reservations tab.
 * 
 */
public class ReservationsTab extends TabBase {
	private JButton updateButton;
	private JButton findReservationButton;

	JTextField flightJField;
	JTextField codeJField;
	JTextField airlineJField;
	JTextField costJField;
	JTextField nameJField;
	JTextField citizenJField;
	JTextField codeJField2;
	JTextField nameJField2;
	JTextField airlineJField2;

	JComboBox statusJComboBox;

	/**
	 * Instance of reservation manager.
	 */
	private ReservationManager reservationManager;

	private JList<Reservation> reservationsList;

	private DefaultListModel<Reservation> reservationsModel;

	/**
	 * Creates the components for reservations tab.
	 */
	public ReservationsTab(ReservationManager reservationManager) {
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

		JLabel title = new JLabel("Reservations", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		panel.add(title);

		return panel;
	}

	private JPanel createWestPanel() {
		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout());

		reservationsModel = new DefaultListModel<>();
		reservationsList = new JList<>(reservationsModel);
		reservationsList.setFixedCellWidth(350);
		reservationsList.setFixedCellHeight(15);

		// User can only select one item at a time.
		reservationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Wrap JList in JScrollPane so it is scrollable.
		JScrollPane scrollPane = new JScrollPane(this.reservationsList);

		reservationsList.addListSelectionListener(new ReservationListSelectionListener());

		panel.add(scrollPane);

		return panel;
	}

	private JPanel createEastPanel() {
		JPanel panel = new JPanel();
		JPanel southPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel northPanel = new JPanel();

		panel.setLayout(new BorderLayout());

		southPanel.setLayout(new GridLayout(1, 1));
		updateButton = new JButton("Update");
		southPanel.add(updateButton);
		panel.add(southPanel, BorderLayout.SOUTH);

		updateButton.addActionListener(new updateButtonListerner());

		northPanel.setLayout(new FlowLayout());
		JLabel title = new JLabel("Reserve", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 24));
		title.setHorizontalAlignment(JLabel.RIGHT);
		northPanel.add(title);
		panel.add(northPanel, BorderLayout.NORTH);

		centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.fill = GridBagConstraints.HORIZONTAL;

		JLabel codeJLabel = new JLabel("Code:");
		codeJLabel.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 1;
		centerPanel.add(codeJLabel, constraints);

		codeJField = new JTextField(10);
		constraints.gridx = 1;
		constraints.gridy = 1;
		codeJField.setEditable(false);
		centerPanel.add(codeJField, constraints);

		JLabel flightJLabel = new JLabel("Flight:");
		flightJLabel.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 2;
		centerPanel.add(flightJLabel, constraints);

		flightJField = new JTextField(10);
		constraints.gridx = 1;
		constraints.gridy = 2;
		flightJField.setEditable(false);
		centerPanel.add(flightJField, constraints);

		JLabel airlineJLabel = new JLabel("Airline:");
		airlineJLabel.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 3;
		centerPanel.add(airlineJLabel, constraints);

		airlineJField = new JTextField(10);
		constraints.gridx = 1;
		constraints.gridy = 3;
		airlineJField.setEditable(false);
		centerPanel.add(airlineJField, constraints);

		JLabel costJLabel = new JLabel("Cost:");
		costJLabel.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 4;
		centerPanel.add(costJLabel, constraints);

		costJField = new JTextField(10);
		constraints.gridx = 1;
		constraints.gridy = 4;
		costJField.setEditable(false);
		centerPanel.add(costJField, constraints);

		JLabel nameJLabel = new JLabel("Name:");
		nameJLabel.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 5;
		centerPanel.add(nameJLabel, constraints);

		nameJField = new JTextField(10);
		constraints.gridx = 1;
		constraints.gridy = 5;
		centerPanel.add(nameJField, constraints);

		JLabel citizenJLabel = new JLabel("Citizenship:");
		citizenJLabel.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 6;
		centerPanel.add(citizenJLabel, constraints);

		citizenJField = new JTextField(10);
		constraints.gridx = 1;
		constraints.gridy = 6;
		centerPanel.add(citizenJField, constraints);

		JLabel statusJLabel = new JLabel("Status:");
		statusJLabel.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 7;
		centerPanel.add(statusJLabel, constraints);

		statusJComboBox = new JComboBox<String>();
		statusJComboBox.addItem("Active");
		statusJComboBox.addItem("Inactive");
		constraints.gridx = 1;
		constraints.gridy = 7;
		centerPanel.add(statusJComboBox, constraints);

		panel.add(centerPanel, BorderLayout.CENTER);

		return panel;
	}

	private JPanel createSouthPanel() {
		JPanel panel = new JPanel();

		JPanel northPanel = new JPanel();
		JLabel title = new JLabel("Search", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 25));
		northPanel.add(title);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());

		JPanel codePanel = new JPanel();
		codePanel.setLayout(new BoxLayout(codePanel, BoxLayout.X_AXIS));
		JLabel codeJLabel = new JLabel("  Code:");
		codeJField2 = new JTextField(10);
		codePanel.add(codeJLabel);
		codePanel.add(codeJField2);

		centerPanel.add(codePanel, BorderLayout.NORTH);

		JPanel airlinePanel = new JPanel();
		airlinePanel.setLayout(new BoxLayout(airlinePanel, BoxLayout.X_AXIS));
		JLabel airlineJLabel = new JLabel("Airline:");
		airlineJField2 = new JTextField(10);
		airlinePanel.add(airlineJLabel);
		airlinePanel.add(airlineJField2);

		centerPanel.add(airlinePanel, BorderLayout.CENTER);

		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
		JLabel dayJLabel = new JLabel("Name:");
		nameJField2 = new JTextField(10);
		namePanel.add(dayJLabel);
		namePanel.add(nameJField2);

		centerPanel.add(namePanel, BorderLayout.SOUTH);

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1, 1));
		findReservationButton = new JButton("Find Reservation");
		southPanel.add(findReservationButton);

		findReservationButton.addActionListener(new FindReservationButtonListener());

		panel.setLayout(new BorderLayout());
		panel.add(northPanel, BorderLayout.NORTH);
		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(southPanel, BorderLayout.SOUTH);

		return panel;
	}

	private class FindReservationButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String code = codeJField2.getText();
			String airline = airlineJField2.getText();
			String customerName = nameJField2.getText();

			ArrayList<Reservation> reservationsFound;

			if (code.equals("") && airline.equals("") && customerName.equals("")) {
				JOptionPane.showMessageDialog(null, "Please enter a reservation code, airline name or customer name");
			} else {
				reservationsFound = reservationManager.findReservations(code, airline, customerName);
				if (reservationsFound.size() == 0 || reservationsFound == null) {
					String notEmptyBox = (!code.equals("")) ? "Reservation Code"
							: (!airline.equals("") ? "Airline Name" : "Customer Name");
					JOptionPane.showMessageDialog(null, "No Reservations have been found with that " + notEmptyBox);
				} else {
					reservationsModel.clear();
					for (int i = 0; i < reservationsFound.size(); i++) {
						reservationsModel.add(i, reservationsFound.get(i));
					}
				}
			}
		}

	}

	private class updateButtonListerner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String reservationCode = codeJField.getText();
			String reservationUpdatedName = nameJField.getText();
			String reservationUpdatedCitizenship = citizenJField.getText();
			String activeBox = (String) statusJComboBox.getSelectedItem();
			boolean isActive = (activeBox.equals("Active")) ? true : false;

			Reservation reservation = null;

			reservation = reservationManager.findReservationByCode(reservationCode);

			if (reservationUpdatedCitizenship.equals("") || reservationUpdatedName.equals("")) {
				JOptionPane.showMessageDialog(null, "Citizenship and Name Field Must not be empty");
			} else {
				reservation.setCitzenship(reservationUpdatedCitizenship);
				reservation.setName(reservationUpdatedName);
				reservation.setActive(isActive);
				reservationManager.persist();
				JOptionPane.showMessageDialog(null, "Reservation Updated!");
				reservationsList.clearSelection();
				nameJField.setText("");
				citizenJField.setText("");
				codeJField.setText("");
				flightJField.setText("");
				costJField.setText("");
				airlineJField.setText("");
				statusJComboBox.setSelectedIndex(0);
			}

		}

	}

	private class ReservationListSelectionListener implements ListSelectionListener {
		/**
		 * Called when user selects an item in the JList.
		 */
		@Override
		public void valueChanged(ListSelectionEvent e) {
			Reservation reservation = reservationsList.getSelectedValue();

			try {
				flightJField.setText(reservation.getFlightCode());
				codeJField.setText(reservation.getCode());
				airlineJField.setText(reservation.getAirline());
				costJField.setText("$" + reservation.getCost());
				nameJField.setText(reservation.getName());
				citizenJField.setText(reservation.getCitzenship());
				statusJComboBox.setSelectedIndex((reservation.isActive()) ? 0 : 1 );
			} catch (NullPointerException exception) {

			}

		}

	}
}
