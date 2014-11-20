package com.example.spacegunner.highscore;

import com.example.spacegunner.ioservice.PlayerHighscore;

public class HighscorePresenterImpl implements HighscorePresenter {

	private HighscoreView view;

	public HighscorePresenterImpl(HighscoreView view) {
		super();
		this.view = view;
	}

	@Override
	public void saveHighscoreButtonClicked(PlayerHighscore playerHighscore) {
		this.view.saveHighscore(playerHighscore);
		this.view.showHighscoreSavedToast();
		this.view.startMainActivity();
	}

}
