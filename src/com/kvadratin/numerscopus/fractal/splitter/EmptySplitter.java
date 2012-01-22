package com.kvadratin.numerscopus.fractal.splitter;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.kvadratin.numerscopus.fractal.FractalPart;
import com.kvadratin.numerscopus.fractal.NumberFractalPart;

public class EmptySplitter implements IFractalSplitter{

	@Override
	public void draw(FractalPart pPart, Canvas pCanvas, Paint pPaint) {		
	}

	@Override
	public int getId() {
		return FractalSplitterManager.EMPTY_SPLITTER;
	}

	@Override
	public FractalPart[] split(FractalPart pPart) {
		FractalPart[] result = new FractalPart[1];
		result[0] = new NumberFractalPart(pPart.getField());
		
		return result;
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
