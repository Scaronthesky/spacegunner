package com.example.spacegunner.level;

import static com.example.spacegunner.constants.Constants.TAG;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.spacegunner.R;
import com.example.spacegunner.game.GameViewImpl;
import com.example.spacegunner.ioservice.IOService;
import com.example.spacegunner.main.MainViewImpl;

public class LevelViewImpl extends Activity implements LevelView,
		OnClickListener {

	private LevelPresenter presenter;
	private Button buttonContinueGame;
	private Button buttonQuitGame;
	private int highscore;
	IOService ioService;

	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		Log.d(TAG, "Creating HighscoreActivity");
		// TODO: Create new layout
		 setContentView(R.layout.highscore);
		this.presenter = new LevelPresenterImpl(this);
		// TODO: Replace with new ids
		this.buttonContinueGame = (Button) findViewById(R.id.buttonsavename);
		this.buttonContinueGame.setOnClickListener(this);
		// TODO: Replace with new ids
		this.buttonQuitGame = (Button) findViewById(R.id.buttonsavename);
		this.buttonQuitGame.setOnClickListener(this);
	}

	@Override
	public void onClick(View button) {
		// TODO: Replace with new ids
		if (button.getId() == R.id.buttonsavename) {
			this.presenter.continueGameButtonClicked();
			// TODO: Replace with new ids
		} else if (button.getId() == R.id.buttonsavename) {
			this.presenter.quitGameButtonClicked();
		}
	}

	@Override
	public void startMainActivity() {
		startActivity(new Intent(this, MainViewImpl.class));
	}

	@Override
	public void startGameActivity() {
		startActivity(new Intent(this, GameViewImpl.class));
	}

}
