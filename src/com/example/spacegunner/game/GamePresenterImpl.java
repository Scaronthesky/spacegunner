package com.example.spacegunner.game;

import static com.example.spacegunner.constants.Constants.TAG;

import java.util.Random;

import android.os.Handler;
import android.util.Log;

public class GamePresenterImpl implements GamePresenter, Runnable {

	private GameModel model;
	private GameView view;
	private Handler handler;
	private Random random;
	private static final int THOUSAND_MS = 1000;
	private static final int TIME_BETWEEN_FRAMES = 50;
	private static final float DIFFICULTY_MODIFIER = 1.5f;

	public GamePresenterImpl(GameView view, int level, int points) {
		super();
		this.view = view;
		this.model = new GameModelImpl(level, points);
		this.handler = new Handler();
		this.random = new Random();
		startNextLevel();
	}

	@Override
	public void startNextLevel() {
		this.model.startNextLevel();
		Log.d(TAG, "Started level: " + model);
		this.view.showLevelStartInfo(model.getLevel());
		refreshScreen();
		this.handler.postDelayed(this, TIME_BETWEEN_FRAMES);
	}

	@Override
	public void run() {
		this.view.moveShipsToNewLocation(this.model.getSpeedModifier());
		this.view.increaseFrame();
		if (this.view.getFrame() >= THOUSAND_MS / TIME_BETWEEN_FRAMES) {
			countDown();
			this.view.resetFrame();
		}
		if (this.model.isGameOver()) {
			endGame();
		} else if (this.model.isLevelFinished()) {
			endLevel();
		} else {
			handler.postDelayed(this, TIME_BETWEEN_FRAMES);
		}
	}

	@Override
	public void countDown() {
		this.model.countdownTime();
		final float randomValue = this.random.nextFloat();
		// display 50 per cent more ships than necessary
		final double probability = this.model.getShipsToDestroy()
				* DIFFICULTY_MODIFIER / this.model.getSecondsPerLevel();
		// if the probability is above 1, two ships might have to be displayed
		if (probability > 1) {
			this.view.displayShip();
			if (randomValue < probability - 1) {
				this.view.displayShip();
			}
		} else if (randomValue < probability) {
			this.view.displayShip();
		}
		this.view.removeShips(this.model.getMaximumTimeShown());
		refreshScreen();
	}

	@Override
	public void refreshScreen() {
		this.view.setHits(this.model.getShipsDestroyed());
		this.view.setPoints(this.model.getPoints());
		this.view.setLevel(this.model.getLevel());
		this.view.setTime(this.model.getTime());
		this.view.setHitBarWidth(this.model.getShipsDestroyed(),
				this.model.getShipsToDestroy());
		this.view.setTimeBarWidth(this.model.getTime(),
				this.model.getSecondsPerLevel());
	}

	@Override
	public void shipDestroyed() {
		this.model.shipDestroyed();
		this.view.playExplosionSound();
		this.view.showShipDestroyedAnimation();
		refreshScreen();
	}

	@Override
	public void endLevel() {
		handler.removeCallbacks(this);
		this.view.startLevelView(this.model.getLevel(), this.model.getPoints());
	}

	@Override
	public void backButtonPressed() {
		handler.removeCallbacks(this);
		if (!model.isLevelFinished()) {
			final int currentLevel = this.model.getLevel();
			if (currentLevel == 1) {
				this.view.startGameResultView(this.model.getPoints());
			} else {
				this.view.returnToPreviousLevelView(this.model.getLevel() - 1,
						this.model.getPointsAtLevelStart());
			}
		}
	}

	@Override
	public void endGame() {
		Log.d(TAG, "Game over: " + this.model);
		this.view.startGameResultView(this.model.getPoints());
	}

}
