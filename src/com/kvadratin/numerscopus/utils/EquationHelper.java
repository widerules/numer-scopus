package com.kvadratin.numerscopus.utils;

import java.util.Random;

public class EquationHelper {

	public final static int MAX_PRIME_NUMBER = 10000;
	private static int[] mPrimeNumbers = sieveEuler(MAX_PRIME_NUMBER);
	private static Random mRnd = new Random();

	/**
	 * Решето Эратосфена. Алгоритм поиска простых чисел.
	 * 
	 * @param n
	 *            Верхняя граница поиска
	 * @return Массив простых чисел от 2 до n
	 */
	public static int[] sieveEratosthenes(final int n) {

		boolean[] source = new boolean[n + 1];
		int i = 2;
		int j = 0;
		int k = 0;
		int l = 0;
		int count = n - 1;

		while (i * i <= n) {
			if (!source[i]) {
				k = i * i;
				j = k;
				l = 0;
				while (j <= n) {
					if (!source[j]) {
						source[j] = true;
						count--;
					}
					j = k + i * (++l);
				}
			}
			i++;
		}

		int[] result = new int[count];
		count = 0;

		for (i = 2; i <= n; i++) {
			if (!source[i]) {
				result[count++] = i;
			}
		}
		return result;
	}

	/**
	 * Решето Эйлера. Модифицированный алгоритм "решето Эратосфена". Алгоритм
	 * поиска простых чисел.
	 * 
	 * @param n
	 *            Верхняя граница поиска
	 * @return Массив простых чисел от 2 до n
	 */
	public static int[] sieveEuler(final int n) {

		boolean[] source = new boolean[n + 1];
		int i = 2;
		int j = 0;
		int k = 0;
		int count = n - 1;

		while (i * i <= n) {
			source[i * i] = true;
			count--;
			j = i + 1;
			while (j <= n) {
				k = j * i;
				if (k <= n) {
					if (!source[k]) {
						source[k] = true;
						count--;
					}
				}
				j++;
			}
			j = i + 1;
			while (j <= n) {
				if (!source[j]) {
					i = j;
					break;
				}
				j++;
			}
		}

		int[] result = new int[count];
		count = 0;

		for (i = 2; i <= n; i++) {
			if (!source[i]) {
				result[count++] = i;
			}
		}
		return result;
	}

	/**
	 * Проверяет число на принадлежность к множеству простых чисел
	 * 
	 * @param n
	 *            Число для проверки
	 * @return Если число простое, вернет true, иначе false
	 */
	public static boolean isPrimeNumber(final int n) {

		boolean result = false;

		if (n <= MAX_PRIME_NUMBER) {
			for (int i = 0; i < mPrimeNumbers.length; i++) {
				if (mPrimeNumbers[i] == n) {
					result = true;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * Разлагает число n на простые множители
	 * 
	 * @param n
	 * @return Массив простых множителей
	 */
	public static int[] decomposeToMultipliers(final int n) {
		int[] result = null;

		if (n < 0) {
			result = null;
		} else if (n <= 3) {
			result = new int[1];
			result[0] = n;
		} else {
			// Оцениваем максимально возможное количество множителей
			int[] res = new int[(int) Math.floor(Math.log10(n) * 3.321928095f)];
			int count = 0;
			int x = n;

			while (x > 1) {
				for (int i = 0; i < mPrimeNumbers.length; i++)
					if (x % mPrimeNumbers[i] == 0) {
						res[count++] = mPrimeNumbers[i];
						x /= mPrimeNumbers[i];
						break;
					} else if (i == mPrimeNumbers.length - 1) {
						res[count++] = x;
						x = 1;
						break;
					}
			}
			System.arraycopy(res, 0, result, 0, count); 
		}

		return result;
	}

	/**
	 * Производит разложение числа n на summandsCount слагаемых
	 * 
	 * @param n
	 *            Число для разложения
	 * @param summandsCount
	 *            Количество слагаемых
	 * @return Массив слагаемых
	 */
	public static int[] decomposeToSummand(final int n, final int summandsCount) {
		int[] result = new int[summandsCount];
		int x = n;
		int dx = n / summandsCount;

		for (int i = 0; i < summandsCount - 1; i++) {
			if (x > 0) {
				result[i] = mRnd.nextInt(x > dx ? dx : x) + 1;
				x -= result[i];
			}
		}
		result[summandsCount - 1] = x;

		return result;
	}

	public static String createEquation(final int n, final int type) {

		String result = Integer.toString(n);
		int[] s;
		int x;
		int y;
		int l;

		switch (type) {
		case 1:
			switch (mRnd.nextInt(4)) {
			case 0: // Сумма
				s = decomposeToSummand(n, 2);
				result = Integer.toString(s[0]) + " + "
						+ Integer.toString(s[1]);
				break;
			case 1: // Разность
				x = mRnd.nextInt((int) (n * 0.5f) + 1) + 1;
				result = Integer.toString(n + x) + " - " + Integer.toString(x);
				break;
			case 2: // Произведение
				s = decomposeToMultipliers(n);
				l = s.length / 2;
				x = 1;
				y = 1;
				for (int i = 0; i < s.length; i++)
					if (i < l)
						x *= s[i];
					else
						y *= s[i];
				result = Integer.toString(x) + " * " + Integer.toString(y);
				break;
			case 3: // Частное
				x = mRnd.nextInt((int) (n * 0.2f) + 1) + 1;
				result = Integer.toString(n * x) + " / " + Integer.toString(x);
				break;
			}
			break;
		case 2:			
			s = decomposeToMultipliers(n);
			if (s.length == 1) {
				result = "1 * " + createEquation(s[0], 1);
			} else {
				l = s.length / 2;
				x = 1;
				y = 1;
				for (int i = 0; i < s.length; i++)
					if (i < l)
						x *= s[i];
					else
						y *= s[i];
				if(mRnd.nextBoolean()){
					result = "(" + createEquation(x, 1) + ")";
				} else {
					result = Integer.toString(x);
				}
				if(mRnd.nextBoolean()){
					result += " * (" + createEquation(y, 1) + ")";
				} else {
					result += " * " + Integer.toString(y);
				}					
			}
		}

		return result;
	}
}

