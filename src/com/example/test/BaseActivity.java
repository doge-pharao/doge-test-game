package com.example.test;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import android.graphics.Typeface;
import android.util.Log;

public class BaseActivity extends SimpleBaseGameActivity {

	static final int CAMERA_WIDTH = 800;
	static final int CAMERA_HEIGHT = 480;
	static final int SCORE_HEIGHT = 30;

	
	private BitmapTextureAtlas mAutoParallaxBackgroundTexture;
	public AutoParallaxBackground backgroundTexture ;
	public SpriteBackground dogeBackgroundTexture;

	
	public Font mFont;
	public Camera mCamera;
	public Scene mCurrentScene;
	public Font mScoreFont;
	private TextureRegion mDogeParallaxLayerFront;
	private TextureRegion mGameBackground;
	private BitmapTextureAtlas mBackgroundTexture;
	public Music music;
	public static BaseActivity instance;

	public static BaseActivity getSharedInstance() {
		return instance;
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		instance = this;
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(),
				mCamera);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("tiles/");
		FontFactory.setAssetBasePath("fonts/");

		mFont = FontFactory.create(this.getFontManager(),
				this.getTextureManager(), 256, 256,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		mFont.load();
		
		final ITexture scoreFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		this.mScoreFont = FontFactory.createFromAsset(this.getFontManager(), scoreFontTexture, this.getAssets(), "8_bit_party.ttf", 16, true, Color.WHITE_ARGB_PACKED_INT);
		this.mScoreFont.load();
		
		this.mBackgroundTexture = new BitmapTextureAtlas(
				this.getTextureManager(), 2048, 2048);
		this.mGameBackground = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBackgroundTexture, this,
						"back.gif", 0, 0);
		this.mDogeParallaxLayerFront = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBackgroundTexture, this, "doge.png", 0, 451);

		this.mBackgroundTexture.load();
		
		backgroundTexture = new AutoParallaxBackground(
				0, 0, 0, 5);
		
		backgroundTexture.attachParallaxEntity(new ParallaxEntity(-10.0f,
				new Sprite(0, SCORE_HEIGHT, this.mGameBackground,
						this.getVertexBufferObjectManager())));
		
		float centerX = ((CAMERA_WIDTH -
                mDogeParallaxLayerFront.getWidth()) / 2); 
		float centerY = (int) ((CAMERA_HEIGHT -
                mDogeParallaxLayerFront.getHeight()) / 2);
		
		dogeBackgroundTexture = new SpriteBackground(new Sprite(centerX, centerY, mDogeParallaxLayerFront,this.getVertexBufferObjectManager()));
     
		try {
			music = MusicFactory.createMusicFromAsset(this.getMusicManager(),
					this, "music/ballon.mp3");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected Scene onCreateScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
		mCurrentScene = new SplashScene();
		return mCurrentScene;
	}

	// to change the current main scene
	public void setCurrentScene(Scene scene) {
		mCurrentScene = null;
		mCurrentScene = scene;
		getEngine().setScene(mCurrentScene);
	}

	@Override
	public void onBackPressed() {
		Log.v("Jimvaders",
				"BaseActivity BackPressed " + mCurrentScene.toString());
		if (mCurrentScene instanceof GameScene)
			((GameScene) mCurrentScene).detachSelf();

		mCurrentScene = null;
		super.onBackPressed();
	}

	public boolean setupAccelerationSensor(IAccelerationListener pAccelerationListener){
		return super.enableAccelerationSensor(pAccelerationListener);
	}
}
