package com.example.spacegunner.ioservice;

public class PlayerHighscore {

	private final String playerName;

	private final int highscore;

	public PlayerHighscore(String playerName, int highscore) {
		super();
		this.playerName = playerName;
		this.highscore = highscore;
	}

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @return the highscore
	 */
	public int getHighscore() {
		return highscore;
	}

}
