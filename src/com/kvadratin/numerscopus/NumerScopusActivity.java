package com.kvadratin.numerscopus;

import java.util.Random;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.SmoothCamera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import com.kvadratin.numerscopus.fractal.Fractal;
import com.kvadratin.numerscopus.ui.StatusBar;

public class NumerScopusActivity extends BaseGameActivity {

	public final static int SCREEN_WIDTH = 320;
	public final static int SCREEN_HEIGHT = 480;

	private DisplayMetrics mMetrics;
	private SmoothCamera mCamera;

	private ResourceManager mResources;
	private Fractal mFractal;
	private StatusBar mBar;

	@Override
	public void onLoadComplete() {

	}

	@Override
	public Engine onLoadEngine() {
		mMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(mMetrics);

		mCamera = new SmoothCamera(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, 500, 500,
				1) {
			@Override
			public void onUpdate(float pSecondsElapsed) {
				super.onUpdate(pSecondsElapsed);

				if (mBar != null) {
					mBar.setPosition(
							this.getCenterX() - this.getWidth() * 0.5f, this
									.getCenterY()
									- this.getHeight() * 0.5f);
				}
			};
		};

		EngineOptions options = new EngineOptions(true,
				ScreenOrientation.PORTRAIT, new RatioResolutionPolicy(
						SCREEN_WIDTH, SCREEN_HEIGHT), mCamera);
		options.getTouchOptions().enableRunOnUpdateThread();

		return new Engine(options);
	}

	@Override
	public void onLoadResources() {

		ResourceManager.init(this, mEngine);
		mResources = ResourceManager.getInstance();

		mBar = new StatusBar(320, 60, mResources
				.getSprite(ResourceManager.GFX_STATUS_TIME), mResources
				.getSprite(ResourceManager.GFX_STATUS_STAR), Color.argb(255,
				248, 255, 134), Color.argb(255, 36, 28, 28), Color.argb(255,
				212, 0, 0), mResources.getFontManager(
				ResourceManager.FONT_MANAGER_MENU).get(
				"menu_LinLibertine_DR.otf"), mResources.getFontManager(
				ResourceManager.FONT_MANAGER_MENU).get(
				"menu_lerotica-regular.otf"));

	}

	@Override
	public Scene onLoadScene() {
		mEngine.registerUpdateHandler(new FPSLogger());

		mFractal = new Fractal(mMetrics, mEngine, mResources
				.getFractalTheme(ResourceManager.THEME_BASE), 50);

		mBar.attachTo(mFractal.getScene());
		mFractal.setPaddingTop(mBar.getHeight());

		mEngine.getCamera().setCenter(mFractal.getWidth() * 0.5f,
				mFractal.getHeight() * 0.5f);

		mBar.showAttention(2);
		return mFractal.getScene();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(com.kvadratin.R.menu.gamemenu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		mEngine.runOnUpdateThread(new Runnable() {

			@Override
			public void run() {

				mBar.detachSelf();
				mBar.setScore("77775");
				mBar.setText("Last number: 5");
				mBar.setTime("0:10");
				mBar.setColor(Color.MAGENTA);

				Random rnd = new Random();
				mFractal.split(mResources.getFractalTheme(rnd
						.nextInt(ResourceManager.THEMES_COUNT)), rnd
						.nextInt(45) + 5);

				mBar.attachTo(mFractal.getScene());
				mBar.hide();
				mEngine.getCamera().setCenter(mFractal.getWidth() * 0.5f,
						mFractal.getHeight() * 0.5f);
				
			}
		});

		return true;
	}
}