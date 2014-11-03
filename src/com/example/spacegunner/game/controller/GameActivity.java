package com.example.spacegunner.game.controller;

import static com.example.spacegunner.constants.Constants.TAG;

import java.util.Date;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spacegunner.R;
import com.example.spacegunner.game.model.GameModel;

@SuppressLint("RtlHardcoded")
public class GameActivity extends Activity implements OnClickListener, Runnable {

	private Handler handler;
	private GameModel model;
	private ViewGroup gameArea;
	private int displayWidth;
	private Random random;

	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		Log.d(TAG, "onCreate");
		setContentView(R.layout.game);
		this.handler = new Handler();
		this.model = new GameModel();
		this.gameArea = (ViewGroup) findViewById(R.id.gamearea);
		this.displayWidth = getResources().getDisplayMetrics().widthPixels;
		this.random = new Random();
		startGame();
	}

	private void startGame() {
		Log.d(TAG, "startGame");
		model.startGame();
		startNextLevel();
	}

	private void startNextLevel() {
		Log.d(TAG, "startNextLevel");
		this.model.startNextLevel();
		Log.d(TAG, "Started next level: " + model);
		Toast.makeText(this, "Starting level: " + this.model.getLevel(), Toast.LENGTH_LONG).show();
		refreshScreen();
	}

	@Override
	public void run() {
		countDown();
	}

	private void countDown() {
		this.model.countdownTime();
		final float randomValue = this.random.nextFloat();
		// display 50 per cent more ships than necessary
		final double probability = this.model.getShipsToDestroy() * 1.5f / GameModel.INTERVALL;
		// if the probability is above 1, two ships might have to be displayed
		if (probability > 1) {
			displayShip();
			if (randomValue < probability - 1) {
				displayShip();
			}
		} else if (randomValue < probability) {
			displayShip();
		}
		removeShips();
		refreshScreen();
		if (this.model.isGameOver()) {
			endGame();
		} else if (this.model.isLevelFinished()) {
			startNextLevel();
		} else {
			handler.postDelayed(this, 1000);
		}
	}

	private void refreshScreen() {
		// refresh text views with values from game model
		final TextView tvPoints = (TextView) findViewById(R.id.points);
		tvPoints.setText("Points: " + Integer.toString(this.model.getPoints()));
		final TextView tvLevel = (TextView) findViewById(R.id.level);
		tvLevel.setText("Level: " + Integer.toString(this.model.getLevel()));
		final TextView tvHits = (TextView) findViewById(R.id.hits);
		tvHits.setText("Destroyed: "
				+ Integer.toString(this.model.getShipsDestroyed()));
		final TextView tvTime = (TextView) findViewById(R.id.time);
		tvTime.setText("Time left: " + Integer.toString(this.model.getTime()));
		// refresh width of the hits and time bars
		final int textViewWidth = Math.round(this.displayWidth / 4);
		final FrameLayout flHits = (FrameLayout) findViewById(R.id.barhits);
		final LayoutParams lpHits = flHits.getLayoutParams();
		lpHits.width = Math.round((displayWidth - textViewWidth)
				* this.model.getShipsDestroyed()
				/ this.model.getShipsToDestroy());
		final FrameLayout flTime = (FrameLayout) findViewById(R.id.bartime);
		final LayoutParams lpTime = flTime.getLayoutParams();
		lpTime.width = Math.round((displayWidth - textViewWidth)
				* this.model.getTime() / GameModel.INTERVALL);
	}

	private void displayShip() {
		Log.d(TAG, "displayShip");
		final int gameAreaWidth = this.gameArea.getWidth();
		final int gameAreaHeight = this.gameArea.getHeight();
		final int shipWidth = (int) Math.round(getResources().getDrawable(
				R.drawable.spaceship).getMinimumWidth());
		final int shipHeight = (int) Math.round(getResources().getDrawable(
				R.drawable.spaceship).getMinimumHeight());
		final int shipXPos = random.nextInt(gameAreaWidth - shipWidth);
		Log.d(TAG, "ship x pos: " + shipXPos);
		final int shipYPos = random.nextInt(gameAreaHeight - shipHeight);
		Log.d(TAG, "ship y pos: " + shipYPos);
		final ImageView ship = new ImageView(this);
		ship.setImageResource(R.drawable.spaceship);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				shipWidth, shipHeight);
		params.leftMargin = shipXPos;
		params.topMargin = shipYPos;
		params.gravity = Gravity.TOP + Gravity.LEFT;
		ship.setTag(R.id.displaydate, new Date());
		ship.setOnClickListener(this);
		this.gameArea.addView(ship, params);
	}

	@Override
	public void onClick(View ship) {
		Log.d(TAG, "shipDestroyed");
		this.model.shipDestroyed();
		this.gameArea.removeView(ship);
		refreshScreen();
	}

	// TODO Use joda time
	private void removeShips() {
		int counter = 0;
		while (counter < this.gameArea.getChildCount()) {
			ImageView ship = (ImageView) gameArea.getChildAt(counter);
			Date displayDate = (Date) ship.getTag(R.id.displaydate);
			long timeShown = (new Date()).getTime() - displayDate.getTime();
			if (timeShown > GameModel.MAXIMUM_TIME_SHOWN) {
				Log.d(TAG, "Removing ship");
				this.gameArea.removeView(ship);
			} else {
				counter++;
			}
		}
	}

	private void endGame() {
		this.model.stopGame();
		Dialog dialog = new Dialog(this,
				android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		dialog.setContentView(R.layout.gameover);
		dialog.show();
	}
}
