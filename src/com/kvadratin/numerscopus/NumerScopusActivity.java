package com.kvadratin.numerscopus;

import java.io.IOException;
import java.util.Random;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;

import com.kvadratin.numerscopus.fractal.Fractal;
import com.kvadratin.numerscopus.fractal.splitter.FractalSplitterManager;
import com.kvadratin.numerscopus.ornament.OrnamentManager;

public class NumerScopusActivity extends BaseGameActivity {

	private DisplayMetrics mMetrics;
	private Camera mCamera;
	private Scene mScene;

	private FractalSplitterManager mSplitterManager;
	private OrnamentManager mOrnamentManager;
	private SparseArray<Font> mFontLibrary;

	private Fractal mFractal;

	private void loadFonts() {
		AssetManager manager = this.getAssets();

		try {
			String list[] = manager.list("fonts");
			mFontLibrary = new SparseArray<Font>(list.length);

			for (int i = 0; i < list.length; i++) {
				try {
					// TODO: Texture size, must be relative to font size
					BitmapTextureAtlas fontTexture = new BitmapTextureAtlas(
							512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
					// TODO: Font size, must be relative to display metrics
					Font font = FontFactory.createFromAsset(fontTexture, this,
							list[i], 120, true, Color.argb(255, 68, 24, 24));

					mEngine.getTextureManager().loadTexture(fontTexture);
					mFontLibrary.put(i, font);
				} catch (Exception ex) {
					Log.e("NumerScopus", "Error on load font", ex);
				}
			}
		} catch (IOException ex) {
			Log.e("NumerScopus", "Error on get list of fonts", ex);
		}

		for (int i = 0; i < mFontLibrary.size(); i++)
			mEngine.getFontManager().loadFonts(mFontLibrary.get(i));
	}

	@Override
	public void onLoadComplete() {

	}

	@Override
	public Engine onLoadEngine() {
		mMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(mMetrics);

		mCamera = new Camera(0, 0, mMetrics.widthPixels, mMetrics.heightPixels);

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
		this.loadFonts();

		mSplitterManager = new FractalSplitterManager(mMetrics);
		// TODO: Texture size, must be relative to display metrics
		mOrnamentManager = new OrnamentManager(this, "gfx", "orn_", mEngine
				.getTextureManager(), 256);
	}

	@Override
	public Scene onLoadScene() {
		mEngine.registerUpdateHandler(new FPSLogger());

		mScene = new Scene() {

			@Override
			public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
				super.onSceneTouchEvent(pSceneTouchEvent);

				//try {
					if (pSceneTouchEvent.isActionDown()) {
						Log.d("NumerScopus", "Start split");
						mFractal.split((new Random()).nextInt(20));
						Log.d("NumerScopus", "End split");
					}
				//} catch (Exception ex) {
				//	Log.e("NumerScopus", "Error on touch: " + ex.getMessage(),
				//			ex);
				//}

				return true;
			}
		};

		mScene.setBackground(new ColorBackground(1f, 1f, 1f));
		
		mFractal = new Fractal(mEngine.getTextureManager(), mScene,
				mSplitterManager, mOrnamentManager, mFontLibrary, 1);

		return mScene;
	}

}