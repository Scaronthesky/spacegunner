package com.example.spacegunner.gameresult;

import static com.example.spacegunner.constants.Constants.POINTS;
import static com.example.spacegunner.constants.Constants.TAG;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spacegunner.R;
import com.example.spacegunner.ioservice.IOService;
import com.example.spacegunner.ioservice.PlayerHighscore;
import com.example.spacegunner.main.MainViewImpl;

public class GameResultViewImpl extends Activity implements GameResultView,
		OnClickListener {

	private GameResultPresenter presenter;
	private Button buttonSaveHighscore;
	private Button buttonReturnMainView;
	IOService ioService;

	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		Log.d(TAG, "Creating HighscoreView");
		setContentView(R.layout.gameresult);
		this.ioService = new IOService(this);
		final int defaultScore = 0;
		final int highscore = getIntent().getIntExtra(POINTS, defaultScore);
		this.presenter = new GameResultPresenterImpl(this, highscore);
		this.buttonSaveHighscore = (Button) findViewById(R.id.buttonsavehighscore);
		this.buttonSaveHighscore.setOnClickListener(this);
		this.buttonReturnMainView = (Button) findViewById(R.id.buttonreturnmain);
		this.buttonReturnMainView.setOnClickListener(this);
	}

	@Override
	public void hideSaveHighscoreLayout() {
		LinearLayout layout = (LinearLayout) findViewById(R.id.layoutHighscore);
		layout.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View button) {
		if (button.getId() == R.id.buttonsavehighscore) {
			final TextView textViewName = (TextView) findViewById(R.id.textname);
			final String playerName = textViewName.getText().toString().trim();
			this.presenter.saveHighscoreButtonClicked(playerName);
		} else if (button.getId() == R.id.buttonreturnmain) {
			this.presenter.buttonReturnMainViewClicked();
		}

	}

	@Override
	public PlayerHighscore readHighscore() {
		return this.ioService.readHighscore();
	}

	@Override
	public void saveHighscore(final PlayerHighscore playerHighscore) {
		this.ioService.saveHighscore(playerHighscore);
	}

	@Override
	public void showHighscoreSavedToast() {
		Toast.makeText(this,
				getResources().getString(R.string.highscore_saved),
				Toast.LENGTH_SHORT).show();
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
