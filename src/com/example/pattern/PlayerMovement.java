package com.example.pattern;

public class PlayerMovement extends Movement {

	public PlayerMovement(int screenHeigth, int screenWidth) {
		super(screenHeigth, screenWidth);
	}

	@Override
	public void move() {
		int posX = this.getCurrentPosX();
		int posY = this.getCurrentPosY();
		int accelX = this.getCurrentAccelX();
		int accelY = this.getCurrentAccelY();
		
		posX += accelX;
		posY += accelY;

		if (posX <= 0) posX = 0;
		if (posX + entityToMove.getRepresentation().getWidth() >= screenWidth) posX = screenWidth - (int) entityToMove.getRepresentation().getWidth();

		if (posY <= 30) posY = 30;
		if (posY + entityToMove.getRepresentation().getHeight() >= screenHeigth) posY = screenHeigth - (int) entityToMove.getRepresentation().getHeight();

		this.setCurrentPosX(posX);
		this.setCurrentPosY(posY);
	}

}
