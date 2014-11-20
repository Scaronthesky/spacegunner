package com.example.spacegunner.gameresult;

public class GameResultModelImpl implements GameResultModel {

	private final int points; 
	
	public GameResultModelImpl(int points) {
		super();
		this.points = points;
	}

	@Override
	public int getPoints() {
		return this.points;
	}

}
