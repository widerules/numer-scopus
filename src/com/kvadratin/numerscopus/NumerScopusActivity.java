package com.kvadratin.numerscopus;

import java.util.Random;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.SmoothCamera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import com.kvadratin.numerscopus.fractal.Fractal;
import com.kvadratin.numerscopus.fractal.event.FractalTouchEvent;
import com.kvadratin.numerscopus.fractal.event.IFractalClickListener;
import com.kvadratin.numerscopus.fractal.theme.FractalThemeFactory;
import com.kvadratin.numerscopus.fractal.theme.IFractalTheme;
import com.kvadratin.numerscopus.fractal.theme.font.FontManagerFactory;
import com.kvadratin.numerscopus.fractal.theme.font.IFontManager;
import com.kvadratin.numerscopus.fractal.theme.ornament.IOrnamentManager;
import com.kvadratin.numerscopus.fractal.theme.ornament.OrnamentManagerFactory;

public class NumerScopusActivity extends BaseGameActivity {

	private DisplayMetrics mMetrics;
	private SmoothCamera mCamera;

	private IOrnamentManager mOrnamentManager;
	private IFontManager mFontManager;
	private Fractal mFractal;
	private IFractalTheme[] mThemes;

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

		mThemes = new IFractalTheme[3];
		mThemes[0] = FractalThemeFactory
				.createClearGreyFractalTheme(mFontManager);
		mThemes[1] = FractalThemeFactory.createBaseFractalTheme(mFontManager,
				mOrnamentManager);
		mThemes[2] = FractalThemeFactory.createColorRectFractalTheme(
				mFontManager, OrnamentManagerFactory
						.createColorRectOrnamentManager());

		mFractal = new Fractal(mMetrics, mEngine, mThemes[2], 100);
		mFractal.addClickListener(new IFractalClickListener() {

			@Override
			public void onClick(FractalTouchEvent e) {

				if (e.getTouchedPart() != null) {
					IEntity en = e.getTouchedPart().getOrnamentEntity();

					if (en != null) {
						en.setIgnoreUpdate(false);
						en.registerEntityModifier(new RotationModifier(2, 0,
								360));
					}

					Text txt = e.getTouchedPart().getNumberText();
					txt.setIgnoreUpdate(false);
					txt.setColor(e.getSource().getTheme()
							.getDisabledTextColorRed(), e.getSource()
							.getTheme().getDisabledTextColorGreen(), e
							.getSource().getTheme().getDisabledTextColorBlue());
				}
			}
		});

		mEngine.getCamera().setCenter(mFractal.getWidth() * 0.5f,
				mFractal.getHeight() * 0.5f);

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
				Random rnd = new Random();
				mFractal.split(mThemes[rnd.nextInt(mThemes.length)], rnd.nextInt(145) + 5);
				mEngine.getCamera().setCenter(mFractal.getWidth() * 0.5f,
						mFractal.getHeight() * 0.5f);
			}
		});

		return true;
	}
}