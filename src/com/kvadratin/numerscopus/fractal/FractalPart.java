package com.kvadratin.numerscopus.fractal;

import java.util.HashSet;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import com.kvadratin.numerscopus.fractal.splitter.FractalSplitterManager;
import com.kvadratin.numerscopus.fractal.splitter.IFractalSplitter;

public class FractalPart {

	// --------------------------------------------------------------------
	// Поля
	// --------------------------------------------------------------------
	private RectF mField;
	private HashSet<FractalPart> mChild;
	private IFractalSplitter mSplitter;

	// --------------------------------------------------------------------
	// Конструкторы
	// --------------------------------------------------------------------
	public FractalPart(RectF pField) {
		mField = pField;
		mChild = new HashSet<FractalPart>();
	}

	// --------------------------------------------------------------------
	// Методы
	// --------------------------------------------------------------------
	/**
	 * Производит разбиение области на подобласти
	 * 
	 * @param pSplitterManager
	 *            Менеджер алгоритмов разбиения
	 * @param pSubpartCount
	 *            Требуемое количество подобластей
	 */
	public int split(FractalSplitterManager pSplitterManager, int pSubpartCount) {
		
		int result = 0;
		this.clear();
		
		Log.d("NumerScopus", "Start split: pSubpartCount = " + Integer.toString(pSubpartCount)); 
				
		if (pSubpartCount <= 1) {
			mSplitter = pSplitterManager
					.getSplitter(FractalSplitterManager.EMPTY_SPLITTER);
		} else {
			if (mField.width() == mField.height()){
				mSplitter = pSplitterManager.getRandomSplitter();
			} else if (mField.width() > mField.height()){
				mSplitter = pSplitterManager.getRandomSplitter(false);
			} else {
				mSplitter = pSplitterManager.getRandomSplitter(true);
			}
			
			if (mSplitter != null) {
				mChild = mSplitter.split(this);

				float maxSubpartArea = 0;
				FractalPart maxSubpart = null;

				for (FractalPart fp : mChild) {
					if (fp.getFieldArea() > maxSubpartArea)
						maxSubpart = fp;
				}

				int subpartCount = pSubpartCount / mChild.size();
				for (FractalPart fp : mChild) {
					fp.split(pSplitterManager, (fp == maxSubpart ? subpartCount
							+ pSubpartCount % mChild.size() : subpartCount));
				}
			} else {
				result = pSubpartCount;
			}
		}
		
		Log.d("NumerScopus", "End split: pSubpartCount = " + Integer.toString(pSubpartCount) + "; result = " + Integer.toString(result));
		
		return result;
	}

	/**
	 * Очищает область от разбиений
	 */
	public void clear() {
		for (FractalPart fp : mChild) {
			fp.clear();
		}
		mChild.clear();
	}

	/**
	 * Рисует область на pCanvas
	 * 
	 * @param pCanvas
	 */
	public void draw(Canvas pCanvas) {
		for (FractalPart fp : mChild) {
			fp.draw(pCanvas);
		}

		if (mSplitter != null)
			mSplitter.draw(this, pCanvas);
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
			for (FractalPart fp : mChild) {
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
}
