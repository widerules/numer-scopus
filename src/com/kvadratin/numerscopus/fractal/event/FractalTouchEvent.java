package com.kvadratin.numerscopus.fractal.event;

import com.kvadratin.numerscopus.fractal.Fractal;
import com.kvadratin.numerscopus.fractal.NumberFractalPart;

public class FractalTouchEvent {
	
	private Fractal mSource;
	private NumberFractalPart mTouchedPart;
	private boolean mIsDoubleClick;
			
	public FractalTouchEvent(Fractal pSource, NumberFractalPart pTouchedPart, boolean pIsDoubleClick){
		mSource = pSource;
		mTouchedPart = pTouchedPart;
		mIsDoubleClick = pIsDoubleClick;
	}
	
	public Fractal getSource(){
		return mSource;
	}
	
	public NumberFractalPart getTouchedPart(){
		return mTouchedPart;
	}
	
	public boolean isDoubleClick(){
		return mIsDoubleClick;
	}
}
