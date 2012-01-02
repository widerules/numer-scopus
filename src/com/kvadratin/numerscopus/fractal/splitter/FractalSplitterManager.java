package com.kvadratin.numerscopus.fractal.splitter;

import java.util.HashMap;
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
	public final static int SPLITTERS_COUNT = 3;

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

	// --------------------------------------------------------------------
	// Поля
	// --------------------------------------------------------------------
	private HashMap<Integer, IFractalSplitter> mSplitters;
	private DisplayMetrics mMetrics;
	private float mSubpartMinHeight;
	private float mSubpartMinWidth;
	private SquaresTable mSquaresTable;

	// --------------------------------------------------------------------
	// Конструкторы
	// --------------------------------------------------------------------
	public FractalSplitterManager(final DisplayMetrics pMetrics) {
		mMetrics = pMetrics;
		mSubpartMinHeight = 50 * mMetrics.density;
		mSubpartMinWidth = 50 * mMetrics.density;

		mSquaresTable = new SquaresTable(50);

		mSplitters = new HashMap<Integer, IFractalSplitter>(SPLITTERS_COUNT);

		mSplitters.put(EMPTY_SPLITTER, new EmptySplitter());
		mSplitters.put(HORIZONTAL_DOUBLE_SPLITTER,
				new HorizontalDoubleSplitter());
		mSplitters.put(VERTICAL_DOUBLE_SPLITTER, new VerticalDoubleSplitter());
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
		return mSplitters.get(pSplitterId);
	}

	/**
	 * Вернет случайный не пустой {@link EmptySplitter} алгоритм разбиения
	 * 
	 * @return
	 */
	public IFractalSplitter getRandomSplitter() {
		return mSplitters.get((new Random(System.nanoTime()))
				.nextInt(SPLITTERS_COUNT - 1) + 1);
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
}
