package com.example.spacegunner.level;

public class LevelPresenterImpl implements LevelPresenter {

	private LevelView view;

	public LevelPresenterImpl(LevelView view) {
		super();
		this.view = view;
	}

	@Override
	public void continueGameButtonClicked() {
		this.view.startGameActivity();
	}

	@Override
	public void quitGameButtonClicked() {
		this.view.startMainActivity();
	}

}
