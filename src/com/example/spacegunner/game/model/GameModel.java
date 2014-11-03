package com.example.spacegunner.game.model;

public class GameModel {

	public static final long MAXIMUM_TIME_SHOWN = 2000;
	private boolean isGameRunning;
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
		this.isGameRunning = false;
		this.level = 0;
		this.points = 0;
		this.shipsToDestroy = 0;
		this.time = 0;
	}

	/**
	 * Start the game
	 */
	public void startGame() {
		this.isGameRunning = true;
	}

	/**
	 * Stop the game
	 */
	public void stopGame() {
		this.isGameRunning = false;
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
		this.shipsToDestroy = this.level * 7;
		this.shipsDestroyed  = 0;
		this.time = 60;
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
	 * @return the isGameRunning
	 */
	public boolean isGameRunning() {
		return isGameRunning;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GameModel [isGameRunning=" + isGameRunning + ", level=" + level
				+ ", points=" + points + ", shipsToDisplay=" + shipsToDestroy
				+ ", shipsDestroyed=" + shipsDestroyed + ", secondsToPlay="
				+ time + "]";
	}

}
