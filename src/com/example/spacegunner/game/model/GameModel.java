package com.example.spacegunner.game.model;

public class GameModel {

	private static final int SHIP_MULTIPLIER = 10;
	public static final long MAXIMUM_TIME_SHOWN = 2000;
	public static final int SECONDS_PER_LEVEL = 60;
	private int level;
	private int points;
	private int shipsToDestroy;
	private int shipsDestroyed;
	private int time;

	/**
	 * The game model representation.
	 */
	public GameModel() {
		super();
		this.level = 0;
		this.points = 0;
		this.shipsDestroyed = 0;
		this.shipsToDestroy = 0;
		this.time = 0;
	}


	/**
	 * Called whenever a ship is destroyed.
	 */
	public void shipDestroyed() {
		this.shipsDestroyed++;
		this.points += 100;
	}

	/**
	 * Countdown the time.
	 */
	public void countdownTime() {
		this.time--;
	}

	/**
	 * Start the next level.
	 */
	public void startNextLevel() {
		this.level++;
		this.shipsToDestroy = this.level * SHIP_MULTIPLIER;
		this.shipsDestroyed = 0;
		this.time = SECONDS_PER_LEVEL;
	}

	/**
	 * @return whether the current level is finished
	 */
	public boolean isLevelFinished() {
		return getShipsDestroyed() >= getShipsToDestroy();
	}

	/**
	 * @return whether the current game is over
	 */
	public boolean isGameOver() {
		return getTime() == 0 && getShipsDestroyed() < getShipsToDestroy();
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * @return the shipsToDisplay
	 */
	public int getShipsToDestroy() {
		return shipsToDestroy;
	}

	/**
	 * @return the shipsDestroyed
	 */
	public int getShipsDestroyed() {
		return shipsDestroyed;
	}

	/**
	 * @return the secondsToPlay
	 */
	public int getTime() {
		return time;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GameModel [level=" + level + ", points=" + points
				+ ", shipsDestroyed=" + shipsDestroyed + ", shipsToDestroy="
				+ shipsToDestroy + ", time=" + time + "]";
	}

	

}
