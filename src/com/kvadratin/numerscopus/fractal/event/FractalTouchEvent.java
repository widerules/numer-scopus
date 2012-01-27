package com.kvadratin.numerscopus.fractal.event;

import com.kvadratin.numerscopus.fractal.NumberFractalPart;

public class FractalTouchEvent {
	
	private NumberFractalPart mTouchedPart;
	
	public FractalTouchEvent(NumberFractalPart pTouchedPart){
		mTouchedPart = pTouchedPart;
	}
	
	public NumberFractalPart getTouchedPart(){
		return mTouchedPart;
	}
}
