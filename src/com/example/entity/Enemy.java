package com.example.entity;

import org.andengine.entity.sprite.AnimatedSprite;

import com.example.pattern.Movement;

public class Enemy extends Entity{

	public Enemy (AnimatedSprite representation, int initialPosX, int initialPosY){
		this(representation, initialPosX, initialPosY, null);
	}
	
	public Enemy (AnimatedSprite representation, int initialPosX, int initialPosY, Movement movement){
		super(representation, initialPosX, initialPosY, 0, 0, movement);
		
		representation.setScale(2f);
	}


	@Override
	public void update() {
		if (movement != null)
			movement.move();
		
		representation.setPosition(posX , posY);
	}

}
