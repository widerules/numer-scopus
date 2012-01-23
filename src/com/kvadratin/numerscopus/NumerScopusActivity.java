package com.kvadratin.numerscopus;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.SmoothCamera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.graphics.Color;
import android.util.DisplayMetrics;

import com.kvadratin.numerscopus.font.FontManagerFactory;
import com.kvadratin.numerscopus.font.IFontManager;
import com.kvadratin.numerscopus.fractal.Fractal;
import com.kvadratin.numerscopus.fractal.splitter.FractalSplitterManager;
import com.kvadratin.numerscopus.fractal.theme.FractalThemeFactory;
import com.kvadratin.numerscopus.fractal.theme.IFractalTheme;
import com.kvadratin.numerscopus.ornament.IOrnamentManager;
import com.kvadratin.numerscopus.ornament.OrnamentManagerFactory;

public class NumerScopusActivity extends BaseGameActivity {

	private static int FRACTAL_PADDING = 10;

	private DisplayMetrics mMetrics;
	private SmoothCamera mCamera;
	private Scene mScene;

	private FractalSplitterManager mSplitterManager;
	private IOrnamentManager mOrnamentManager;
	private IFontManager mFontManager;

	private Fractal mFractal;

	private float mBeginTouchPositionX;
	private float mBeginTouchPositionY;

	@Override
	public void onLoadComplete() {

	}

	@Override
	public Engine onLoadEngine() {
		mMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(mMetrics);

		mCamera = new SmoothCamera(0, 0, mMetrics.widthPixels,
				mMetrics.heightPixels, 500, 500, 1);

		EngineOptions options = new EngineOptions(true,
				ScreenOrientation.PORTRAIT, new RatioResolutionPolicy(
						mMetrics.widthPixels, mMetrics.heightPixels), mCamera);
		options.getTouchOptions().enableRunOnUpdateThread();

		return new Engine(options);
	}

	@Override
	public void onLoadResources() {
		SVGBitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		FontFactory.setAssetBasePath("fonts/");
		// TODO: Font size, must be relative to display metrics
		mFontManager = FontManagerFactory.createAssetFontManager(this, mEngine
				.getFontManager(), mEngine.getTextureManager(), "fonts",
				"base_", Color.argb(255, 255, 255, 255), Color.argb(255, 255,
						255, 255), 120, 1, false);

		mSplitterManager = new FractalSplitterManager(mMetrics);
		// TODO: Texture size, must be relative to display metrics
		mOrnamentManager = OrnamentManagerFactory.createAssetOrnamentManager(
				this, "gfx", "orn_", mEngine.getTextureManager(), 256);
	}

	@Override
	public Scene onLoadScene() {
		mEngine.registerUpdateHandler(new FPSLogger());

		mScene = new Scene() {

			@Override
			public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
				super.onSceneTouchEvent(pSceneTouchEvent);

				if (pSceneTouchEvent.isActionDown()) {
					mBeginTouchPositionX = pSceneTouchEvent.getX();
					mBeginTouchPositionY = pSceneTouchEvent.getY();
				}

				if (pSceneTouchEvent.isActionMove()) {

					float x = mCamera.getCenterX();
					float y = mCamera.getCenterY();
					float dx = mBeginTouchPositionX - pSceneTouchEvent.getX();
					float dy = mBeginTouchPositionY - pSceneTouchEvent.getY();

					float left = mFractal.getX() + mCamera.getWidth() * 0.5f
							- FRACTAL_PADDING;
					float right = mFractal.getWidth() - mCamera.getWidth()
							* 0.5f + FRACTAL_PADDING;
					
					float top = mFractal.getY() + mCamera.getHeight() * 0.5f - FRACTAL_PADDING;
					float bottom = mFractal.getHeight()	- mCamera.getHeight() * 0.5f + FRACTAL_PADDING;
					
					if (x + dx < left)
						x = left;
					else if (x + dx > right)
						x = right;
					else
						x += dx;

					if (y + dy < top)
						y = top;
					else if (y + dy > bottom)
						y = bottom;
					else
						y += dy;

					mCamera.setCenter(x, y);
				}
				/*
				 * try { if (pSceneTouchEvent.isActionDown()) {
				 * mFractal.split((new Random()).nextInt(20)); } } catch
				 * (Exception ex) { Log.e("NumerScopus", "Error on touch: " +
				 * ex.getMessage(), ex); }
				 */

				return true;
			}
		};

		IFractalTheme theme = // FractalThemeFactory.createClearGreyFractalTheme(mFontManager);
		FractalThemeFactory.createBaseFractalTheme(mFontManager,
				mOrnamentManager);

		mScene.setBackground(theme.getBackground());

		mFractal = new Fractal(mEngine.getTextureManager(), mScene,
				mSplitterManager, theme, 100);

		mCamera.setCenter(mFractal.getWidth() * 0.5f,
				mFractal.getHeight() * 0.5f);

		return mScene;
	}

}