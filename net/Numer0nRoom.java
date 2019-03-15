package net;

public class Numer0nRoom {

	private String roomNumber;
	private Numer0nClientUser first;
	private Numer0nClientUser late;

	public Numer0nRoom(String roomNumber, Numer0nClientUser first, Numer0nClientUser late) {
		this.roomNumber = roomNumber;
		this.first = first;
		this.late = late;
	}

	/**
	 * @return roomNumber
	 */
	public String getRoomNumber() {
		return roomNumber;
	}
	/**
	 * @return first
	 */
	public Numer0nClientUser getFirst() {
		return first;
	}
	/**
	 * @return late
	 */
	public Numer0nClientUser getLate() {
		return late;
	}


}
