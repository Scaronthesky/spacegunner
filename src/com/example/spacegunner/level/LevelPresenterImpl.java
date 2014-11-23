package com.example.spacegunner.level;

import com.example.spacegunner.ioservice.PlayerHighscore;

public class LevelPresenterImpl implements LevelPresenter {

	private LevelView view;
	private LevelModel model;

	public LevelPresenterImpl(LevelView view, int level, int points) {
		super();
		this.view = view;
		this.model = new LevelModelImpl(level, points);
		this.view.displayLevelAndPoints(level, points);
	}

	@Override
	public void continueGameButtonClicked() {
		this.view.startGameView(model.getLevel(), model.getPoints());
	}

	@Override
	public void quitGameButtonClicked() {
		final PlayerHighscore playerHighscore = this.view.readHighscore();
		final int currentPoints = this.model.getPoints();
		if (currentPoints > playerHighscore.getHighscore()) {
			this.view.startGameResultView(currentPoints);
		} else {
			this.view.startMainView();
		}
	}

	@Override
	public void backButtonPressed() {
		this.view.startMainView();
	}

}
