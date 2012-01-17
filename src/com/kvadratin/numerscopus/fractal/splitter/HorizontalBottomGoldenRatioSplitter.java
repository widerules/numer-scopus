package com.kvadratin.numerscopus.fractal.splitter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.kvadratin.numerscopus.fractal.FractalPart;

public class HorizontalBottomGoldenRatioSplitter implements IFractalSplitter {

	@Override
	public void draw(FractalPart pPart, Canvas pCanvas) {
		Paint paint = new Paint();
		paint.setARGB(255, 255, 109, 27);
		paint.setStrokeWidth(3);

		pCanvas.drawLine(pPart.getField().left, pPart.getField().top
				+ pPart.getHeight() * (1 - this.getMinHeightFactor()), pPart
				.getField().right, pPart.getField().top + pPart.getHeight()
				* (1 - this.getMinHeightFactor()), paint);
	}

	@Override
	public float getFillFactor() {
		return 1f;
	}

	@Override
	public int getId() {
		return FractalSplitterManager.HORIZONTAL_BOTTOM_GOLDEN_RATIO_SPLITTER;
	}

	@Override
	public float getMinHeightFactor() {
		return 0.382f;
	}

	@Override
	public float getMinWidthFactor() {
		return 1f;
	}

	@Override
	public int getSubpartCount() {
		return 2;
	}

	@Override
	public FractalPart[] split(FractalPart pPart) {
		FractalPart[] result = new FractalPart[2];

		result[0] = new FractalPart(new RectF(pPart.getField().left, pPart
				.getField().top, pPart.getField().right, pPart.getField().top
				+ pPart.getHeight() * (1 - this.getMinHeightFactor())));

		result[1] = new FractalPart(new RectF(pPart.getField().left, pPart
				.getField().top
				+ pPart.getHeight() * (1 - this.getMinHeightFactor()), pPart
				.getField().right, pPart.getField().bottom));

		return result;
	}

}
