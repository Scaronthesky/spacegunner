package com.example.spacegunner.ioservice;

import android.content.SharedPreferences;

import com.example.spacegunner.constants.Constants;
import com.example.spacegunner.main.controller.MainActivity;

public class IOService {

	private final SharedPreferences sharedPreferences;

	public IOService(MainActivity mainActivity) {
		this.sharedPreferences = mainActivity.getSharedPreferences(
				Constants.GAME, MainActivity.MODE_PRIVATE);
	}

	public int readHighscore() {
		final int defaultValue = 0;
		return sharedPreferences.getInt(Constants.HIGHSCORE, defaultValue);
	}

	public void writeHighscore(int highscore) {
		SharedPreferences.Editor editor = this.sharedPreferences.edit();
		editor.putInt(Constants.HIGHSCORE, highscore);
		editor.commit();
	}

}
