package com.kvadratin.numerscopus.fractal.theme;

import org.anddev.andengine.entity.scene.background.IBackground;

import android.graphics.Paint;

import com.kvadratin.numerscopus.font.IFontManager;
import com.kvadratin.numerscopus.ornament.IOrnamentManager;

/**
 * Тема для разбиений
 * 
 * @author bargatin
 * @since 2012-01-22
 */
public interface IFractalTheme {
	/**
	 * Возврщает фон для сцены
	 * 
	 * @return
	 */
	IBackground getBackground();

	/**
	 * Возвращает менеджер орнаментов
	 * 
	 * @return
	 */
	IOrnamentManager getOrnamentManager();

	/**
	 * Возвращает менеджер шрифтов
	 * 
	 * @return
	 */
	IFontManager getFontManager();

	/**
	 * Рисовать ли рамку разбиения?
	 * 
	 * @return
	 */
	boolean isBorderVisible();

	/**
	 * Стиль рисования рамки
	 * 
	 * @return
	 */
	Paint getBorderPaint();

	/**
	 * Цвет шрифта для чисел
	 * 
	 * @return
	 */
	int getTextColor();	
	float getTextColorAlpha();
	float getTextColorRed();
	float getTextColorGreen();
	float getTextColorBlue();

	/**
	 * Цвет шрифта для последнего числа на которое было произведено нажатие
	 * 
	 * @return
	 */
	int getActiveTextColor();
	float getActiveTextColorAlpha();
	float getActiveTextColorRed();
	float getActiveTextColorGreen();
	float getActiveTextColorBlue();

	/**
	 * Цвет шрифта для чисел на которые уже нажимали
	 * 
	 * @return
	 */
	int getDisabledTextColor();
	float getDisabledTextColorAlpha();
	float getDisabledTextColorRed();
	float getDisabledTextColorGreen();
	float getDisabledTextColorBlue();


	/**
	 * Цвет шрифта для подсветки следующего числа числа
	 * 
	 * @return
	 */
	int getNextTextColor();
	float getNextTextColorAlpha();
	float getNextTextColorRed();
	float getNextTextColorGreen();
	float getNextTextColorBlue();
}
