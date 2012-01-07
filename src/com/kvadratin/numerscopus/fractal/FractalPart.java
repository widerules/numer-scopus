package com.kvadratin.numerscopus.fractal;

import java.util.Stack;

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
	 * Производит разбиение области на подобласти. Рекурсивный алгоритм.
	 * 
	 * @param pSplitterManager
	 *            Менеджер алгоритмов разбиения
	 * @param pSubpartCount
	 *            Требуемое количество подобластей
	 */
	@Deprecated
	public int split(FractalSplitterManager pSplitterManager, int pSubpartCount) {

		int result = 0;
		this.clear();

		Log.d("NumerScopus", "Start split: pSubpartCount = "
				+ Integer.toString(pSubpartCount));

		if (pSubpartCount <= 1) {
			mSplitter = pSplitterManager
					.getSplitter(FractalSplitterManager.EMPTY_SPLITTER);
		} else {
			if (mField.width() == mField.height()) {
				mSplitter = pSplitterManager.getRandomSplitter();
			} else if (mField.width() > mField.height()) {
				mSplitter = pSplitterManager.getRandomSplitter(false);
			} else {
				mSplitter = pSplitterManager.getRandomSplitter(true);
			}

			if (mSplitter != null) {
				mChild = mSplitter.split(this);

				float maxSubpartArea = 0;
				FractalPart maxSubpart = null;

				for (FractalPart fp : mChild) {
					if (fp != null)
						if (fp.getFieldArea() > maxSubpartArea)
							maxSubpart = fp;
				}

				int subpartCount = pSubpartCount / mChild.length;
				for (FractalPart fp : mChild) {
					if (fp != null)
						fp.split(pSplitterManager,
								(fp == maxSubpart ? subpartCount
										+ pSubpartCount % mChild.length
										: subpartCount));
				}
			} else {
				result = pSubpartCount;
			}
		}

		Log.d("NumerScopus", "End split: pSubpartCount = "
				+ Integer.toString(pSubpartCount) + "; result = "
				+ Integer.toString(result));

		return result;
	}

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
	 * Производит разбиение области на подобласти. Итерационный алгоритм.
	 * 
	 * @param pPart
	 *            Исходная область для разбиения
	 * @param pSplitterManager
	 *            Менеджер алгоритмов разбиения
	 * @param pSubpartCount
	 *            Требуемое количество подобластей
	 */
	public static void split(FractalPart pPart, int pSubpartCount,
			FractalSplitterManager pSplitterManager) {

		FractalPart currentPart = pPart;
		FractalPart[] currentChildren;
		int currentSubpartCount = pSubpartCount;

		Stack<FractalPart> parts = new Stack<FractalPart>();
		Stack<Integer> subpartCounts = new Stack<Integer>();

		boolean needNextSplit = false;

		do {
			if (currentSubpartCount <= 1) {
				// Получаем терминальный элемент
				currentPart.split(pSplitterManager
						.getSplitter(FractalSplitterManager.EMPTY_SPLITTER));

				needNextSplit = false;

			} else {
				// Разбиваем текущую область
				if (currentPart.getWidth() == currentPart.getHeight()) {
					currentPart.split(pSplitterManager.getRandomSplitter());
				} else if (currentPart.getWidth() > currentPart.getHeight()) {
					currentPart
							.split(pSplitterManager.getRandomSplitter(false));
				} else {
					currentPart.split(pSplitterManager.getRandomSplitter(true));
				}

				// Обрабатываем подобласти текущего разбиения
				currentChildren = currentPart.getChildren();
				needNextSplit = false;

				if (currentChildren != null) {
					if (currentChildren.length > 0
							&& currentChildren[0] != null) {

						float subpartFillRatio[] = new float[currentChildren.length];
						int counts[] = new int[currentChildren.length];
						int countSum = 0;

						// Распределяем дальнейшие разбиения
						for (int i = 0; i < currentChildren.length; i++) {
							if (currentChildren[i] != null) {
								subpartFillRatio[i] = currentChildren[i]
										.getFieldArea()	/ currentPart.getFieldArea();
								counts[i] = Math.round(currentSubpartCount
										* subpartFillRatio[i]);
								countSum += counts[i];
							}
						}
						counts[0] -= countSum - currentSubpartCount;

						// Помещаем в стек необработанные подобласти
						for (int i = currentChildren.length - 1; i > 0; i--) {
							if (currentChildren != null) {
								parts.push(currentChildren[i]);
								subpartCounts.push(counts[i]);
							}
						}

						// Первая подобласть становится текущей областью для
						// разбиения
						currentPart = currentChildren[0];
						currentSubpartCount = counts[0];

						needNextSplit = true;
					}
				}
			}

			if (needNextSplit == false) {
				if (!parts.empty()) {
					currentPart = parts.pop();
					currentSubpartCount = subpartCounts.pop();

					needNextSplit = true;
				}
			}
		} while (needNextSplit);
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
	public void draw(Canvas pCanvas) {
		if (mChild != null)
			for (FractalPart fp : mChild) {
				if (fp != null)
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
