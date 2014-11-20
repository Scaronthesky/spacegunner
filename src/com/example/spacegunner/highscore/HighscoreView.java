package com.example.spacegunner.highscore;

import com.example.spacegunner.ioservice.PlayerHighscore;

public interface HighscoreView {

	public void saveHighscore(final PlayerHighscore playerHighscore);

	public void showHighscoreSavedToast();

	public void startMainActivity();
}
