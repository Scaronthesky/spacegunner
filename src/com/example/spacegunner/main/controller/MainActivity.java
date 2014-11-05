package com.example.spacegunner.main.controller;

import com.example.spacegunner.R;
import com.example.spacegunner.game.controller.GameActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private Animation fadeIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
		final Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		View background = findViewById(R.id.background);
		background.startAnimation(fadeIn);
	}
	
	@Override
	public void onClick(View v) {
		startActivity(new Intent(this, GameActivity.class));
	}
	
	

}
