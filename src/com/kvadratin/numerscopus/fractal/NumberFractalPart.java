package com.kvadratin.numerscopus.fractal;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;

import android.graphics.RectF;

public class NumberFractalPart extends FractalPart {

	private int mOrnamentId;
	private int mFontId;
	private int mNumber;

	private Sprite mOrnamentSprite;
	private Text mNumberText;

	public NumberFractalPart(RectF pField) {
		super(pField);

		mOrnamentId = -1;
		mFontId = -1;
		mNumber = -1;
	}

	public void init(final Sprite pOrnamentSprite, final Text pNumberText,
			final int pNumber, final int pFontId, final int pOrnamentId) {
		mOrnamentSprite = pOrnamentSprite;
		mNumberText = pNumberText;
		mNumber = pNumber;
		mFontId = pFontId;
		mOrnamentId = pOrnamentId;
	}

	public boolean isInit() {
		return mNumber == -1 ? false : true;
	}
	
	@Override
	public void clear(){
		mOrnamentSprite = null;
		mNumberText = null;
		mOrnamentId = -1;
		mFontId = -1;
		mNumber = -1;		
	}

	public Sprite getOrnamentSprite() {
		return mOrnamentSprite;
	}

	public Text getNumberText() {
		return mNumberText;
	}

	public int getNumber() {
		return mNumber;
	}

	public int getFontId() {
		return mFontId;
	}

	public int getOrnamentId() {
		return mOrnamentId;
	}
}
