package com.kvadratin.numerscopus.fractal;

import com.kvadratin.numerscopus.fractal.splitter.FractalSplitterManager;

import android.graphics.Canvas;
import android.graphics.RectF;

public class Fractal {
	
	private FractalPart mMainPart;
	
	public Fractal(RectF pField){
		mMainPart = new FractalPart(pField);
	}
	
	public void split(FractalSplitterManager pSplitterManager){
		mMainPart.clear();
		mMainPart.split(pSplitterManager, 3);
	}
	
	public void draw(Canvas pCanvas){
		mMainPart.draw(pCanvas);
	}
}
