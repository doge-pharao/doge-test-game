package com.example.entity;

import org.andengine.entity.sprite.AnimatedSprite;
import com.example.pattern.Movement;

public abstract class Entity {
	protected final AnimatedSprite representation;
	protected final Movement movement;
	protected int posX;
	protected int posY;
	protected int accelX;
	protected int accelY;
	
	public Entity (AnimatedSprite representation, int initialPosX, int initialPosY, int accelX, int accelY, Movement movement) {
		this.representation = representation;
		this.posX = initialPosX;
		this.posY = initialPosY;
		this.accelX = accelX;
		this.accelY = accelY;
		
		movement.setEntity(this);
		this.movement = movement;
	}

	public abstract void update();

	public boolean collidesWith(Entity anotherEntity){
		return representation.collidesWith(anotherEntity.representation);
	}
	
	public AnimatedSprite getRepresentation(){
		return representation;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getAccelX() {
		return accelX;
	}

	public void setAccelX(int accelX) {
		this.accelX = accelX;
	}

	public int getAccelY() {
		return accelY;
	}

	public void setAccelY(int accelY) {
		this.accelY = accelY;
	}
}
