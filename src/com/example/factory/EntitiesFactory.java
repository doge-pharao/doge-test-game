package com.example.factory;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.example.entity.Enemy;
import com.example.entity.Player;
import com.example.pattern.Movement;
import com.example.pattern.PlayerMovement;
import com.example.pattern.ZigZagMovement;

public class EntitiesFactory {

	private static EntitiesFactory instance;
	
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private ITiledTextureRegion playerTextureRegion;

	private TiledTextureRegion turtleTextureRegion;
	private TiledTextureRegion fishTextureRegion;
	private TiledTextureRegion medusaTextureRegion;

	private SimpleBaseGameActivity context;

	
	public static EntitiesFactory getInstance (SimpleBaseGameActivity context){
		if (instance == null) { 
			instance = new EntitiesFactory(context);
		}
		
		return instance;
	}
	
	private EntitiesFactory(SimpleBaseGameActivity context){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("tiles/");
		this.context = context;
		
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(), 720, 128, TextureOptions.NEAREST_PREMULTIPLYALPHA);

		this.playerTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mBitmapTextureAtlas, context,
						"mario.png", 0, 0, 3, 1);
		this.turtleTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mBitmapTextureAtlas, context,
						"turtle.png", 0,30 ,2, 1);
		this.medusaTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mBitmapTextureAtlas, context,
						"medusa.png", 0, 58, 2, 1);
		this.fishTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mBitmapTextureAtlas, context,
						"fish.png", 0,79 ,2, 1);
		
		this.mBitmapTextureAtlas.load();
	}
	
	public Player getPlayer(PlayerMovement playerMovement){
		AnimatedSprite playerSprite = new AnimatedSprite(2, 2, this.playerTextureRegion, context.getVertexBufferObjectManager());
		long[] PLAYER_WALK_ANIMATE = new long[] { 100, 100, 100};
		playerSprite.setScale(2f);
		playerSprite.animate(PLAYER_WALK_ANIMATE, 0 , 2,true);
		
		return new Player(playerSprite, 0, 0, playerMovement);
	}
	
	public Enemy getEnemyMedusa(Movement movement){
		final AnimatedSprite enemy = new AnimatedSprite(0, 0,
				medusaTextureRegion,context.getVertexBufferObjectManager());
		
		enemy.animate(100);
		
		return new Enemy(enemy, 0, 0, movement);
	}
	
	public Enemy getEnemyFish(Movement movement){
		final AnimatedSprite enemy = new AnimatedSprite(0, 0,
				fishTextureRegion,context.getVertexBufferObjectManager());
		
		enemy.animate(100);
		
		return new Enemy(enemy, 0, 0, movement);
	}
	
	public Enemy getEnemyTurtle(Movement movement){
		final AnimatedSprite enemy = new AnimatedSprite(0, 0,
				turtleTextureRegion,context.getVertexBufferObjectManager());
		
		enemy.animate(100);
		
		return new Enemy(enemy, 0, 0, movement);
	}
	
}
