package com.kvadratin.numerscopus;

import java.util.Random;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;

import com.kvadratin.numerscopus.fractal.FractalPart;
import com.kvadratin.numerscopus.fractal.splitter.FractalSplitterManager;
import com.kvadratin.numerscopus.utils.BitmapTextureSource;

public class NumerScopusActivity extends BaseGameActivity {

	private DisplayMetrics mMetrics;
	private Camera mCamera;
	private Scene mScene;

	private BitmapTextureAtlas mMapTexture;
	private TextureRegion mMapTextureRegion;

	private BitmapTextureAtlas mIntFontTexture;
	private Font mIntuitiveFont;

	private FractalSplitterManager mSplitterManager;
	private FractalPart mFractal;

	private Sprite mSpr;
	private Text mText;
	private Bitmap mImage;

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
		FontFactory.setAssetBasePath("fonts/");

		mIntFontTexture = new BitmapTextureAtlas(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mIntuitiveFont = FontFactory.createFromAsset(mIntFontTexture, this,
				"intuitive.ttf", 32, true, Color.argb(255, 0, 0, 57));

		mEngine.getTextureManager().loadTexture(mIntFontTexture);
		mEngine.getFontManager().loadFont(mIntuitiveFont);

		mSplitterManager = new FractalSplitterManager(mMetrics);
	}

	@Override
	public Scene onLoadScene() {
		mEngine.registerUpdateHandler(new FPSLogger());

		mScene = new Scene() {
			
			@Override
			public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
				super.onSceneTouchEvent(pSceneTouchEvent);
				
				try{
					
				if (pSceneTouchEvent.isActionDown()) {
					
					if(mSpr != null)
						mScene.detachChild(mSpr);
					if(mText != null)
						mScene.detachChild(mText);
					if(mMapTexture != null)
						mEngine.getTextureManager().unloadTexture(mMapTexture);
					if (mImage !=  null)
						mImage.recycle();
					
					int count = ((new Random()).nextInt(199)) + 1;
					mFractal = mSplitterManager.getFractalPart(count);
					mFractal.split(mSplitterManager, count);
					
					mImage = Bitmap.createBitmap((int) mFractal
							.getWidth(), (int) mFractal.getHeight(),
							Bitmap.Config.ARGB_8888);
					Canvas c = new Canvas(mImage);
					c.drawARGB(255, 255, 255, 255);
					mFractal.draw(c);
					
					int textureSize = mImage.getWidth() <= 128 && mImage.getHeight() <= 128 ? 128
										: mImage.getWidth() <= 256 && mImage.getHeight() <= 256 ? 256
												: mImage.getWidth() <= 512 && mImage.getHeight() <= 512 ? 512
														: mImage.getWidth() <= 1024 && mImage.getHeight() <= 1024 ? 1024
																: mImage.getWidth() <= 2048 && mImage.getHeight() <= 2048 ? 2048 
																		: 4096;

					mMapTexture = new BitmapTextureAtlas(textureSize, textureSize,
							TextureOptions.BILINEAR_PREMULTIPLYALPHA);
					mMapTextureRegion = BitmapTextureAtlasTextureRegionFactory
							.createFromSource(mMapTexture,
									new BitmapTextureSource(mImage), 0, 0);
					mEngine.getTextureManager().loadTexture(mMapTexture);
						
					mSpr = new Sprite(0, 0, mMapTextureRegion);
					mScene.attachChild(mSpr);

					mText = new Text(0, 0, mIntuitiveFont, Integer
							.toString(count));
					mText.setPosition((mMetrics.widthPixels / 2)
							- (mText.getWidth() / 2),
							(mMetrics.heightPixels / 2)
									- (mText.getHeight() / 2));
					mScene.attachChild(mText);
					
				}
				} catch (Exception ex){
					Log.e("NumerScopus", "Error on touch: " + ex.getMessage(), ex);
				}
				
				return true;
			}
		};

		mScene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));
		
		mText = new Text(0, 0, mIntuitiveFont, "Touch");
		mText.setPosition((mMetrics.widthPixels / 2) - (mText.getWidth() / 2),
				(mMetrics.heightPixels / 2) - (mText.getHeight() / 2));
		mScene.attachChild(mText);

		return mScene;
	}

}