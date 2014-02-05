package com.example.pattern;

public class LinealMovement extends Movement {

	public LinealMovement(int screenHeigth, int screenWidth) {
		super(screenHeigth, screenWidth);
	}

	@Override
	public void move() {
		int posX = this.getCurrentPosX();
		int accelX = this.getCurrentAccelX();
		
		posX = posX - accelX;
		
		this.setCurrentPosX(posX);
	}

}
