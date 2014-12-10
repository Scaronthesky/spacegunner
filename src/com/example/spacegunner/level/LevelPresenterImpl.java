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
		final int currentPoints = this.model.getPoints();
		this.view.startGameResultView(currentPoints);
	}

	@Override
	public void backButtonPressed() {
		this.view.startMainView();
	}

}
