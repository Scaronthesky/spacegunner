package com.example.spacegunner.main;

public class MainPresenterImpl implements MainPresenter {

	private MainView view;

	public MainPresenterImpl(MainView view) {
		super();
		this.view = view;
	}

	@Override
	public void startGameButtonClicked() {
		this.view.startGameActivity();
	}

	@Override
	public void backButtonPressed() {
		// no-op
	}

}
