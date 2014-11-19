package com.example.spacegunner.game;

public class GameModelImpl implements GameModel {

	private static final int INCREASE_POINTS_BY = 100;
	private static final int SHIP_MULTIPLIER = 10;
	private static final int SECONDS_PER_LEVEL = 60;
	private static final int MAXIMUM_TIME_SHOWN = 2000;
	private int level;
	private int points;
	private int shipsToDestroy;
	private int shipsDestroyed;
	private int time;

	/**
	 * The game model representation.
	 */
	public GameModelImpl() {
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
	@Override
	public void shipDestroyed() {
		this.shipsDestroyed++;
		this.points += INCREASE_POINTS_BY;
	}

	/**
	 * Countdown the time.
	 */
	@Override
	public void countdownTime() {
		this.time--;
	}

	/**
	 * Start the next level.
	 */
	@Override
	public void startNextLevel() {
		this.level++;
		this.shipsToDestroy = this.level * SHIP_MULTIPLIER;
		this.shipsDestroyed = 0;
		this.time = SECONDS_PER_LEVEL;
	}

	/**
	 * @return whether the current level is finished
	 */
	@Override
	public boolean isLevelFinished() {
		return getShipsDestroyed() >= getShipsToDestroy();
	}

	/**
	 * @return whether the current game is over
	 */
	@Override
	public boolean isGameOver() {
		return getTime() == 0 && getShipsDestroyed() < getShipsToDestroy();
	}

	/**
	 * @return the level
	 */
	@Override
	public int getLevel() {
		return level;
	}

	/**
	 * @return the points
	 */
	@Override
	public int getPoints() {
		return points;
	}

	/**
	 * @return the shipsToDisplay
	 */
	@Override
	public int getShipsToDestroy() {
		return shipsToDestroy;
	}

	/**
	 * @return the shipsDestroyed
	 */
	@Override
	public int getShipsDestroyed() {
		return shipsDestroyed;
	}

	/**
	 * @return the secondsToPlay
	 */
	@Override
	public int getTime() {
		return time;
	}

	@Override
	public int getSecondsPerLevel() {
		return SECONDS_PER_LEVEL;
	}

	@Override
	public int getMaximumTimeShown() {
		return MAXIMUM_TIME_SHOWN;
	}

	@Override
	public int getSpeedModifier() {
		return this.level;
	}

	@Override
	public String toString() {
		return "GameModel [level=" + level + ", points=" + points
				+ ", shipsDestroyed=" + shipsDestroyed + ", shipsToDestroy="
				+ shipsToDestroy + ", time=" + time + "]";
	}



}
