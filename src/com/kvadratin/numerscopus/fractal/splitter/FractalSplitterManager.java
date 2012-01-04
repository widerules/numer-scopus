package com.kvadratin.numerscopus.fractal.splitter;

import java.util.Random;

import android.graphics.RectF;
import android.util.DisplayMetrics;

import com.kvadratin.numerscopus.fractal.FractalPart;
import com.kvadratin.numerscopus.utils.SquaresTable;

/**
 * Менеджер алгоритмов разбиения
 * 
 * @author bargatin
 * 
 */
public class FractalSplitterManager {

	// --------------------------------------------------------------------
	// Константы
	// --------------------------------------------------------------------
	/**
	 * Количество доступных алгоритмов разбиения
	 */
	public final static int SPLITTERS_COUNT = 7;

	/**
	 * Количество доступных алгоритмов, разбивающих исходную область по
	 * горизонтали
	 */
	public final static int HORIZONTAL_SPLITTERS_COUNT = 3;

	/**
	 * Количество доступных алгоритмов, разбивающих исходную область по
	 * вертикали
	 */
	public final static int VERTICAL_SPLITTERS_COUNT = 3;

	/**
	 * Идентификатор путого алгоритма разбиения
	 * 
	 * @see EmptySplitter
	 */
	public final static int EMPTY_SPLITTER = 0;

	/**
	 * Идентификатор алгоритма разбиения, разделяющего исходную область на две
	 * равные половины по горизонтали
	 * 
	 * @see HorizontalDoubleSplitter
	 */
	public final static int HORIZONTAL_DOUBLE_SPLITTER = 1;

	/**
	 * Идентификатор алгоритма разбиения, разделяющего исходную область на две
	 * равные половины по вертикали
	 * 
	 * @see VerticalDoubleSplitter
	 */
	public final static int VERTICAL_DOUBLE_SPLITTER = 2;

	/**
	 * Идентификатор алгоритма разбиения, разделяющего исходную область на две
	 * половины по горизонтали, по линии золотого сечения (меньшая часть
	 * разбиения находится сверху)
	 * 
	 * @see HorizontalTopGoldenRatioSplitter
	 */
	public final static int HORIZONTAL_TOP_GOLDEN_RATIO_SPLITTER = 3;
	
	/**
	 * Идентификатор алгоритма разбиения, разделяющего исходную область на две
	 * половины по горизонтали, по линии золотого сечения (меньшая часть
	 * разбиения находится снизу)
	 * 
	 * @see HorizontalBottomGoldenRatioSplitter
	 */
	public final static int HORIZONTAL_BOTTOM_GOLDEN_RATIO_SPLITTER = 4;
	
	/**
	 * Идентификатор алгоритма разбиения, разделяющего исходную область на две
	 * половины по вертикали, по линии золотого сечения (меньшая часть
	 * разбиения находится слева)
	 * 
	 * @see VerticalLeftGoldenRatioSplitter
	 */
	public final static int VERTICAL_LEFT_GOLDEN_RATIO_SPLITTER = 5;
	
	/**
	 * Идентификатор алгоритма разбиения, разделяющего исходную область на две
	 * половины по вертикали, по линии золотого сечения (меньшая часть
	 * разбиения находится справа)
	 * 
	 * @see VerticalRightGoldenRatioSplitter
	 */
	public final static int VERTICAL_RIGHT_GOLDEN_RATIO_SPLITTER = 6;

	// --------------------------------------------------------------------
	// Поля
	// --------------------------------------------------------------------
	private IFractalSplitter[] mSplitters;
	private IFractalSplitter[] mHorizontalSplitters;
	private IFractalSplitter[] mVerticalSplitters;

	private DisplayMetrics mMetrics;
	private float mSubpartMinHeight;
	private float mSubpartMinWidth;
	private SquaresTable mSquaresTable;
	private Random mRand;

	// --------------------------------------------------------------------
	// Конструкторы
	// --------------------------------------------------------------------
	public FractalSplitterManager(final DisplayMetrics pMetrics) {
		mMetrics = pMetrics;		
		mSubpartMinWidth = 55 * mMetrics.density;
		mSubpartMinHeight = 89 * mMetrics.density;

		mSquaresTable = new SquaresTable(50);
		mRand = new Random(System.nanoTime());

		mSplitters = new IFractalSplitter[SPLITTERS_COUNT];
		mHorizontalSplitters = new IFractalSplitter[HORIZONTAL_SPLITTERS_COUNT];
		mVerticalSplitters = new IFractalSplitter[VERTICAL_SPLITTERS_COUNT];

		mSplitters[EMPTY_SPLITTER] = new EmptySplitter();
		mSplitters[HORIZONTAL_DOUBLE_SPLITTER] = new HorizontalDoubleSplitter();
		mSplitters[VERTICAL_DOUBLE_SPLITTER] = new VerticalDoubleSplitter();
		mSplitters[HORIZONTAL_TOP_GOLDEN_RATIO_SPLITTER] = new HorizontalTopGoldenRatioSplitter();
		mSplitters[HORIZONTAL_BOTTOM_GOLDEN_RATIO_SPLITTER] = new HorizontalBottomGoldenRatioSplitter();
		mSplitters[VERTICAL_LEFT_GOLDEN_RATIO_SPLITTER] = new VerticalLeftGoldenRatioSplitter();
		mSplitters[VERTICAL_RIGHT_GOLDEN_RATIO_SPLITTER] = new VerticalRightGoldenRatioSplitter();

		mHorizontalSplitters[0] = mSplitters[HORIZONTAL_DOUBLE_SPLITTER];
		mHorizontalSplitters[1] = mSplitters[HORIZONTAL_TOP_GOLDEN_RATIO_SPLITTER];
		mHorizontalSplitters[2] = mSplitters[HORIZONTAL_BOTTOM_GOLDEN_RATIO_SPLITTER];
		
		mVerticalSplitters[0] = mSplitters[VERTICAL_DOUBLE_SPLITTER];
		mVerticalSplitters[1] = mSplitters[VERTICAL_LEFT_GOLDEN_RATIO_SPLITTER];
		mVerticalSplitters[2] = mSplitters[VERTICAL_RIGHT_GOLDEN_RATIO_SPLITTER];
	}

	// --------------------------------------------------------------------
	// Методы
	// --------------------------------------------------------------------
	/**
	 * Вернет заданный алгоритм разбиения
	 * 
	 * @param pSplitterId
	 *            Идентификатор требуемого алгоритма
	 */
	public IFractalSplitter getSplitter(final int pSplitterId) {
		return mSplitters[pSplitterId];
	}

	/**
	 * Вернет случайный не пустой {@link EmptySplitter} алгоритм разбиения
	 * 
	 * @return
	 */
	public IFractalSplitter getRandomSplitter() {
		return mSplitters[mRand.nextInt(SPLITTERS_COUNT - 1) + 1];
	}

	/**
	 * Вернет случайный не пустой {@link EmptySplitter} алгоритм разбиения,
	 * горизонтальный или вертикальный, в зависимости от значения параметра
	 * isHorizontal
	 * 
	 * @param isHorizontal
	 * @return
	 */
	public IFractalSplitter getRandomSplitter(boolean isHorizontal) {
		return isHorizontal ? mHorizontalSplitters[mRand
				.nextInt(HORIZONTAL_SPLITTERS_COUNT)]
				: mVerticalSplitters[mRand.nextInt(VERTICAL_SPLITTERS_COUNT)];
	}

	/**
	 * Вернет исходную область для разбиения, площадь которой, гарантированно
	 * может быть разбита на pSubpartCount подобластей
	 * 
	 * @param pSubpartCount
	 *            Количество подобластей
	 * @return
	 */
	public FractalPart getFractalPart(final int pSubpartCount) {

		int a = mSquaresTable.getNearestNumber(pSubpartCount) + 1;
		FractalPart part = new FractalPart(new RectF(0, 0,
				a * mSubpartMinWidth, a * mSubpartMinHeight));

		return part;
	}

	public float getSubpartMinWidth() {
		return mSubpartMinWidth;
	}

	public float getSubpartMinHeight() {
		return mSubpartMinHeight;
	}
}
