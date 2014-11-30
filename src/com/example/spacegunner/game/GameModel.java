package com.example.spacegunner.game;

public interface GameModel {

	/**
	 * Start the next level.
	 */
	public void startNextLevel();

	/**
	 * Count down the time.
	 */
	public void countdownTime();

	/**
	 * Called whenever a ship is destroyed.
	 */
	public void shipDestroyed();

	/**
	 * @return whether the current level is finished
	 */
	public boolean isLevelFinished();

	/**
	 * @return whether the current game is over
	 */
	public boolean isGameOver();

	/**
	 * @return the level
	 */
	public int getLevel();

	/**
	 * @return the speed modifier of the ships.
	 */
	public int getSpeedModifier();

	/**
	 * @return the points
	 */
	public int getPoints();

	/**
	 * @return the points the player had when the level started. Used when the
	 *         game is paused to return to the previous LevelView.
	 */
	public int getPointsAtLevelStart();

	/**
	 * @return the shipsToDisplay
	 */
	public int getShipsToDestroy();

	/**
	 * @return the shipsDestroyed
	 */
	public int getShipsDestroyed();

	/**
	 * @return the secondsToPlay
	 */
	public int getTime();

	/**
	 * @return the secondsPerLevel
	 */
	public int getSecondsPerLevel();

	/**
	 * @return the maximum time a ship should be shown
	 */
	public int getMaximumTimeShown();

	/**
	 * @return the number of the first level
	 */
	public int getFirstLevel();

	/**
	 * @return the toString() method.
	 */
	public String toString();

	

}