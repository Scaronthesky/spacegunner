package com.example.spacegunner.level;

import com.example.spacegunner.ioservice.PlayerHighscore;

public interface LevelView {

	public void displayLevelAndPoints(final int level, final int points);
	
	public PlayerHighscore readHighscore();
	
	public void startMainView();

	public void startGameView(final int level, final int points);

	public void startGameResultView(int points);
}
