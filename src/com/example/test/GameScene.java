package com.example.test;

import java.util.LinkedList;
import java.util.Random;

import org.andengine.audio.music.Music;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;
import com.example.entity.Enemy;
import com.example.entity.Player;
import com.example.factory.EntitiesFactory;
import com.example.pattern.BouncingMovement;
import com.example.pattern.LinealMovement;
import com.example.pattern.PlayerMovement;
import com.example.pattern.ZigZagMovement;

public class GameScene extends Scene implements
		IAccelerationListener {
	private final int[] ACCEL_BY_STAGE = new int[] { 1, 2, 3, 5, 7, 10};
	private final int[] ENEMY_BY_STAGE = new int[] { 10, 20, 40, 50, 70, 100};

	private static final int SCENE_WIDTH = 720;
	private static final int SCENE_HEIGHT = BaseActivity.CAMERA_HEIGHT - BaseActivity.SCORE_HEIGHT;
	private Camera camera;

	private Scene scene;
	

	private Music music;

	private Player player;
	private LinkedList<Enemy> metroidList;
	private EntitiesFactory entitiesFactory;

	private int stage = 0;
	private int score = 0;
	private int lives = 3;
	private int passedEnemy = 0;

	/*
	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(),
				camera);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		
		
		return engineOptions;
	}*/
/*
	@Override
	protected void onCreateResources() {
		
		
		this.mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(
				this.getTextureManager(), 1024, 1024);
		this.mParallaxLayerFront = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mAutoParallaxBackgroundTexture, this,
						"back.gif", 0, 10);
		this.mAutoParallaxBackgroundTexture.load();

		final ITexture scoreFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 512, 512, TextureOptions.BILINEAR);

		FontFactory.setAssetBasePath("fonts/");
		this.mScoreFont = FontFactory.createFromAsset(this.getFontManager(), scoreFontTexture, this.getAssets(), "8_bit_party.ttf", 32, true, Color.WHITE_ARGB_PACKED_INT);
		this.mScoreFont.load();

		try {
			music = MusicFactory.createMusicFromAsset(this.getMusicManager(),
					this, "music/ballon.mp3");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
*/
	/*protected void end(){
		this.finish();
	}
	*/
	public GameScene() {
		final BaseActivity activity = BaseActivity.getSharedInstance();
		
		entitiesFactory = EntitiesFactory.getInstance(activity);
		music = activity.music;
		scene = this;
		scene.setBackground(activity.backgroundTexture);

		final Text livesText = new Text(0, 0, activity.mScoreFont, "Lives 3", 12, activity.getVertexBufferObjectManager());
		final Text scoreText = new Text(SCENE_WIDTH / 2, 0, activity.mScoreFont, "Score 0", 7, activity.getVertexBufferObjectManager());
		final Text stageText = new Text(SCENE_WIDTH / 2, 0, activity.mScoreFont, "Stage 0", 7, activity.getVertexBufferObjectManager());
		stageText.setPosition(SCENE_WIDTH - stageText.getWidth(), 0);

		
		scene.attachChild(livesText);
		scene.attachChild(scoreText);
		scene.attachChild(stageText);

		
		player = entitiesFactory.getPlayer(new PlayerMovement(SCENE_HEIGHT, SCENE_WIDTH));
		metroidList = new LinkedList<Enemy>();
		
		scene.attachChild(player.getRepresentation());

		scene.setOnSceneTouchListener(new IOnSceneTouchListener() {

			@Override
			public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
				return false;
			}
		});
		
		
		
		scene.registerUpdateHandler(new TimerHandler(1f, true, new ITimerCallback() {
			
			
			
			@Override
			public void onTimePassed(TimerHandler arg0) {

				if (metroidList.size() < ENEMY_BY_STAGE[stage]) {
					Enemy newEnemy = null;
					int enemySelect = 1;
					
					//Horrible!!
					enemySelect = (new Random()).nextInt(3) + 1;
					
					switch (enemySelect) {
						case 1:
							newEnemy = entitiesFactory.getEnemyFish(new LinealMovement(SCENE_HEIGHT, SCENE_WIDTH));
							break;
						case 2:
							newEnemy = entitiesFactory.getEnemyTurtle(new BouncingMovement(SCENE_HEIGHT, SCENE_WIDTH));
							break;
						case 3:
							newEnemy = entitiesFactory.getEnemyMedusa(new ZigZagMovement(SCENE_HEIGHT, SCENE_WIDTH));
							break;
					}
					
					newEnemy.setAccelX(ACCEL_BY_STAGE[stage]);
					newEnemy.setAccelY(ACCEL_BY_STAGE[stage]);

					newEnemy.setPosX(SCENE_WIDTH);
					newEnemy.setPosY((new Random()).nextInt(SCENE_HEIGHT) + BaseActivity.SCORE_HEIGHT - (int)newEnemy.getRepresentation().getHeightScaled());
					
					metroidList.add(newEnemy);
					
					scene.attachChild(newEnemy.getRepresentation());
				}
			}
		})); 


		scene.registerUpdateHandler(new IUpdateHandler() {



			@Override
			public void onUpdate(final float pSecondsElapsed) {
				player.update();
				
				
				Enemy metroid;
				for (int i = 0;  i < metroidList.size(); i++) {
					metroid = metroidList.get(i);
					
					metroid.update();
					if (metroid.collidesWith(player)){
						lives--;
						livesText.setText("Lives " + String.valueOf(lives));
						
						metroid.setAccelX(ACCEL_BY_STAGE[stage]);
						metroid.setAccelY(ACCEL_BY_STAGE[stage]);
						metroid.setPosX(SCENE_WIDTH);
						
						if (lives == 0) {
							
							activity.setCurrentScene(new MainMenuScene());
						}
					}
					
					if (metroid.getPosX() < 0) {
						metroid.setAccelX(ACCEL_BY_STAGE[stage]);
						metroid.setAccelY(ACCEL_BY_STAGE[stage]);
						
						metroid.setPosX(SCENE_WIDTH);
						
						score++;
						passedEnemy++;
						
						if (passedEnemy > ENEMY_BY_STAGE[stage]) {
							passedEnemy = 0;
							stage++;
							stageText.setText("Stage " + String.valueOf(stage));
						}
						
						scoreText.setText("Score " + String.valueOf(score));
					}
					
				}
			}

			@Override
			public void reset() {
			}
		});

		music.setLooping(true);
		music.play();
		activity.setupAccelerationSensor(this);
		activity.setCurrentScene(scene);
	}

	@Override
	public void onAccelerationAccuracyChanged(AccelerationData arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAccelerationChanged(AccelerationData arg0) {
		player.setAccelX((int) arg0.getX());
		player.setAccelY((int) arg0.getY());
	}

	@Override
	public void dispose() {
		music.stop();
		super.dispose();
	}
}
