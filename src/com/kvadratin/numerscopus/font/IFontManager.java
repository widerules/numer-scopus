package com.kvadratin.numerscopus.font;

import org.anddev.andengine.opengl.font.Font;

public interface IFontManager {
	/**
	 * Производит загрузку шрифтов	 
	 */
	void load();

	/**
	 * Очищает менеджер от ранее загруженных шрифтов
	 */
	void clear();

	/**
	 * Возвращает количество загруженных шрифтов
	 * 
	 * @return
	 */
	int size();

	/**
	 * Возвращает шрифт по идентификатору
	 * 
	 * @param pFontId
	 *            Идентификатор шрифта
	 */
	Font get(final int pFontId);

	/**
	 * Возвращает цвет шрифта
	 * 
	 * @return Цвет в формате ARGB
	 */
	int getFontColor();

	/**
	 * Возвращает цвет обводки
	 * 
	 * @return Цвет в формате ARGB
	 */
	int getFontStrokeColor();

	/**
	 * Возвращает размер шрифта
	 * 
	 * @return
	 */
	int getFontSize();

	/**
	 * Возвращает толщину обводки
	 * 
	 * @return
	 */
	float getStrokeWidth();
}
