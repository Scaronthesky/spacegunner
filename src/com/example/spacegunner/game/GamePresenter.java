package com.example.spacegunner.game;

public interface GamePresenter {

	public void startNextLevel();

	public void run();

	public void countDown();

	public void refreshScreen();

	public void backButtonPressed();

	public void endLevel();

	public void endGame();

	public void shipDestroyed();

	public void gamePaused();

}
