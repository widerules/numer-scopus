package com.kvadratin.numerscopus;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.content.Context;
import android.graphics.Color;

import com.kvadratin.numerscopus.fractal.theme.FractalThemeFactory;
import com.kvadratin.numerscopus.fractal.theme.IFractalTheme;
import com.kvadratin.numerscopus.fractal.theme.font.FontManagerFactory;
import com.kvadratin.numerscopus.fractal.theme.font.IFontManager;
import com.kvadratin.numerscopus.fractal.theme.ornament.IOrnamentManager;
import com.kvadratin.numerscopus.fractal.theme.ornament.OrnamentManagerFactory;

public final class ResourceManager {

	private static ResourceManager instance;

	private ResourceManager(final Context pContext, final Engine pEngine) {

		mEngine = pEngine;
		mContext = pContext;

		mTextures = new BitmapTextureAtlas[GFX_COUNT];
		mRegions = new TextureRegion[GFX_COUNT];

		SVGBitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		FontFactory.setAssetBasePath("fonts/");

		// Загружаем текстуры
		loadTexture(GFX_STATUS_STAR, 16, 16, "score.svg");
		loadTexture(GFX_STATUS_TIME, 16, 16, "timer.svg");

		// Загружаем шрифты
		mFonts = new IFontManager[FONT_MANAGER_COUNT];
		mFonts[FONT_MANAGER_MENU] = FontManagerFactory.createAssetFontManager(
				mContext, mEngine.getFontManager(),
				mEngine.getTextureManager(), "fonts", "menu_", Color.argb(255,
						255, 255, 255), Color.argb(255, 255, 255, 255), 48, 1,
				false);
		mFonts[FONT_MANAGER_FRACTAL] = FontManagerFactory
				.createAssetFontManager(mContext, mEngine.getFontManager(),
						mEngine.getTextureManager(), "fonts", "base_", Color
								.argb(255, 255, 255, 255), Color.argb(255, 255,
								255, 255), 120, 1, false);

		// Загружаем орнаметы
		mOrnaments = new IOrnamentManager[ORNAMENTS_COUNT];
		mOrnaments[ORNAMENT_BASE] = OrnamentManagerFactory
				.createAssetOrnamentManager(mContext, "gfx", "orn_", mEngine
						.getTextureManager(), 256);
		mOrnaments[ORNAMENT_MUSHROOM] = OrnamentManagerFactory
				.createColorSpriteOrnamentManager(mContext, "gfx",
						"mushroom_orn", mEngine.getTextureManager(), 256);

		// Загружаем темы
		mThemes = new IFractalTheme[THEMES_COUNT];
		mThemes[THEME_BASE] = FractalThemeFactory.createBaseFractalTheme(
				mFonts[FONT_MANAGER_FRACTAL], mOrnaments[ORNAMENT_BASE]);
		mThemes[THEME_CLEAR_GREY] = FractalThemeFactory
				.createClearGreyFractalTheme(mFonts[FONT_MANAGER_FRACTAL]);
		mThemes[THEME_COLOR_RECT] = FractalThemeFactory
				.createColorRectFractalTheme(mFonts[FONT_MANAGER_FRACTAL],
						OrnamentManagerFactory.createColorRectOrnamentManager());
		mThemes[THEME_MUSHROOM] = FractalThemeFactory
				.createMushroomFractalTheme(mFonts[FONT_MANAGER_FRACTAL],
						mOrnaments[ORNAMENT_MUSHROOM]);
	}

	public static synchronized void init(final Context pContext,
			final Engine pEngine) {
		if (instance == null) {
			instance = new ResourceManager(pContext, pEngine);
		}
	}

	public static synchronized ResourceManager getInstance() {
		return instance;
	}

	// ---------------------------------------------------------------
	// Константы
	// ---------------------------------------------------------------
	public final static int ORNAMENTS_COUNT = 2;
	public final static int ORNAMENT_BASE = 0;
	public final static int ORNAMENT_MUSHROOM = 1;

	public final static int FONT_MANAGER_COUNT = 2;
	public final static int FONT_MANAGER_MENU = 0;
	public final static int FONT_MANAGER_FRACTAL = 1;

	public final static int GFX_COUNT = 2;
	public final static int GFX_STATUS_STAR = 0;
	public final static int GFX_STATUS_TIME = 1;

	public final static int THEMES_COUNT = 4;
	public final static int THEME_BASE = 0;
	public final static int THEME_CLEAR_GREY = 1;
	public final static int THEME_COLOR_RECT = 2;
	public final static int THEME_MUSHROOM = 3;

	// ---------------------------------------------------------------
	// Поля
	// ---------------------------------------------------------------
	private Context mContext;
	private Engine mEngine;

	private IOrnamentManager[] mOrnaments;
	private IFontManager[] mFonts;
	private IFractalTheme[] mThemes;

	private BitmapTextureAtlas[] mTextures;
	private TextureRegion[] mRegions;

	// ---------------------------------------------------------------
	// Методы
	// ---------------------------------------------------------------
	private void loadTexture(final int indx, final int width, final int height,
			String name) {
		mTextures[indx] = new BitmapTextureAtlas(width, height,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		mRegions[indx] = SVGBitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mTextures[indx], mContext, name, width,
						height, 0, 0);

		mEngine.getTextureManager().loadTexture(mTextures[indx]);
	}

	public IFontManager getFontManager(final int indx) {
		return mFonts[indx];
	}

	public IFractalTheme getFractalTheme(final int indx) {
		return mThemes[indx];
	}

	public Sprite getSprite(final int indx) {
		return new Sprite(0, 0, mRegions[indx]);
	}
}
