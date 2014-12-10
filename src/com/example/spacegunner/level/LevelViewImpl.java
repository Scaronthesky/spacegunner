package com.example.spacegunner.level;

import static com.example.spacegunner.constants.Constants.TAG;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.spacegunner.R;
import com.example.spacegunner.constants.Constants;
import com.example.spacegunner.game.GameViewImpl;
import com.example.spacegunner.gameresult.GameResultViewImpl;
import com.example.spacegunner.ioservice.IOService;
import com.example.spacegunner.ioservice.PlayerHighscore;
import com.example.spacegunner.main.MainViewImpl;

public class LevelViewImpl extends Activity implements LevelView,
		OnClickListener {

	private LevelPresenter presenter;
	
	private IOService ioService;
	
	private Button buttonContinueGame;
	private Button buttonQuitGame;
	private Animation fadeIn;

	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		Log.d(TAG, "Creating LevelView");
		setContentView(R.layout.level);
		final int defaultLevel = 0;
		final int level = getIntent()
				.getIntExtra(Constants.LEVEL, defaultLevel);
		final int defaultPoints = 0;
		final int points = getIntent().getIntExtra(Constants.POINTS,
				defaultPoints);
		this.presenter = new LevelPresenterImpl(this, level, points);
		this.ioService = new IOService(this);
		this.buttonContinueGame = (Button) findViewById(R.id.buttoncontinuegame);
		this.buttonContinueGame.setOnClickListener(this);
		this.buttonQuitGame = (Button) findViewById(R.id.buttonquitgame);
		this.buttonQuitGame.setOnClickListener(this);
		this.fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
	}

	@Override
	public void onResume() {
		super.onResume();
		View gameResultBackground = findViewById(R.id.level_background);
		gameResultBackground.startAnimation(fadeIn);
	}
	
	@Override
	public void displayLevelAndPoints(int level, int points) {
		TextView levelText = (TextView) findViewById(R.id.labellevel);
		levelText.setText(getResources().getString(
				R.string.current_level) + level);
		TextView levelPoints = (TextView) findViewById(R.id.labelpoints);
		levelPoints.setText(getResources().getString(
				R.string.current_points) + points);
	}

	@Override
	public void onClick(View button) {
		if (button.getId() == R.id.buttoncontinuegame) {
			this.presenter.continueGameButtonClicked();
		} else if (button.getId() == R.id.buttonquitgame) {
			this.presenter.quitGameButtonClicked();
		}
	}

	@Override
	public PlayerHighscore readHighscore() {
		return this.ioService.readHighscore();
	}

	@Override
	public void startGameView(final int level, final int points) {
		Intent intent = new Intent(this, GameViewImpl.class);
		intent.putExtra(Constants.LEVEL, level);
		intent.putExtra(Constants.POINTS, points);
		startActivity(intent);
	}

	@Override
	public void startGameResultView(int points) {
		Intent intent = new Intent(this, GameResultViewImpl.class);
		intent.putExtra(Constants.POINTS, points);
		startActivity(intent);
	}

	@Override
	public void startMainView() {
		startActivity(new Intent(this, MainViewImpl.class));
	}

	@Override
	public void onBackPressed() {
		this.presenter.backButtonPressed();
	}
}
