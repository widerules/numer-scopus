package com.kvadratin.numerscopus.fractal.splitter;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.kvadratin.numerscopus.fractal.FractalPart;

public interface IFractalSplitter {
	/**
	 * Максимальное количество субчастей для алгоритмов разбиения, реализующих интерфейс 
	 */
	public final static int MAX_SUBPARTS_COUNT = 2;

	/**
	 * Производит разбиение части фрактала на {@link getSubpartCount}
	 * непересекающихся субчастей входящих в pPart
	 * 
	 * @param pPart
	 *            Исходная часть для разбиения
	 * @return Коллекцию субчастей - результат разбиения
	 */
	FractalPart[] split(FractalPart pPart);

	/**
	 * Рисует разбиение на pCanvas для pPart
	 * 
	 * @param pCanvas
	 */
	void draw(FractalPart pPart, Canvas pCanvas, Paint pPaint);

	/**
	 * Возвращает уникальный идентификатор алгоритма разбиения
	 * 
	 * @return UID
	 */
	int getId();

	/**
	 * Возвращает количество субчастей, получаемых в результате применения
	 * алгоритма. Значение должно быть меньше или равно
	 * MAX_SUBPARTS_COUNT
	 * 
	 * @return
	 */
	int getSubpartCount();

	/**
	 * Коэффициент заполнения исходного пространства. Fill factor = (source
	 * area)/sum(subparts areas)
	 * 
	 * @return (0; 1]
	 */
	float getFillFactor();

	/**
	 * Коэффициент отношения минимальной ширины субчасти, получаемой в
	 * результате разбиения, к ширине исходной части
	 * 
	 * @return (0; 1]
	 */
	float getMinWidthFactor();

	/**
	 * Коэффициент отношения минимальной высоты субчасти, получаемой в
	 * результате разбиения, к высоте исходной части
	 * 
	 * @return (0; 1]
	 */
	float getMinHeightFactor();
}
