package com.kvadratin.numerscopus.font;

import org.anddev.andengine.opengl.font.FontManager;
import org.anddev.andengine.opengl.texture.TextureManager;

import android.content.Context;

public class FontManagerFactory {
	
	public static AssetFontManager createAssetFontManager(
			final Context pContext, final FontManager pFontManager,
			final TextureManager pTextureManager, final String pAssetBasePath,
			final String pFileNamePrefix, final int pFontColor,
			final int pFontStrokeColor, final int pFontSize,
			final float pStrokeWidth, final boolean pIsStroke) {

		return new AssetFontManager(pContext, pFontManager, pTextureManager,
				pAssetBasePath, pFileNamePrefix, pFontColor, pFontStrokeColor,
				pFontSize, pStrokeWidth, pIsStroke);
	}
}
