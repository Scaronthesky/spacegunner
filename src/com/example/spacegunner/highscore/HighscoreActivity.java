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

import com.example.spacegunner.R;
import com.example.spacegunner.ioservice.IOService;
import com.example.spacegunner.main.MainActivity;

public class HighscoreActivity extends Activity implements OnClickListener {

	private Button buttonSaveName;
	private int highscore;
	IOService ioService;

	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		Log.d(TAG, "Creating HighscoreActivity");
		setContentView(R.layout.highscore);
		this.ioService  = new IOService(this);
		final int defaultScore = 0;
		this.highscore = getIntent().getIntExtra(POINTS, defaultScore);
		if (this.highscore == defaultScore) {
			throw new IllegalStateException("New highscore passed to this activity is 0.");
		}
		this.buttonSaveName = (Button) findViewById(R.id.buttonsavename);
		this.buttonSaveName.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		final TextView textViewName = (TextView) findViewById(R.id.textname);
		final String playerName = textViewName.getText().toString().trim();
		this.ioService.saveHighscore(playerName, this.highscore);
		startActivity(new Intent(this,MainActivity.class));
	}

}
