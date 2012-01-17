package com.kvadratin.numerscopus.utils;

/**
 * Таблица квадратов натуральных чисел
 * 
 * @author bargatin
 * 
 */
public class SquaresTable {

	private int[] mSquares;

	public SquaresTable(final int pMaxNumber) {
		mSquares = new int[pMaxNumber];
		for (int i = 1; i <= pMaxNumber; i++) {
			mSquares[i - 1] = i * i;
		}
	}
	
	/**
	 * Возвращает максимальное число из таблицы, для которого расчитан его квадрат 
	 * @return Максимальное число
	 */
	public int getMaxNumber() {
		return mSquares.length;
	}
	
	/**
	 * Возвращает квадрат максимального числа из таблицы 
	 * @return Максимальный квадрат числа
	 */
	public int getMaxSquare() {
		return mSquares[mSquares.length - 1];
	}
	
	/**
	 * Возвращает минимальный квадрат числа, большый или равный исходной площади
	 * @param pSourceSquare Исходная площадь
	 * @return Квадрат числа
	 */
	public int getNearestSquare(final int pSourceSquare){
		int result = 0;
		
		for(int i = 0; i < pSourceSquare; i++){
			if (mSquares[i] >= pSourceSquare){
				result = mSquares[i];
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * Возвращает минимальное число, квадрат которого, больше или равен исходной площади
	 * @param pSourceSquare Исходная площадь
	 * @return Число, квадрат которого, больше или равен исходной площади
	 */
	public int getNearestNumber(final int pSourceSquare){
		int result = 0;
		
		for(int i = 0; i < pSourceSquare; i++){
			if (mSquares[i] >= pSourceSquare){
				result = i + 1;
				break;
			}
		}
		
		return result;
	}
}
