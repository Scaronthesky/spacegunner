package com.example.spacegunner.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.spacegunner.R;
import com.example.spacegunner.game.GameViewImpl;
import com.example.spacegunner.ioservice.IOService;
import com.example.spacegunner.ioservice.PlayerHighscore;

public class MainViewImpl extends Activity implements OnClickListener, MainView {

	private MainPresenter presenter;
	private IOService iOService;
	private Animation fadeIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.presenter = new MainPresenterImpl(this);
		this.iOService = new IOService(this);
		this.fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
		final Button button = (Button) findViewById(R.id.buttonsavehighscore);
		button.setOnClickListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		View background = findViewById(R.id.background);
		background.startAnimation(fadeIn);
		displayHighscore();
	}

	private void displayHighscore() {
		TextView highscorelist = (TextView) findViewById(R.id.highscorelist);
		PlayerHighscore playerHighscore = iOService.readHighscore();
		highscorelist.setText(playerHighscore.getPlayerName() + ": " + playerHighscore.getHighscore());
	}

	@Override
	public void onClick(View v) {
		this.presenter.startGameButtonClicked();
	}

	@Override
	public void startGameActivity() {
		startActivity(new Intent(this, GameViewImpl.class));
	}

}
