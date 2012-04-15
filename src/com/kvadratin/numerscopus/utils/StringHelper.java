package com.kvadratin.numerscopus.utils;

public class StringHelper {

	/**
	 * Производит конвертирование исходной строки в многострочный блок текста,
	 * где каждая строка не длиннее lineWidth
	 * 
	 * @param lineWidth
	 *            Максимальная длина строки в многострочном блоке
	 * @return Многострочный блок текста (Строка разделенная символами перевода
	 *         корретки)
	 */
	public final static String splitToMultiline(final String str,
			final int lineWidth) {

		String[] words = str.split("[ \t\n\f\r]");
		StringBuilder sb = new StringBuilder();
		int len = 0;

		for (String s : words) {
			len += s.length();
			if (len > lineWidth)
				sb.append("\n" + s);
			else
				sb.append(s);
		}

		return sb.toString();
	}
	
	/**
	 * Урезает исходную строку до lineWidth - 3 символов и добавляет в результат многоточие (...)
	 * @param str Исходная строка
	 * @param lineWidth Ширина строки
	 * @return
	 */
	public final static String cut(final String str,
			final int lineWidth){
		return str.length() > lineWidth ? str.substring(0, lineWidth - 3) + "..." : str;
	}
}
