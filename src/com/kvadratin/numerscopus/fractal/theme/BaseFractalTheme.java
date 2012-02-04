package com.kvadratin.numerscopus.fractal.theme;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.IEntityModifier;
import org.anddev.andengine.entity.modifier.ScaleAtModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.scene.background.IBackground;

import android.graphics.Color;
import android.graphics.Paint;

import com.kvadratin.numerscopus.fractal.NumberFractalPart;
import com.kvadratin.numerscopus.fractal.theme.font.IFontManager;
import com.kvadratin.numerscopus.fractal.theme.ornament.IOrnamentManager;

public class BaseFractalTheme implements IFractalTheme {

	int mTextColor;
	int mActiveTextColor;
	int mDisabledTextColor;
	int mNextTextColor;

	boolean mIsBorderVisible;
	Paint mBorderPaint;

	IBackground mBackground;
	IFontManager mFontManager;
	IOrnamentManager mOrnamentManager;

	public BaseFractalTheme(IFontManager pFontManager,
			IOrnamentManager pOrnamentManager) {
		mTextColor = Color.argb(255, 68, 24, 24);
		mActiveTextColor = Color.argb(255, 212, 0, 0);
		mDisabledTextColor = Color.DKGRAY;
		mNextTextColor = Color.argb(255, 1, 51, 151);

		mIsBorderVisible = false;

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
	public IEntityModifier getOnClickOrnametModifier(NumberFractalPart pPart) {
		return null;
	}

	@Override
	public IEntityModifier getOnClickTextModifier(NumberFractalPart pPart) {
		IEntity text = pPart.getNumberText();

		return new SequenceEntityModifier(new ScaleAtModifier(0.1f, text
				.getScaleX(), text.getScaleX() * 0.9f, text.getScaleCenterX(),
				text.getScaleCenterY()), new ScaleAtModifier(0.1f, text
				.getScaleX() * 0.9f, text.getScaleX(), text.getScaleCenterX(),
				text.getScaleCenterY()));
	}
}
