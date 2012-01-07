package com.kvadratin.numerscopus;

import java.io.IOException;
import java.util.Random;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.font.FontLibrary;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.Log;

import com.kvadratin.numerscopus.fractal.FractalPart;
import com.kvadratin.numerscopus.fractal.splitter.FractalSplitterManager;
import com.kvadratin.numerscopus.ornament.OrnamentManager;
import com.kvadratin.numerscopus.utils.BitmapTextureSource;

public class NumerScopusActivity extends BaseGameActivity {

	private DisplayMetrics mMetrics;
	private Camera mCamera;
	private Scene mScene;

	private BitmapTextureAtlas mMapTexture;
	private TextureRegion mMapTextureRegion;

	private FractalSplitterManager mSplitterManager;
	private FractalPart mFractal;
	private OrnamentManager mOrnamentManager;

	private FontLibrary mFontLibrary;
	private int mFontsCount;

	private Sprite mOrnament;
	private Sprite mSpr;
	private ChangeableText mText;
	private Bitmap mImage;
	private Rectangle mRect;

	private void loadFonts() {
		AssetManager manager = this.getAssets();

		try {
			String list[] = manager.list("fonts");
			mFontLibrary = new FontLibrary(list.length);
			mFontsCount = list.length;

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

		mEngine.getFontManager().loadFonts(mFontLibrary);
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
		mOrnamentManager = new OrnamentManager(this, "gfx", mEngine.getTextureManager(), 256);
	}

	@Override
	public Scene onLoadScene() {
		mEngine.registerUpdateHandler(new FPSLogger());

		mScene = new Scene() {

			@Override
			public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
				super.onSceneTouchEvent(pSceneTouchEvent);

				try {

					if (pSceneTouchEvent.isActionDown()) {

						if (mSpr != null)
							mScene.detachChild(mSpr);
						if (mText != null)
							mScene.detachChild(mText);
						if (mMapTexture != null)
							mEngine.getTextureManager().unloadTexture(
									mMapTexture);
						if (mImage != null)
							mImage.recycle();
						if (mRect != null)
							mScene.detachChild(mRect);
						if (mOrnament != null)
							mScene.detachChild(mOrnament);

						int count = ((new Random()).nextInt(199)) + 1;
						mFractal = mSplitterManager.getFractalPart(count);						
						FractalPart.split(mFractal, count, mSplitterManager);

						mImage = Bitmap.createBitmap((int) mFractal.getWidth(),
								(int) mFractal.getHeight(),
								Bitmap.Config.ARGB_8888);
						Canvas c = new Canvas(mImage);
						c.drawARGB(255, 255, 255, 255);
						mFractal.draw(c);

						int textureSize = mImage.getWidth() <= 128
								&& mImage.getHeight() <= 128 ? 128 : mImage
								.getWidth() <= 256
								&& mImage.getHeight() <= 256 ? 256 : mImage
								.getWidth() <= 512
								&& mImage.getHeight() <= 512 ? 512 : mImage
								.getWidth() <= 1024
								&& mImage.getHeight() <= 1024 ? 1024 : mImage
								.getWidth() <= 2048
								&& mImage.getHeight() <= 2048 ? 2048 : 4096;

						mMapTexture = new BitmapTextureAtlas(textureSize,
								textureSize,
								TextureOptions.BILINEAR_PREMULTIPLYALPHA);
						mMapTextureRegion = BitmapTextureAtlasTextureRegionFactory
								.createFromSource(mMapTexture,
										new BitmapTextureSource(mImage), 0, 0);
						mEngine.getTextureManager().loadTexture(mMapTexture);

						mSpr = new Sprite(0, 0, mMapTextureRegion);
						mScene.attachChild(mSpr);

						final Random rand = new Random();
						mText = new ChangeableText(0, 0, mFontLibrary.get(rand
								.nextInt(mFontsCount)), Integer.toString(count));
						// mText.setText(Integer.toString(count));
						mText.setColor(68 / 255, 24 / 255, 24 / 255);
						mText.setScaleCenter(0, 0);
						//mText.setScale(rand.nextFloat() * 0.5f + 0.4f, rand.nextFloat() * 0.5f + 0.4f);
						// mText.setRotation(mText.getRotation() + 90);

						Log.d("NumerScopus", "AAAAA 0 "
								+ Integer.toString(count));
						Log.d("NumerScopus", "AAAAA 1 "
								+ Float.toString(mText.getScaleX()) + " "
								+ Float.toString(mText.getScaleY()));
						Log.d("NumerScopus", "AAAAA 2 "
								+ Float.toString(mText.getWidthScaled())
								+ " x "
								+ Float.toString(mText.getHeightScaled()));
						Log.d("NumerScopus", "AAAAA 3 "
								+ Float.toString(mText.getWidth()) + " x "
								+ Float.toString(mText.getHeight()));

						mText.setPosition((mMetrics.widthPixels * 0.5f)
								- (mText.getWidthScaled() * 0.5f),
								(mMetrics.heightPixels * 0.5f)
										- (mText.getHeightScaled() * 0.5f));

						mRect.setWidth(mText.getWidthScaled());
						mRect.setHeight(mText.getHeightScaled());
						mRect.setRotationCenter(mText.getRotationCenterX(),
								mText.getRotationCenterY());
						mRect.setRotation(mText.getRotation());
						mRect.setPosition(mText.getX(), mText.getY());

						int ornId = rand.nextInt(mOrnamentManager
								.getOrnamentCount());

						mOrnament = mOrnamentManager.getSprite(ornId,
								new RectF(mRect.getX(), mRect.getY(), mRect
										.getX()
										+ mRect.getWidthScaled(), mRect.getY()
										+ mRect.getHeightScaled()));
						mOrnament.setRotationCenter(mRect.getRotationCenterX(),
								mRect.getRotationCenterY());
						mOrnament.setRotation(mRect.getRotation());

						mScene.attachChild(mRect);
						mScene.attachChild(mOrnament);
						mScene.attachChild(mText);

					}
				} catch (Exception ex) {
					Log.e("NumerScopus", "Error on touch: " + ex.getMessage(),
							ex);
				}

				return true;
			}
		};

		mScene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));

		mText = new ChangeableText(0, 0, mFontLibrary.get((new Random()).nextInt(mFontsCount)), "01234567890");
		mText.setScaleCenter(0, 0);
		mText.setScale(0.5f);

		mText.setPosition((mMetrics.widthPixels / 2)
				- (mText.getWidthScaled() / 2), (mMetrics.heightPixels / 2)
				- (mText.getHeightScaled() / 2));

		mRect = new Rectangle(mText.getX(), mText.getY(), mText
				.getWidthScaled(), mText.getHeightScaled());
		mRect.setColor(1f, 1f, 1f);

		mOrnament = mOrnamentManager.getSprite(0, new RectF(mRect.getX(), mRect
				.getY(), mRect.getX() + mRect.getWidthScaled(), mRect.getY()
				+ mRect.getHeightScaled()));

		mScene.attachChild(mRect);
		mScene.attachChild(mOrnament);
		mScene.attachChild(mText);

		return mScene;
	}

}