package com.example.pattern;

import com.example.entity.Entity;

public abstract class Movement {
	
	protected Entity entityToMove;
	protected int screenHeigth;
	protected int screenWidth;
	
	public abstract void move();
	
	public Movement(Entity entity, int screenHeigth, int screenWidth){
		this(screenHeigth, screenWidth);
		this.entityToMove = entity;
	}
	
	public Movement(int screenHeigth, int screenWidth){
		this.entityToMove = null;
		this.screenHeigth = screenHeigth;
		this.screenWidth = screenWidth;
	}
	
	protected int getCurrentPosX(){
		return this.entityToMove.getPosX();
	}
	
	protected int getCurrentPosY(){
		return this.entityToMove.getPosY();
	}
	
	protected int getCurrentAccelX(){
		return this.entityToMove.getAccelX();
	}
	
	protected int getCurrentAccelY(){
		return this.entityToMove.getAccelY();
	}
	
	protected int getScreenHeigth(){
		return this.screenHeigth;
	}
	
	protected int getScreenwidth(){
		return this.screenWidth;
	}
	
	protected void setCurrentPosX(int x){
		this.entityToMove.setPosX(x);
	}

	protected void setCurrentPosY(int y){
		this.entityToMove.setPosY(y);
	}
	
	protected void setCurrentAccelX(int x){
		this.entityToMove.setAccelX(x);
	}
	
	protected void setCurrentAccelY(int y){
		this.entityToMove.setAccelY(y);
	}

	public void setEntity(Entity entity) {
		this.entityToMove = entity;
	}
}
