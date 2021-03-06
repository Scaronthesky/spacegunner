package com.example.spacegunner.game;

import static com.example.spacegunner.constants.Constants.TAG;

import java.util.Date;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spacegunner.R;
import com.example.spacegunner.constants.Constants;
import com.example.spacegunner.gameresult.GameResultViewImpl;
import com.example.spacegunner.ioservice.IOService;
import com.example.spacegunner.ioservice.PlayerHighscore;
import com.example.spacegunner.level.LevelViewImpl;
import com.example.spacegunner.main.MainViewImpl;

@SuppressLint("RtlHardcoded")
public class GameViewImpl extends Activity implements OnClickListener, GameView {

	private GamePresenter presenter;

	private ViewGroup gameArea;
	private int displayWidth;
	private long frame;
	private Random random;
	private ImageView destroyedShip;
	private Animation fadeIn;

	private IOService ioService;
	private MediaPlayer mediaPlayer;

	private static final String SPACESHIP_IMAGE = "spaceship_";
	private static final String BACKGROUND_IMAGE = "background_";
	private static final String CARDINAL_DIRECTIONS[][] = {
			{ "nw", "n", "ne" }, { "w", "", "e" }, { "sw", "s", "se" } };

	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		Log.d(TAG, "Creating GameView");
		setContentView(R.layout.game);
		this.gameArea = (ViewGroup) findViewById(R.id.gamearea);
		this.displayWidth = getResources().getDisplayMetrics().widthPixels;
		this.random = new Random();
		this.mediaPlayer = MediaPlayer.create(this, R.raw.explosion);
		this.frame = 0;
		this.ioService = new IOService(this);
		final int defaultLevel = 0;
		final int level = getIntent()
				.getIntExtra(Constants.LEVEL, defaultLevel);
		final int defaultPoints = 0;
		final int points = getIntent().getIntExtra(Constants.POINTS,
				defaultPoints);
		this.fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
		this.presenter = new GamePresenterImpl(this, level, points);
	}

	@Override
	public void setBackgroundImageAndCredits(int level) {
		final LinearLayout backgroundLayout = (LinearLayout) findViewById(R.id.game_background);
		final Drawable backgroundImage;
		final TextView textCredit = (TextView) findViewById(R.id.text_credits);
		String credits = getResources().getString(R.string.image_credits);
		if (level <= 0) {
			throw new IllegalArgumentException(
					"Background image cannot be set for level <= 0.");
		} else if (level > 10) {
			backgroundImage = getResources().getDrawable(R.drawable.background);
			credits += getResources().getString(R.string.background_1);

		} else {
			final String identifier = BACKGROUND_IMAGE
					+ Integer.toString(level);
			backgroundImage = getResources().getDrawable(
					getResources().getIdentifier(identifier, "drawable",
							this.getPackageName()));
			credits += getResources().getString(
					getResources().getIdentifier(identifier, "string",
							this.getPackageName()));
		}
		backgroundLayout.setBackground(backgroundImage);
		textCredit.setText(credits);
		backgroundLayout.startAnimation(fadeIn);
	}

	@Override
	public void showLevelStartInfo(int level) {
		Toast.makeText(this, "Starting level: " + level, Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void increaseFrame() {
		this.frame++;
	}

	@Override
	public long getFrame() {
		return frame;
	}

	@Override
	public void resetFrame() {
		this.frame = 0;
	}

	@Override
	public void setTime(final int time) {
		final TextView tvTime = (TextView) findViewById(R.id.time);
		tvTime.setText("Time left: " + Integer.toString(time));
	}

	@Override
	public void setHits(final int shipsDestroyed) {
		final TextView tvHits = (TextView) findViewById(R.id.hits);
		tvHits.setText("Destroyed: " + Integer.toString(shipsDestroyed));
	}

	@Override
	public void setLevel(final int level) {
		final TextView tvLevel = (TextView) findViewById(R.id.level);
		tvLevel.setText("Level: " + Integer.toString(level));
	}

	@Override
	public void setPoints(final int points) {
		final TextView tvPoints = (TextView) findViewById(R.id.points);
		tvPoints.setText("Points: " + Integer.toString(points));
	}

	@Override
	public void setTimeBarWidth(final int time, final int secondsPerLevel) {
		final int textViewWidth = Math.round(this.displayWidth / 4);
		final FrameLayout flTime = (FrameLayout) findViewById(R.id.bartime);
		final LayoutParams lpTime = flTime.getLayoutParams();
		lpTime.width = Math.round((displayWidth - textViewWidth) * time
				/ secondsPerLevel);
	}

	@Override
	public int setHitBarWidth(final int shipsDestroyed, final int shipsToDestroy) {
		final int textViewWidth = Math.round(this.displayWidth / 4);
		final FrameLayout flHits = (FrameLayout) findViewById(R.id.barhits);
		final LayoutParams lpHits = flHits.getLayoutParams();
		lpHits.width = Math.round((displayWidth - textViewWidth)
				* shipsDestroyed / shipsToDestroy);
		return textViewWidth;
	}

	@Override
	public void displayShip() {
		final int gameAreaWidth = this.gameArea.getWidth();
		final int gameAreaHeight = this.gameArea.getHeight();
		final int shipWidth = (int) Math.round(getResources().getDrawable(
				R.drawable.spaceship_n).getMinimumWidth());
		final int shipHeight = (int) Math.round(getResources().getDrawable(
				R.drawable.spaceship_n).getMinimumHeight());
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

	@Override
	public void moveShipsToNewLocation(final int speedModifier) {
		int counter = 0;
		while (counter < this.gameArea.getChildCount()) {
			ImageView ship = (ImageView) gameArea.getChildAt(counter);
			Integer vektorX = (Integer) ship.getTag(R.id.vx);
			Integer vektorY = (Integer) ship.getTag(R.id.vy);
			FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) ship
					.getLayoutParams();
			params.leftMargin += vektorX * speedModifier;
			params.topMargin += vektorY * speedModifier;
			ship.setLayoutParams(params);
			counter++;
		}
	}

	@Override
	public void onClick(View ship) {
		this.destroyedShip = (ImageView) ship;
		destroyedShip.setOnClickListener(null);
		this.presenter.shipDestroyed();
	}

	@Override
	public void playExplosionSound() {
		this.mediaPlayer.seekTo(0);
		this.mediaPlayer.start();
	}

	@Override
	public void showShipDestroyedAnimation() {
		final Animation hit = AnimationUtils.loadAnimation(this, R.anim.hit);
		hit.setAnimationListener(new HitAnimationListener(destroyedShip));
		destroyedShip.startAnimation(hit);
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
			gameArea.removeView(destroyedShip);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}
	}

	// TODO Use joda time
	@Override
	public void removeShips(final int maximumTimeShown) {
		int counter = 0;
		while (counter < this.gameArea.getChildCount()) {
			ImageView ship = (ImageView) gameArea.getChildAt(counter);
			Date displayDate = (Date) ship.getTag(R.id.displaydate);
			long timeShown = (new Date()).getTime() - displayDate.getTime();
			if (timeShown > maximumTimeShown) {
				Animation quickFadeOut = AnimationUtils.loadAnimation(this,
						R.anim.quickfadeout);
				ship.startAnimation(quickFadeOut);
				this.gameArea.removeView(ship);
			} else {
				counter++;
			}
		}
	}

	@Override
	public PlayerHighscore getHighscore() {
		return ioService.readHighscore();
	}

	@Override
	public void startMainView() {
		startActivity(new Intent(this, MainViewImpl.class));
	}

	@Override
	public void startLevelView(int level, int points) {
		Intent intent = new Intent(this, LevelViewImpl.class);
		intent.putExtra(Constants.LEVEL, level);
		intent.putExtra(Constants.POINTS, points);
		startActivity(intent);
	}

	@Override
	public void returnToPreviousLevelView(int previousLevel,
			int pointsAtLevelStart) {
		startLevelView(previousLevel, pointsAtLevelStart);

	}

	@Override
	public void startGameResultView(final int points) {
		Intent intent = new Intent(this, GameResultViewImpl.class);
		intent.putExtra(Constants.POINTS, points);
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		this.presenter.backButtonPressed();
	}

	@Override
	protected void onPause() {
		super.onPause();
		this.presenter.gamePaused();
	}

	@Override
	protected void onDestroy() {
		mediaPlayer.release();
		super.onDestroy();
	}
	

}
