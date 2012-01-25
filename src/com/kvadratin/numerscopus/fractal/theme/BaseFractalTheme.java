package com.kvadratin.numerscopus.fractal.theme;

import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.scene.background.IBackground;

import android.graphics.Color;
import android.graphics.Paint;

import com.kvadratin.numerscopus.fractal.theme.font.IFontManager;
import com.kvadratin.numerscopus.fractal.theme.ornament.IOrnamentManager;
import com.kvadratin.numerscopus.utils.ColorHelper;

public class BaseFractalTheme implements IFractalTheme {
	
	int mTextColor;
	float mTextColorAlpha;
	float mTextColorRed;
	float mTextColorGreen;
	float mTextColorBlue;
	
	int mActiveTextColor;
	float mActiveTextColorAlpha;
	float mActiveTextColorRed;
	float mActiveTextColorGreen;
	float mActiveTextColorBlue;
	
	int mDisabledTextColor;
	float mDisabledTextColorAlpha;
	float mDisabledTextColorRed;
	float mDisabledTextColorGreen;
	float mDisabledTextColorBlue;
	
	int mNextTextColor;
	float mNextTextColorAlpha;
	float mNextTextColorRed;
	float mNextTextColorGreen;
	float mNextTextColorBlue;
	
	boolean mIsBorderVisible;
	Paint mBorderPaint;
	
	IBackground mBackground;
	IFontManager mFontManager;
	IOrnamentManager mOrnamentManager;
	
	public BaseFractalTheme(IFontManager pFontManager, IOrnamentManager pOrnamentManager){
		mTextColor = Color.argb(255, 68, 24, 24);
		mTextColorAlpha = ColorHelper.alpha(mTextColor);
		mTextColorRed = ColorHelper.red(mTextColor);
		mTextColorGreen = ColorHelper.green(mTextColor);
		mTextColorBlue = ColorHelper.blue(mTextColor);		
		
		mActiveTextColor = Color.argb(255, 212, 0, 0);
		mActiveTextColorAlpha = ColorHelper.alpha(mActiveTextColor);
		mActiveTextColorRed = ColorHelper.red(mActiveTextColor);
		mActiveTextColorGreen = ColorHelper.green(mActiveTextColor);
		mActiveTextColorBlue = ColorHelper.blue(mActiveTextColor);	
		
		mDisabledTextColor = Color.argb(255, 86, 82, 72);
		mDisabledTextColorAlpha = ColorHelper.alpha(mDisabledTextColor);
		mDisabledTextColorRed = ColorHelper.red(mDisabledTextColor);
		mDisabledTextColorGreen = ColorHelper.green(mDisabledTextColor);
		mDisabledTextColorBlue = ColorHelper.blue(mDisabledTextColor);
		
		mNextTextColor = Color.argb(255, 1, 51, 151);
		mNextTextColorAlpha = ColorHelper.alpha(mNextTextColor);
		mNextTextColorRed = ColorHelper.red(mNextTextColor);
		mNextTextColorGreen = ColorHelper.green(mNextTextColor);
		mNextTextColorBlue = ColorHelper.blue(mNextTextColor);
		
		mIsBorderVisible = true;
		
		mBorderPaint = new Paint();		
		mBorderPaint.setARGB(255, 255, 109, 27);
		mBorderPaint.setStrokeWidth(3);
		
		mBackground = new ColorBackground(1f, 1f, 1f);
		mFontManager = pFontManager;
		mOrnamentManager = pOrnamentManager;
	}
	
	@Override
	public int getActiveTextColor() {
		return mActiveTextColor;
	}

	@Override
	public IBackground getBackground() {
		return mBackground;
	}

	@Override
	public int getDisabledTextColor() {
		return mDisabledTextColor;
	}

	@Override
	public IFontManager getFontManager() {
		return mFontManager;
	}

	@Override
	public int getNextTextColor() {
		return mNextTextColor;
	}

	@Override
	public IOrnamentManager getOrnamentManager() {
		return mOrnamentManager;
	}

	@Override
	public int getTextColor() {
		return mTextColor;
	}

	@Override
	public boolean isBorderVisible() {
		return mIsBorderVisible;
	}

	@Override
	public Paint getBorderPaint() {
		return mBorderPaint;
	}

	@Override
	public float getActiveTextColorAlpha() {
		return mActiveTextColorAlpha;
	}

	@Override
	public float getActiveTextColorBlue() {
		return mActiveTextColorBlue;
	}

	@Override
	public float getActiveTextColorGreen() {
		return mActiveTextColorGreen;
	}

	@Override
	public float getActiveTextColorRed() {
		return mActiveTextColorRed;
	}

	@Override
	public float getDisabledTextColorAlpha() {
		return mDisabledTextColorAlpha;
	}

	@Override
	public float getDisabledTextColorBlue() {
		return mDisabledTextColorBlue;
	}

	@Override
	public float getDisabledTextColorGreen() {
		return mDisabledTextColorGreen;
	}

	@Override
	public float getDisabledTextColorRed() {
		return mDisabledTextColorRed;
	}

	@Override
	public float getNextTextColorAlpha() {
		return mNextTextColorAlpha;
	}

	@Override
	public float getNextTextColorBlue() {
		return mNextTextColorBlue;
	}

	@Override
	public float getNextTextColorGreen() {
		return mNextTextColorGreen;
	}

	@Override
	public float getNextTextColorRed() {
		return mNextTextColorRed;
	}

	@Override
	public float getTextColorAlpha() {
		return mTextColorAlpha;
	}

	@Override
	public float getTextColorBlue() {
		return mTextColorBlue;
	}

	@Override
	public float getTextColorGreen() {
		return mTextColorGreen;
	}

	@Override
	public float getTextColorRed() {
		return mTextColorRed;
	}

}
