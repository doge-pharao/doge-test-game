package com.example.pattern;

import java.util.Random;

public class ZigZagMovement extends Movement {

	private int upLimit;
	private int downLimit;
	private boolean up;
	
	public ZigZagMovement(int screenHeigth, int screenWidth) {
		super(screenHeigth, screenWidth);
		upLimit = (new Random()).nextInt(screenHeigth / 2);
		downLimit = (screenHeigth / 2) + (new Random()).nextInt(screenHeigth / 2);
		up = (new Random()).nextBoolean();
	}

	@Override
	public void move() {
		int posX = this.getCurrentPosX();
		int posY = this.getCurrentPosY();
		int accelX = this.getCurrentAccelX();
		int accelY = this.getCurrentAccelY();
		
		posX = posX - accelX;
		
		posY = posY + ((up)?-accelY:accelY);
		
		if (posY <= upLimit && up) {
			posY = upLimit;
			upLimit = (new Random()).nextInt(screenHeigth / 2);
			up = false;
		}
		else
			if (posY >= downLimit && !up) {
				posY = downLimit;
				downLimit = (screenHeigth / 2) + (new Random()).nextInt(screenHeigth / 2);
				up = true;
			}
		
		this.setCurrentPosX(posX);
		this.setCurrentPosY(posY);
	}

}
