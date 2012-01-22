package com.kvadratin.numerscopus.fractal;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.kvadratin.numerscopus.fractal.splitter.FractalSplitterManager;
import com.kvadratin.numerscopus.fractal.splitter.IFractalSplitter;

public class FractalPart {

	// --------------------------------------------------------------------
	// Поля
	// --------------------------------------------------------------------
	private RectF mField;
	private FractalPart[] mChild;
	private IFractalSplitter mSplitter;

	// --------------------------------------------------------------------
	// Конструкторы
	// --------------------------------------------------------------------
	public FractalPart(RectF pField) {
		mField = pField;
	}

	// --------------------------------------------------------------------
	// Методы
	// --------------------------------------------------------------------
	/**
	 * Производит разбиение текущей области
	 * 
	 * @param pSplitter
	 *            Алгоритм разбиения
	 */
	public void split(IFractalSplitter pSplitter) {
		this.clear();

		mSplitter = pSplitter;
		mChild = mSplitter.split(this);
	}

	/**
	 * Очищает область от разбиений
	 */
	public void clear() {
		if (mChild != null)
			for (int i = 0; i < mChild.length; i++) {
				if (mChild[i] != null) {
					mChild[i].clear();
					mChild[i] = null;
				}
			}
	}

	/**
	 * Рисует область на pCanvas
	 * 
	 * @param pCanvas
	 */
	public void draw(Canvas pCanvas, Paint pPaint) {
		if (mChild != null)
			for (FractalPart fp : mChild) {
				if (fp != null)
					fp.draw(pCanvas, pPaint);
			}

		if (mSplitter != null)
			mSplitter.draw(this, pCanvas, pPaint);
	}

	/**
	 * Производит проверку на принадлежность точки, текущей области
	 * 
	 * @param pX
	 * @param pY
	 * @return Если область содержит точку, вернет true, иначе false
	 */
	public boolean contains(float pX, float pY) {
		return mField.contains(pX, pY);
	}

	/**
	 * Производит проверку на принадлежность pRect, текущей области
	 * 
	 * @param pRect
	 * @return Если область содержит pRect, вернет true, иначе false
	 */
	public boolean contains(RectF pRect) {
		return mField.contains(pRect);
	}

	/**
	 * Вернет подобласть содержащую точку
	 * 
	 * @param pX
	 * @param pY
	 * @return
	 */
	public FractalPart getPart(float pX, float pY) {

		FractalPart result = null;

		if (this.contains(pX, pY)) {
			if (mChild != null)
				for (FractalPart fp : mChild) {
					if (fp != null)
						result = fp.getPart(pX, pY);

					if (result != null)
						break;
				}
			if (result == null)
				result = this;
		}
		return result;
	}

	/**
	 * Вернет ширину области
	 * 
	 * @return
	 */
	public float getWidth() {
		return mField.width();
	}

	/**
	 * Вернет высоту области
	 * 
	 * @return
	 */
	public float getHeight() {
		return mField.height();
	}

	/**
	 * Вернет площадь области
	 */
	public float getFieldArea() {
		return this.getWidth() * this.getHeight();
	}
	
	/**
	 * Вернет позицию по X
	 * @return
	 */
	public float getX(){
		return mField.left;
	}
	
	/**
	 * Вернет позицию по Y
	 * @return
	 */
	public float getY(){
		return mField.top;
	}
	
	/**
	 * Вернет поле области
	 * 
	 * @return
	 */
	public RectF getField() {
		return mField;
	}

	/**
	 * Вернет идентификатор алгоритма разбиения, примененного к области
	 * 
	 * @return
	 */
	public int getSplitterId() {
		return mSplitter != null ? mSplitter.getId()
				: FractalSplitterManager.EMPTY_SPLITTER;
	}

	/**
	 * Вернет true, если к области был применен алгоритм разбиения
	 * 
	 * @return
	 */
	public boolean isSplitted() {
		return mSplitter != null ? true : false;
	}

	public FractalPart[] getChildren() {
		return mChild;
	}
}
