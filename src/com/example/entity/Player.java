package com.example.entity;

import org.andengine.entity.sprite.AnimatedSprite;

import com.example.pattern.Movement;

public class Player extends Entity {

	public Player (AnimatedSprite representation, int initialPosX, int initialPosY){
		this(representation, initialPosX, initialPosY, null);
	}
	
	public Player (AnimatedSprite representation, int initialPosX, int initialPosY, Movement movement){
		super(representation, initialPosX, initialPosY, 0, 0, movement);
		
		representation.setScale(2f);
	}
	
	@Override
	public void update(){
		movement.move();
		
		representation.setScaleX((accelX>0)?1.0f:-1.0f);
		representation.setPosition(posX, posY);	
	}

}
