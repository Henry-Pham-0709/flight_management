package sait.frms.problemdomain;

public class Reservation {
	private String code;
	private String flightCode;
	private String airline;
	private String name;
	private String citzenship;
	private double cost;
	private boolean active;

	public Reservation(String code, String flightCode, String airline, String name, String citzenship, double cost,
			boolean active) {
		
		this.code = code;
		this.flightCode = flightCode;
		this.airline = airline;
		this.name = name;
		this.citzenship = citzenship;
		this.cost = cost;
		this.active = active;
	}

	public String getCode() {
		return code;
	}

	public String getFlightCode() {
		return flightCode;
	}

	public String getAirline() {
		return airline;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCitzenship() {
		return citzenship;
	}

	public void setCitzenship(String citzenship) {
		this.citzenship = citzenship;
	}

	public double getCost() {
		return cost;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return code;
	}

}
