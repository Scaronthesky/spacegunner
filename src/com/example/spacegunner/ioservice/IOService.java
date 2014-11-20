package com.example.spacegunner.ioservice;

import static com.example.spacegunner.constants.Constants.GAME;
import static com.example.spacegunner.constants.Constants.HIGHSCORE;
import static com.example.spacegunner.constants.Constants.PLAYERNAME;

import com.example.spacegunner.R;

import android.app.Activity;
import android.content.SharedPreferences;

public class IOService {

	private final SharedPreferences sharedPreferences;
	private final Activity activity;
	
	public IOService(final Activity activity) {
		this.activity = activity;
		this.sharedPreferences = this.activity.getSharedPreferences(GAME,
				Activity.MODE_PRIVATE);
	}

	public PlayerHighscore readHighscore() {
		final String defaultString = this.activity.getResources().getString(R.string.no_highscore);
		final String playerName = sharedPreferences.getString(PLAYERNAME,
				defaultString);
		final int defaultScore = 0;
		final int highscore = sharedPreferences.getInt(HIGHSCORE, defaultScore);
		return new PlayerHighscore(playerName, highscore);
	}

	public void saveHighscore(PlayerHighscore playerHighscore) {
		final SharedPreferences.Editor editor = this.sharedPreferences.edit();
		editor.putString(PLAYERNAME, playerHighscore.getPlayerName());
		editor.putInt(HIGHSCORE, playerHighscore.getHighscore());
		editor.commit();
	}

}
