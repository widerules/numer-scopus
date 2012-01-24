package com.kvadratin.numerscopus;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.SmoothCamera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.graphics.Color;
import android.util.DisplayMetrics;

import com.kvadratin.numerscopus.font.FontManagerFactory;
import com.kvadratin.numerscopus.font.IFontManager;
import com.kvadratin.numerscopus.fractal.Fractal;
import com.kvadratin.numerscopus.fractal.theme.FractalThemeFactory;
import com.kvadratin.numerscopus.fractal.theme.IFractalTheme;
import com.kvadratin.numerscopus.ornament.IOrnamentManager;
import com.kvadratin.numerscopus.ornament.OrnamentManagerFactory;

public class NumerScopusActivity extends BaseGameActivity {

	private DisplayMetrics mMetrics;
	private SmoothCamera mCamera;

	private IOrnamentManager mOrnamentManager;
	private IFontManager mFontManager;
	private Fractal mFractal;

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

		// TODO: Texture size, must be relative to display metrics
		mOrnamentManager = OrnamentManagerFactory.createAssetOrnamentManager(
				this, "gfx", "orn_", mEngine.getTextureManager(), 256);
	}

	@Override
	public Scene onLoadScene() {
		mEngine.registerUpdateHandler(new FPSLogger());

		IFractalTheme theme = // FractalThemeFactory.createClearGreyFractalTheme(mFontManager);
		FractalThemeFactory.createBaseFractalTheme(mFontManager,
				mOrnamentManager);

		mFractal = new Fractal(mMetrics, mEngine, theme, 100);

		mEngine.getCamera().setCenter(mFractal.getWidth() * 0.5f,
				mFractal.getHeight() * 0.5f);

		return mFractal.getScene();
	}

}