package com.example.spacegunner.gameresult;

import com.example.spacegunner.ioservice.PlayerHighscore;

public class GameResultPresenterImpl implements GameResultPresenter {

	private GameResultView view;
	private GameResultModel model;

	public GameResultPresenterImpl(GameResultView view, int points) {
		super();
		this.model = new GameResultModelImpl(points);
		this.view = view;
		checkForHighscore();
	}

	private void checkForHighscore() {
		final PlayerHighscore playerHighscore = this.view.readHighscore();
		final int currentPoints = this.model.getPoints();
		if (currentPoints < playerHighscore.getHighscore()) {
			this.view.hideSaveHighscoreLayout();
		}
	}

	@Override
	public void saveHighscoreButtonClicked(final String playerName) {
		PlayerHighscore playerHighscore = new PlayerHighscore(playerName,
				this.model.getPoints());
		this.view.saveHighscore(playerHighscore);
		this.view.showHighscoreSavedToast();
		this.view.startMainView();
	}

	@Override
	public void buttonReturnMainViewClicked() {
		this.view.startMainView();
	}

	@Override
	public void backButtonPressed() {
		this.view.startMainView();
	}

}
