package com.example.pattern;

public class BouncingMovement extends Movement {


	private boolean up;

	public BouncingMovement(int screenHeigth, int screenWidth) {
		super(screenHeigth, screenWidth);
	}

	@Override
	public void move() {
		int posX = this.getCurrentPosX();
		int posY = this.getCurrentPosY();
		int accelX = this.getCurrentAccelX();
		int accelY = this.getCurrentAccelY();
		
		posX = posX - accelX;
		posY = posY + ((up)?-accelY:accelY);
		
		if (posY <= 0 && up) {
			posY = 0;
			up = false;
		}
		else
			if (posY >= screenHeigth && !up) {
				posY = screenHeigth;
				up = true;
			}
		
		this.setCurrentPosX(posX);
		this.setCurrentPosY(posY);
	}

}
