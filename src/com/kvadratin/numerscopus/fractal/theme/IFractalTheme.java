package com.kvadratin.numerscopus.fractal.theme;

import org.anddev.andengine.entity.modifier.IEntityModifier;
import org.anddev.andengine.entity.scene.background.IBackground;

import android.graphics.Paint;

import com.kvadratin.numerscopus.fractal.NumberFractalPart;
import com.kvadratin.numerscopus.fractal.theme.font.IFontManager;
import com.kvadratin.numerscopus.fractal.theme.ornament.IOrnamentManager;

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
	
	/**
	 * Цвет шрифта для последнего числа на которое было произведено нажатие
	 * 
	 * @return
	 */
	int getActiveTextColor();
	

	/**
	 * Цвет шрифта для чисел на которые уже нажимали
	 * 
	 * @return
	 */
	int getDisabledTextColor();
	
	/**
	 * Цвет шрифта для подсветки следующего числа числа
	 * 
	 * @return
	 */
	int getNextTextColor();
	
	/**
	 * Вернет анимацию для орнамента, которая будет воспроизведена при нажатии
	 * 
	 * @return
	 */
	IEntityModifier getOnClickOrnametModifier(NumberFractalPart pPart);
	
	/**
	 * Вернет анимацию для текста, которая будет воспроизведена при нажатии
	 * 
	 * @return
	 */
	IEntityModifier getOnClickTextModifier(NumberFractalPart pPart);
}
