package com.example.spacegunner.game;

import com.example.spacegunner.ioservice.PlayerHighscore;

public interface GameView {

	public void showLevelStartInfo(int level);

	public void increaseFrame();

	public long getFrame();

	public void resetFrame();

	public void setPoints(final int points);

	public void setLevel(final int level);

	public void setHits(final int shipsDestroyed);

	public void setTime(final int time);

	public int setHitBarWidth(final int shipsDestroyed, final int shipsToDestroy);

	public void setTimeBarWidth(final int time, final int secondsPerLevel);

	public void moveShipsToNewLocation(int speedModifier);

	public void displayShip();

	public void removeShips(final int maximumTimeShown);

	public abstract void showShipDestroyedAnimation();

	public abstract void playExplosionSound();

	public PlayerHighscore getHighscore();

	public void startMainView();

	public void startLevelView(int level, int points);

	public void returnToPreviousLevelView(int previousLevel, int pointsAtLevelStart);

	public void startGameResultView(int points);
	
}