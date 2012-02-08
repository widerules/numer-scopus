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

public class MushroomFractalTheme implements IFractalTheme {

	private IBackground mBackground;
	private IFontManager mFontManager;
	private IOrnamentManager mOrnamentManager;

	public MushroomFractalTheme(IFontManager pFontManager,
			IOrnamentManager pOrnamentManager) {
		mBackground = new ColorBackground(1f, 1f, 1f);
		mFontManager = pFontManager;
		mOrnamentManager = pOrnamentManager;
	}

	@Override
	public int getActiveTextColor() {
		return Color.BLACK;
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
		return Color.argb(255, 109, 42, 0);
	}

	@Override
	public IFontManager getFontManager() {
		return mFontManager;
	}

	@Override
	public int getNextTextColor() {
		return Color.argb(255, 160, 160, 160);
	}

	@Override
	public IOrnamentManager getOrnamentManager() {
		return mOrnamentManager;
	}

	@Override
	public int getTextColor() {
		return Color.argb(255, 51, 51, 51);
	}

	@Override
	public boolean isBorderVisible() {
		return false;
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
