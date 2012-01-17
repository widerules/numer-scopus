package com.kvadratin.numerscopus.font;

import java.io.IOException;

import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.font.FontManager;
import org.anddev.andengine.opengl.texture.TextureManager;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

import android.content.Context;
import android.util.Log;

public class AssetFontManager implements IFontManager {

	private Font[] mFontLibrary;
	private String mAssetBasePath;
	private String mFileNamePrefix;

	private int mFontColor;
	private int mFontStrokeColor;
	private int mFontSize;
	private float mStrokeWidth;
	private boolean mIsStroke;

	private boolean mIsLoaded;
	private Context mContext;
	private FontManager mFontManager;
	private TextureManager mTextureManager;

	public AssetFontManager(final Context pContext,
			final FontManager pFontManager,
			final TextureManager pTextureManager, final String pAssetBasePath,
			final String pFileNamePrefix, final int pFontColor,
			final int pFontStrokeColor, final int pFontSize,
			final float pStrokeWidth, final boolean pIsStroke) {

		mContext = pContext;
		mFontManager = pFontManager;
		mTextureManager = pTextureManager;

		mAssetBasePath = pAssetBasePath;
		mFileNamePrefix = pFileNamePrefix;

		mFontColor = pFontColor;
		mFontStrokeColor = pFontStrokeColor;
		mFontSize = pFontSize;
		mStrokeWidth = pStrokeWidth;
		mIsStroke = pIsStroke;

		this.load();
	}

	@Override
	public Font get(int pFontId) {
		return mFontLibrary[pFontId];
	}

	@Override
	public int getFontColor() {
		return mFontColor;
	}

	@Override
	public int getFontSize() {
		return mFontSize;
	}

	@Override
	public void load() {

		if (!mIsLoaded) {
			try {
				String list[] = mContext.getAssets().list(mAssetBasePath);
				int count = 0;

				for (String name : list) {
					if (name.startsWith(mFileNamePrefix))
						count++;
				}

				if (count > 0) {
					
					mFontLibrary = new Font[count];
					count = 0;

					for (int i = 0; i < list.length; i++) {

						if (list[i].startsWith(mFileNamePrefix)) {

							try {
								int textureSize = mFontSize < 125 ? 512
										: mFontSize < 250 ? 1024
												: mFontSize < 500 ? 2048 : 4096;

								BitmapTextureAtlas fontTexture = new BitmapTextureAtlas(
										textureSize,
										textureSize,
										TextureOptions.BILINEAR_PREMULTIPLYALPHA);

								Font font = null;

								if (mIsStroke) {
									font = FontFactory.createStrokeFromAsset(
											fontTexture, mContext, list[i],
											mFontSize, true, mFontColor,
											mStrokeWidth, mFontStrokeColor);

								} else {
									font = FontFactory.createFromAsset(
											fontTexture, mContext, list[i],
											mFontSize, true, mFontColor);
								}

								mTextureManager.loadTexture(fontTexture);
								mFontLibrary[count++] = font;

							} catch (Exception ex) {
								Log.e("NumerScopus", "Error on load font", ex);
							}
						}
					}
				}
			} catch (IOException ex) {
				Log.e("NumerScopus", "Error on get list of fonts", ex);
			}

			for (int i = 0; i < mFontLibrary.length; i++)
				mFontManager.loadFont(mFontLibrary[i]);
		}

		mIsLoaded = true;
	}

	@Override
	public int size() {
		return mFontLibrary.length;
	}

	@Override
	public void clear() {

	}

	@Override
	public int getFontStrokeColor() {
		return mFontStrokeColor;
	}

	@Override
	public float getStrokeWidth() {
		return mStrokeWidth;
	}

}
