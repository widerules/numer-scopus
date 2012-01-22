package com.kvadratin.numerscopus.ornament;

import org.anddev.andengine.opengl.texture.TextureManager;

import android.content.Context;

public class OrnamentManagerFactory {

	/**
	 * Создает AssetOrnamentManager и производит загрузку орнаментов из
	 * pAssetBasePath
	 * 
	 * @param pAssetBasePath
	 *            Название поддиректории assets/, в которой содержатся файлы
	 *            орнаментов
	 * @param pFileNamePrefix
	 *            Префикс SVG файлов, содержащих орнаменты
	 * @param pTextureSize
	 *            Размер текстуры орнамента
	 */
	public static AssetOrnamentManager createAssetOrnamentManager(
			final Context pContext, final String pAssetBasePath,
			final String pFileNamePrefix, final TextureManager pTextureManager,
			final int pTextureSize) {
		return new AssetOrnamentManager(pContext, pAssetBasePath,
				pFileNamePrefix, pTextureManager, pTextureSize);
	}
}
