package com.kvadratin.numerscopus.fractal.splitter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.kvadratin.numerscopus.fractal.FractalPart;

public class VerticalRightGoldenRatioSplitter implements IFractalSplitter {

	@Override
	public void draw(FractalPart pPart, Canvas pCanvas, Paint pPaint) {
		pCanvas.drawLine(pPart.getField().left + pPart.getWidth()
				* (1 - this.getMinWidthFactor()), pPart.getField().top, pPart
				.getField().left
				+ pPart.getWidth() * (1 - this.getMinWidthFactor()),
				pPart.getField().bottom, pPaint);
	}

	@Override
	public float getFillFactor() {
		return 1f;
	}

	@Override
	public int getId() {
		return FractalSplitterManager.VERTICAL_RIGHT_GOLDEN_RATIO_SPLITTER;
	}

	@Override
	public float getMinHeightFactor() {
		return 1f;
	}

	@Override
	public float getMinWidthFactor() {
		return 0.382f;
	}

	@Override
	public int getSubpartCount() {
		return 2;
	}

	@Override
	public FractalPart[] split(FractalPart pPart) {
		FractalPart[] result = new FractalPart[2];

		result[0] = new FractalPart(new RectF(pPart.getField().left, pPart
				.getField().top, pPart.getField().left + pPart.getWidth()
				* (1 - this.getMinWidthFactor()), pPart.getField().bottom));

		result[1] = new FractalPart(new RectF(pPart.getField().left
				+ pPart.getWidth() * (1 - this.getMinWidthFactor()),
				pPart.getField().top, pPart.getField().right,
				pPart.getField().bottom));

		return result;
	}

}
