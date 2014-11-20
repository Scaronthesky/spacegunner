package com.example.spacegunner.gameresult;

import com.example.spacegunner.ioservice.PlayerHighscore;

public interface GameResultView {

	public void hideSaveHighscoreLayout();
	
	public PlayerHighscore readHighscore();
	
	public void saveHighscore(final PlayerHighscore playerHighscore);

	public void showHighscoreSavedToast();

	public void startMainView();
}
