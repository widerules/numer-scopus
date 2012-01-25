package com.kvadratin.numerscopus.fractal;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.text.Text;

import android.graphics.RectF;

public class NumberFractalPart extends FractalPart {

	private int mOrnamentId;
	private int mFontId;
	private int mNumber;

	private IEntity mOrnamentEntity;
	private Text mNumberText;

	public NumberFractalPart(RectF pField) {
		super(pField);

		mOrnamentId = -1;
		mFontId = -1;
		mNumber = -1;
	}

	public void init(final IEntity pOrnamentEntity, final Text pNumberText,
			final int pNumber, final int pFontId, final int pOrnamentId) {
		mOrnamentEntity = pOrnamentEntity;
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
		mOrnamentEntity = null;
		mNumberText = null;
		mOrnamentId = -1;
		mFontId = -1;
		mNumber = -1;		
	}

	public IEntity getOrnamentEntity() {
		return mOrnamentEntity;
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
