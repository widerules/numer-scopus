package com.kvadratin.numerscopus.fractal.splitter;

import com.kvadratin.numerscopus.fractal.FractalPart;

import android.graphics.Canvas;

public class EmptySplitter implements IFractalSplitter{

	@Override
	public void draw(FractalPart pPart, Canvas pCanvas) {		
	}

	@Override
	public int getId() {
		return FractalSplitterManager.EMPTY_SPLITTER;
	}

	@Override
	public FractalPart[] split(FractalPart pPart) {
		return new FractalPart[1];
	}

	@Override
	public float getFillFactor() {
		return 1;
	}

	@Override
	public float getMinHeightFactor() {
		return 1;
	}

	@Override
	public float getMinWidthFactor() {
		return 1;
	}

	@Override
	public int getSubpartCount() {
		return 1;
	}

}
