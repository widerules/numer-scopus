package com.kvadratin.numerscopus.fractal.splitter;

import java.util.HashSet;

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
	public HashSet<FractalPart> split(FractalPart pPart) {
		return new HashSet<FractalPart>();
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
