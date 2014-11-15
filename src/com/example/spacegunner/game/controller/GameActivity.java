package com.example.spacegunner.game.controller;

import static com.example.spacegunner.constants.Constants.TAG;

import java.util.Date;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
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
	private MediaPlayer mediaPlayer;
	private long frame;
	private static final int INTERVALL_MS = 50;
	private static final String SPACESHIP_IMAGE = "spaceship_";
	private static final String CARDINAL_DIRECTIONS[][] = {
			{ "nw", "n", "ne" }, { "w", "", "e" }, { "sw", "s", "se" } };

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
		this.mediaPlayer = MediaPlayer.create(this, R.raw.explosion);
		startGame();
	}

	private void startGame() {
		Log.d(TAG, "startGame");
		startNextLevel();
	}

	private void startNextLevel() {
		Log.d(TAG, "startNextLevel");
		this.frame = 0;
		this.model.startNextLevel();
		Log.d(TAG, "Started next level: " + model);
		Toast.makeText(this, "Starting level: " + this.model.getLevel(),
				Toast.LENGTH_SHORT).show();
		refreshScreen();
		handler.postDelayed(this, INTERVALL_MS);
	}

	@Override
	public void run() {
		moveShipsToNewLocation();
		this.frame++;
		if (frame >= 1000 / INTERVALL_MS) {
			countDown();
			frame = 0;
		}
		if (this.model.isGameOver()) {
			endGame();
		} else if (this.model.isLevelFinished()) {
			startNextLevel();
		} else {
			handler.postDelayed(this, INTERVALL_MS);
		}
	}

	private void countDown() {
		this.model.countdownTime();
		final float randomValue = this.random.nextFloat();
		// display 50 per cent more ships than necessary
		final double probability = this.model.getShipsToDestroy() * 1.5f
				/ GameModel.SECONDS_PER_LEVEL;
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
				* this.model.getTime() / GameModel.SECONDS_PER_LEVEL);
	}

	private void displayShip() {
		final int gameAreaWidth = this.gameArea.getWidth();
		final int gameAreaHeight = this.gameArea.getHeight();
		final int shipWidth = (int) Math.round(getResources().getDrawable(
				R.drawable.spaceship).getMinimumWidth());
		final int shipHeight = (int) Math.round(getResources().getDrawable(
				R.drawable.spaceship).getMinimumHeight());
		final int shipXPos = random.nextInt(gameAreaWidth - shipWidth);
		final int shipYPos = random.nextInt(gameAreaHeight - shipHeight);
		int vektorX, vektorY;
		do {
			vektorX = this.random.nextInt(3) - 1;
			vektorY = this.random.nextInt(3) - 1;
		} while (vektorX == 0 && vektorY == 0);
		double correctionalFactor = 1.0;
		if (vektorX != 0 && vektorY != 0) {
			correctionalFactor = 0.70710678;
		}
		vektorX = (int) Math.round(vektorX * correctionalFactor);
		vektorY = (int) Math.round(vektorY * correctionalFactor);
		final ImageView ship = new ImageView(this);
		setShipImage(ship, vektorX, vektorY);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				shipWidth, shipHeight);
		params.leftMargin = shipXPos;
		params.topMargin = shipYPos;
		params.gravity = Gravity.TOP + Gravity.LEFT;
		ship.setTag(R.id.displaydate, new Date());
		ship.setTag(R.id.vx, Integer.valueOf(vektorX));
		ship.setTag(R.id.vy, Integer.valueOf(vektorY));
		this.gameArea.addView(ship, params);
		Animation quickFadeIn = AnimationUtils.loadAnimation(this,
				R.anim.quickfadein);
		ship.startAnimation(quickFadeIn);
		ship.setOnClickListener(this);
	}

	private void setShipImage(ImageView ship, int vektorX, int vektorY) {
		ship.setImageResource(getResources()
				.getIdentifier(
						SPACESHIP_IMAGE
								+ CARDINAL_DIRECTIONS[vektorY + 1][vektorX + 1],
						"drawable", this.getPackageName()));
	}

	private void moveShipsToNewLocation() {
		int counter = 0;
		while (counter < this.gameArea.getChildCount()) {
			ImageView ship = (ImageView) gameArea.getChildAt(counter);
			Integer vektorX = (Integer) ship.getTag(R.id.vx);
			Integer vektorY = (Integer) ship.getTag(R.id.vy);
			FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) ship
					.getLayoutParams();
			params.leftMargin += vektorX * this.model.getLevel();
			params.topMargin += vektorY * this.model.getLevel();
			ship.setLayoutParams(params);
			counter++;
		}
	}

	@Override
	public void onClick(View ship) {
		ImageView destroyedShip = (ImageView) ship;
		destroyedShip.setOnClickListener(null);
		this.model.shipDestroyed();
		this.mediaPlayer.seekTo(0);
		this.mediaPlayer.start();
		final Animation hit = AnimationUtils.loadAnimation(this, R.anim.hit);
		hit.setAnimationListener(new HitAnimationListener(destroyedShip));
		destroyedShip.startAnimation(hit);
		refreshScreen();
	}

	private class HitAnimationListener implements AnimationListener {
		private ImageView destroyedShip;

		public HitAnimationListener(ImageView destroyedShip) {
			this.destroyedShip = destroyedShip;
		}

		@Override
		public void onAnimationStart(Animation animation) {
			destroyedShip.setImageResource(R.drawable.explosion);		
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			handler.post(new Runnable() {
				@Override
				public void run() {
					gameArea.removeView(destroyedShip);
				}
			});
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}
	}

	// TODO Use joda time
	private void removeShips() {
		int counter = 0;
		while (counter < this.gameArea.getChildCount()) {
			ImageView ship = (ImageView) gameArea.getChildAt(counter);
			Date displayDate = (Date) ship.getTag(R.id.displaydate);
			long timeShown = (new Date()).getTime() - displayDate.getTime();
			if (timeShown > GameModel.MAXIMUM_TIME_SHOWN) {
				Animation quickFadeOut = AnimationUtils.loadAnimation(this,
						R.anim.quickfadeout);
				ship.startAnimation(quickFadeOut);
				this.gameArea.removeView(ship);
			} else {
				counter++;
			}
		}
	}

	private void endGame() {
		Log.d(TAG, "Game over: " + this.model);
		Dialog dialog = new Dialog(this,
				android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		dialog.setContentView(R.layout.gameover);
		dialog.show();
		setResult(this.model.getPoints());
	}

	@Override
	protected void onPause() {
		super.onPause();
		handler.removeCallbacks(this);
	}

	@Override
	protected void onDestroy() {
		mediaPlayer.release();
		super.onDestroy();
	}
}
