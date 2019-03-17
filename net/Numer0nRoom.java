package net;

import java.util.Arrays;

public class Numer0nRoom {

	private String roomID;
	private Numer0nClientUser first;
	private Numer0nClientUser late;
	private boolean[][] flag = new boolean[2][2];
	private int FIRST = 0;
	private int LATE = 1;
	private int isPreProcessing = 0;
	private int nextAttacker = FIRST;

	public Numer0nRoom(String roomNumber, Numer0nClientUser first, Numer0nClientUser late) {
		this.roomID = roomNumber;
		this.first = first;
		this.late = late;
		for(int i = 0; i < flag.length; i++) Arrays.fill(flag[i], false);
	}

	/**
	 * @return roomNumber
	 */
	public String getRoomID() {
		return roomID;
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

	public void setPreProcessing(Numer0nClientUser user) {
		this.flag[isPreProcessing][divUser(user)] = true;
	}

	public boolean isPreProcessing() {
		if(this.flag[isPreProcessing][FIRST] && this.flag[isPreProcessing][LATE])
			return true;
		else return false;
	}

	public Numer0nClientUser whoAttacker() {
		if(nextAttacker == FIRST) {
			nextAttacker = LATE;
			return first;
		}else {
			nextAttacker = FIRST;
			return late;
		}
	}

	public Numer0nClientUser whoDiver() {
		if(nextAttacker == FIRST) return first;
		else return late;
	}

	private int divUser(Numer0nClientUser user) {
		if(user.equals(first)) return FIRST;
		if(user.equals(late)) return LATE;
		throw new IllegalArgumentException("引数のクラスは存在しません");
	}



}
