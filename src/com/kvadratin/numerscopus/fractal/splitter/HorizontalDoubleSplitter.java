package com.kvadratin.numerscopus.fractal.splitter;

import java.util.HashSet;

import com.kvadratin.numerscopus.fractal.FractalPart;
import com.kvadratin.numerscopus.fractal.splitter.IFractalSplitter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class HorizontalDoubleSplitter implements IFractalSplitter{
	
	@Override
	public void draw(FractalPart pPart, Canvas pCanvas) {
		Paint paint = new Paint();
		paint.setARGB(255, 255, 109, 27);
		paint.setStrokeWidth(3);
		
		pCanvas.drawLine(pPart.getField().left, pPart.getField().top + pPart.getHeight() / 2, 
				pPart.getField().right, pPart.getField().top + pPart.getHeight() / 2, paint);		
	}

	@Override
	public int getId() {
		return FractalSplitterManager.HORIZONTAL_DOUBLE_SPLITTER;
	}

	@Override
	public HashSet<FractalPart> split(FractalPart pPart) {
		HashSet<FractalPart> result = new HashSet<FractalPart>(2);
		
		result.add(new FractalPart(new RectF(pPart.getField().left, pPart.getField().top, 
				pPart.getField().right, pPart.getField().top + pPart.getHeight() / 2)));
		
		result.add(new FractalPart(new RectF(pPart.getField().left, pPart.getField().top + pPart.getHeight() / 2, 
				pPart.getField().right, pPart.getField().bottom)));
		
		return result;
	}

	@Override
	public float getFillFactor() {
		return 1;
	}

	@Override
	public float getMinHeightFactor() {
		return 0.5f;
	}

	@Override
	public float getMinWidthFactor() {
		return 1;
	}

	@Override
	public int getSubpartCount() {
		return 2;
	}
}
