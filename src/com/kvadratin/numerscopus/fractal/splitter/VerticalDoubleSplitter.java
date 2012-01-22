package com.kvadratin.numerscopus.fractal.splitter;

import com.kvadratin.numerscopus.fractal.FractalPart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class VerticalDoubleSplitter implements IFractalSplitter {

	@Override
	public void draw(FractalPart pPart, Canvas pCanvas, Paint pPaint) {
		pCanvas.drawLine(pPart.getField().left + pPart.getWidth()
				* this.getMinWidthFactor(), pPart.getField().top, pPart.getField().left
				+ pPart.getWidth() * this.getMinWidthFactor(),
				pPart.getField().bottom, pPaint);
	}

	@Override
	public int getId() {
		return FractalSplitterManager.VERTICAL_DOUBLE_SPLITTER;
	}

	@Override
	public FractalPart[] split(FractalPart pPart) {
		FractalPart[] result = new FractalPart[2];

		result[0] = new FractalPart(new RectF(pPart.getField().left, pPart
				.getField().top, pPart.getField().left + pPart.getWidth()
				* this.getMinWidthFactor(), pPart.getField().bottom));

		result[1] = new FractalPart(new RectF(pPart.getField().left
				+ pPart.getWidth() * this.getMinWidthFactor(),
				pPart.getField().top, pPart.getField().right,
				pPart.getField().bottom));

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
		return 0.5f;
	}

	@Override
	public int getSubpartCount() {
		return 2;
	}
}