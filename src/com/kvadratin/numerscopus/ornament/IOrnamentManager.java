package com.kvadratin.numerscopus.ornament;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.graphics.RectF;

public interface IOrnamentManager {
	/**
	 * Количество орнаментов загруженных в менеджер
	 */
	int getOrnamentCount();
	
	/**
	 * Возвращает текстуру орнамента
	 * 
	 * @param pOrnamentId
	 *            Идентификатор орнамента
	 */
	BitmapTextureAtlas getTexture(final int pOrnamentId);
	
	/**
	 * Возвращает регион текстуры орнамента
	 * 
	 * @param pOrnamentId
	 *            Идентификатор орнамента
	 */
	TextureRegion getTextureRegion(final int pOrnamentId);
	
	/**
	 * Возвращает спрайт орнамента, подогнанный по размеру под pField
	 * 
	 * @param pOrnamentId
	 *            Идентификатор орнамента
	 * @param pField
	 *            Поле под размеры которого будет подгонятся спрайт орнамента
	 * @param pFillMethod
	 *            Метод заполнения спрайта текстурой. pFillMethod <
	 *            FILL_METHODS_COUNT
	 */
	Sprite getSprite(final int pOrnamentId, RectF pField, byte pFillMethod);
	
	/**
	 * Производит удаление всех текстур
	 */
	void clear();
	
	/**
	 * Возвращает количество методов заполнения спрайта.
	 */
	byte getFillMethodCount();
}
