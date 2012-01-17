package com.kvadratin.numerscopus.ornament;

import java.io.IOException;
import java.util.Arrays;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.TextureManager;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.RectF;
import android.util.Log;

/**
 * Менеджер орнаментов
 * 
 * @author bargatin
 * @since 2012-01-06
 */
public class OrnamentManager implements IOrnamentManager {

	// --------------------------------------------------------------------
	// Константы
	// --------------------------------------------------------------------
	/**
	 * Количество методов заполнения спрайта.
	 */
	public static final byte FILL_METHODS_COUNT = 9;

	// --------------------------------------------------------------------
	// Поля
	// --------------------------------------------------------------------
	private int mTextureSize;
	private BitmapTextureAtlas[] mTextures;
	private TextureRegion[] mRegions;
	private TextureManager mTextureManager;

	// --------------------------------------------------------------------
	// Конструкторы
	// --------------------------------------------------------------------
	/**
	 * Производит загрузку орнаментов из pAssetBasePath
	 * 
	 * @param pAssetBasePath
	 *            Название поддиректории assets/, в которой содержатся файлы
	 *            орнаментов
	 * @param pFileNamePrefix
	 *            Префикс SVG файлов, содержащих орнаменты
	 * @param pTextureSize
	 *            Размер текстуры орнамента
	 */
	public OrnamentManager(final Context pContext, final String pAssetBasePath,
			final String pFileNamePrefix, final TextureManager pTextureManager,
			final int pTextureSize) {

		mTextureSize = pTextureSize;
		mTextureManager = pTextureManager;
		AssetManager manager = pContext.getAssets();

		try {
			String list[] = manager.list(pAssetBasePath);
			int count = 0;

			for (String name : list) {
				if (name.startsWith(pFileNamePrefix))
					count++;
			}

			if (count > 0) {
				mTextures = new BitmapTextureAtlas[count];
				mRegions = new TextureRegion[count];

				Arrays.sort(list);
				count = 0;

				for (int i = 0; i < list.length; i++) {
					if (list[i].startsWith(pFileNamePrefix)) {
						try {
							mTextures[count] = new BitmapTextureAtlas(
									pTextureSize, pTextureSize,
									TextureOptions.BILINEAR_PREMULTIPLYALPHA);

							mRegions[count] = SVGBitmapTextureAtlasTextureRegionFactory
									.createFromAsset(mTextures[count],
											pContext, list[i], pTextureSize,
											pTextureSize, 0, 0);

							mTextureManager.loadTexture(mTextures[count]);
							count++;

						} catch (Exception ex) {
							Log.e("NumerScopus",
									"Error on create ornament texture", ex);
						}
					}
				}
			}
		} catch (IOException ex) {
			Log.e("NumerScopus", "Error on get list of ornament files", ex);
		}
	}

	// --------------------------------------------------------------------
	// Методы
	// --------------------------------------------------------------------
	/**
	 * Количество орнаментов загруженных в менеджер
	 */
	@Override
	public int getOrnamentCount() {
		return mTextures.length;
	}

	/**
	 * Возвращает текстуру орнамента
	 * 
	 * @param pOrnamentId
	 *            Идентификатор орнамента
	 */
	@Override
	public BitmapTextureAtlas getTexture(final int pOrnamentId) {
		return mTextures[pOrnamentId];
	}

	/**
	 * Возвращает регион текстуры орнамента
	 * 
	 * @param pOrnamentId
	 *            Идентификатор орнамента
	 */
	@Override
	public TextureRegion getTextureRegion(final int pOrnamentId) {
		return mRegions[pOrnamentId];
	}

	/**
	 * Возвращает спрайт орнамента, подогнанный по размеру под pField
	 * 
	 * @param pOrnamentId
	 *            Идентификатор орнамента
	 * @param pField
	 *            Поле под размеры которого будет подгонятся спрайт орнамента
	 * @param pFillMethod
	 *            Метод заполнения спрайта текстурой. pFillMethod <
	 *            FILL_METHODS_COUNT
	 */
	@Override
	public Sprite getSprite(final int pOrnamentId, RectF pField,
			byte pFillMethod) {

		pFillMethod = pFillMethod < 0 || pFillMethod >= FILL_METHODS_COUNT ? 0
				: pFillMethod;

		int x = 0;
		int y = 0;

		int width = (int) pField.width();
		width = width < mTextureSize ? width : mTextureSize;

		int height = (int) pField.height();
		height = height < mTextureSize ? height : mTextureSize;

		int dX = mTextureSize - width;
		int dY = mTextureSize - height;

		float xScaleFactor = pField.width() / width;
		float yScaleFactor = pField.height() / height;

		switch (pFillMethod) {
		case 0:
			x = 0;
			y = 0;
			break;

		case 1:
			x = dX;
			y = 0;
			break;

		case 2:
			x = dX;
			y = dY;
			break;

		case 3:
			x = 0;
			y = dY;
			break;

		case 4:
			x = (int) (dX * 0.5f);
			y = (int) (dY * 0.5f);
			break;

		case 5:
			x = (int) (dX * 0.5f);
			y = 0;
			break;

		case 6:
			x = dX;
			y = (int) (dY * 0.5f);
			break;

		case 7:
			x = (int) (dX * 0.5f);
			y = dY;
			break;

		case 8:
			x = 0;
			y = (int) (dY * 0.5f);
			break;
		}

		// Создаем и масштабируем спрайт
		Sprite result = null;

		if (mTextures[pOrnamentId] != null) {
			result = new Sprite(pField.left, pField.top, new TextureRegion(
					mTextures[pOrnamentId], x, y, width, height));

			result.setScaleCenter(0, 0);
			result.setScaleX(xScaleFactor);
			result.setScaleY(yScaleFactor);
		}

		return result;
	}

	@Override
	public void clear() {
		for (int i = 0; i < mTextures.length; i++) {
			if (mTextures[i] != null)
				mTextureManager.unloadTexture(mTextures[i]);

			mTextures[i] = null;
			mRegions[i] = null;
		}
	}
}
