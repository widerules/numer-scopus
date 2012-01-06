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
public class OrnamentManager {

	// --------------------------------------------------------------------
	// Константы
	// --------------------------------------------------------------------
	/**
	 * Префикс SVG файлов, содержащих орнаменты
	 */
	public static final String ORNAMENT_NAME_PREFIX = "orn_";

	// --------------------------------------------------------------------
	// Поля
	// --------------------------------------------------------------------
	private int mTextureSize;
	private BitmapTextureAtlas[] mTextures;
	private TextureRegion[] mRegions;

	// --------------------------------------------------------------------
	// Конструкторы
	// --------------------------------------------------------------------
	/**
	 * Производит загрузку орнаментов из pAssetBasePath
	 * 
	 * @param pAssetBasePath
	 *            Название поддиректории assets/, в которой содержатся файлы
	 *            орнаментов
	 * @param pTextureSize
	 *            Размер текстуры орнамента
	 */
	public OrnamentManager(final Context pContext, final String pAssetBasePath,
			final TextureManager pTextureManager, final int pTextureSize) {

		mTextureSize = pTextureSize;
		AssetManager manager = pContext.getAssets();

		try {
			String list[] = manager.list(pAssetBasePath);
			int count = 0;

			for (String name : list) {
				if (name.startsWith(ORNAMENT_NAME_PREFIX))
					count++;
			}

			if (count > 0) {
				mTextures = new BitmapTextureAtlas[count];
				mRegions = new TextureRegion[count];

				Arrays.sort(list);
				count = 0;

				for (int i = 0; i < list.length; i++) {
					if (list[i].startsWith(ORNAMENT_NAME_PREFIX)) {
						try {
							mTextures[count] = new BitmapTextureAtlas(
									pTextureSize, pTextureSize,
									TextureOptions.BILINEAR_PREMULTIPLYALPHA);

							mRegions[count] = SVGBitmapTextureAtlasTextureRegionFactory
									.createFromAsset(mTextures[count],
											pContext, list[i], pTextureSize,
											pTextureSize, 0, 0);

							pTextureManager.loadTexture(mTextures[count]);
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
	public int getOrnamentCount() {
		return mTextures.length;
	}

	/**
	 * Возвращает текстуру орнамента
	 * 
	 * @param pOrnamentId
	 *            Идентификатор орнамента
	 */
	public BitmapTextureAtlas getTexture(final int pOrnamentId) {
		return mTextures[pOrnamentId];
	}

	/**
	 * Возвращает регион текстуры орнамента
	 * 
	 * @param pOrnamentId
	 *            Идентификатор орнамента
	 */
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
	 */
	public Sprite getSprite(final int pOrnamentId, RectF pField) {

		int width = (int) pField.width();
		int height = (int) pField.height();

		Sprite result = new Sprite(pField.left, pField.top, new TextureRegion(
				mTextures[pOrnamentId], 0, 0, width < mTextureSize ? width
						: mTextureSize, height < mTextureSize ? height
						: mTextureSize));

		result.setScaleCenter(0, 0);
		result.setScaleX(pField.width() / result.getWidth());
		result.setScaleY(pField.height() / result.getHeight());

		return result;
	}
}
