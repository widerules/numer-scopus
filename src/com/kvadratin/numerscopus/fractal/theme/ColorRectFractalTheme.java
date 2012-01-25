package com.kvadratin.numerscopus.fractal.theme;

import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.scene.background.IBackground;

import android.graphics.Color;
import android.graphics.Paint;

import com.kvadratin.numerscopus.fractal.theme.font.IFontManager;
import com.kvadratin.numerscopus.fractal.theme.ornament.IOrnamentManager;
import com.kvadratin.numerscopus.utils.ColorHelper;

public class ColorRectFractalTheme implements IFractalTheme {
	
	private IBackground mBackground;
	private IFontManager mFontManager;
	private IOrnamentManager mOrnamentManager;
	
	public ColorRectFractalTheme(IFontManager pFontManager, IOrnamentManager pOrnamentManager){
		mBackground = new ColorBackground(1f, 1f, 1f);
		mFontManager = pFontManager;
		mOrnamentManager = pOrnamentManager;
	}
	
	@Override
	public int getActiveTextColor() {
		return Color.WHITE;
	}

	@Override
	public float getActiveTextColorAlpha() {
		return 1f;
	}

	@Override
	public float getActiveTextColorBlue() {
		return 1f;
	}

	@Override
	public float getActiveTextColorGreen() {
		return 1f;
	}

	@Override
	public float getActiveTextColorRed() {
		return 1f;
	}

	@Override
	public IBackground getBackground() {
		return mBackground;
	}

	@Override
	public Paint getBorderPaint() {
		return null;
	}

	@Override
	public int getDisabledTextColor() {
		return Color.GRAY;
	}

	@Override
	public float getDisabledTextColorAlpha() {
		return 1f;
	}

	@Override
	public float getDisabledTextColorBlue() {
		return ColorHelper.blue(Color.GRAY);
	}

	@Override
	public float getDisabledTextColorGreen() {
		return ColorHelper.green(Color.GRAY);
	}

	@Override
	public float getDisabledTextColorRed() {
		return ColorHelper.red(Color.GRAY);
	}

	@Override
	public IFontManager getFontManager() {
		return mFontManager;
	}

	@Override
	public int getNextTextColor() {
		return Color.BLUE;
	}

	@Override
	public float getNextTextColorAlpha() {
		return 1f;
	}

	@Override
	public float getNextTextColorBlue() {
		return ColorHelper.blue(Color.BLUE);
	}

	@Override
	public float getNextTextColorGreen() {
		return ColorHelper.green(Color.BLUE);
	}

	@Override
	public float getNextTextColorRed() {
		return ColorHelper.red(Color.BLUE);
	}

	@Override
	public IOrnamentManager getOrnamentManager() {
		return mOrnamentManager;
	}

	@Override
	public int getTextColor() {
		return Color.WHITE;
	}

	@Override
	public float getTextColorAlpha() {
		return 1f;
	}

	@Override
	public float getTextColorBlue() {
		return 1f;
	}

	@Override
	public float getTextColorGreen() {
		return 1f;
	}

	@Override
	public float getTextColorRed() {
		return 1f;
	}

	@Override
	public boolean isBorderVisible() {
		return false;
	}

}
