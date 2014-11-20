package com.example.spacegunner.highscore;

import static com.example.spacegunner.constants.Constants.POINTS;
import static com.example.spacegunner.constants.Constants.TAG;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spacegunner.R;
import com.example.spacegunner.ioservice.IOService;
import com.example.spacegunner.ioservice.PlayerHighscore;
import com.example.spacegunner.main.MainViewImpl;

public class HighscoreViewImpl extends Activity implements HighscoreView,
		OnClickListener {

	private HighscorePresenter presenter;
	private Button buttonSaveName;
	private int highscore;
	IOService ioService;

	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		Log.d(TAG, "Creating HighscoreActivity");
		setContentView(R.layout.highscore);
		this.presenter = new HighscorePresenterImpl(this);
		this.ioService = new IOService(this);
		final int defaultScore = 0;
		this.highscore = getIntent().getIntExtra(POINTS, defaultScore);
		this.buttonSaveName = (Button) findViewById(R.id.buttonsavename);
		this.buttonSaveName.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		final TextView textViewName = (TextView) findViewById(R.id.textname);
		final String playerName = textViewName.getText().toString().trim();
		final PlayerHighscore playerHighscore = new PlayerHighscore(playerName,
				this.highscore);
		this.presenter.saveHighscoreButtonClicked(playerHighscore);
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
	public void startMainActivity() {
		startActivity(new Intent(this, MainViewImpl.class));
	}

}
