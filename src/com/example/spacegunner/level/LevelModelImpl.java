package com.example.spacegunner.level;

public class LevelModelImpl implements LevelModel {

	private final int level;

	private final int points; 
	
	public LevelModelImpl(int level, int points) {
		super();
		this.level = level;
		this.points = points;
	}

	@Override
	public int getLevel() {
		return this.level;
	}

	@Override
	public int getPoints() {
		return this.points;
	}

}
