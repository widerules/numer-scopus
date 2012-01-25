package com.kvadratin.numerscopus.fractal.theme.ornament;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.graphics.RectF;

public class ColorRectOrnamentManager implements IOrnamentManager {

	@Override
	public void clear() {
		
	}

	@Override
	public IEntity getEntity(int pOrnamentId, RectF pField, byte pFillMethod) {
		
		Rectangle entity = new Rectangle(pField.left, pField.top, pField.width(), pField.height());
		
		switch(pOrnamentId){
		case 0:
			// rgb(255,174,165)
			entity.setColor(1f, 0.68f, 0.64f);
			break;		
		case 1:
			// rgb(255,166,115)
			entity.setColor(1f, 0.65f, 0.45f);
			break;
		case 2:
			// rgb(245,226,121)
			entity.setColor(0.96f, 0.88f, 0.47f);
			break;
		case 3:
			// rgb(249,247,101)
			entity.setColor(0.97f, 0.96f, 0.39f);
			break;
		case 4:
			// rgb(223,251,87)
			entity.setColor(0.87f, 0.98f, 0.34f);
			break;
		case 5:
			// rgb(178,243,71)
			entity.setColor(0.69f, 0.95f, 0.27f);
			break;
		case 6:
			// rgb(96,247,98)
			entity.setColor(0.37f, 0.96f, 0.38f);
			break;
		case 7:
			// rgb(110,250,180)
			entity.setColor(0.43f, 0.98f, 0.7f);
			break;
		case 8:
			// rgb(131,251,251)
			entity.setColor(0.51f, 0.98f, 0.98f);
			break;
		case 9:
			// rgb(175,191,248)
			entity.setColor(0.68f, 0.74f, 0.97f);
			break;
		case 10:
			// rgb(208,128,244)
			entity.setColor(0.81f, 0.5f, 0.95f);
			break;
		case 11:
			// rgb(240,157,249)
			entity.setColor(0.94f, 0.61f, 0.97f);
			break;
		case 12:
			// rgb(251,129,199)
			entity.setColor(0.98f, 0.5f, 0.78f);
			break;
		case 13:
			// rgb(252,145,170)
			entity.setColor(0.98f, 0.56f, 0.66f);
			break;
		case 14:
			// rgb(253,148,148)
			entity.setColor(0.99f, 0.58f, 0.58f);
			break;		
		}
		
		return entity;
	}

	@Override
	public byte getFillMethodCount() {
		return 1;
	}

	@Override
	public int getOrnamentCount() {
		return 15;
	}

	@Override
	public BitmapTextureAtlas getTexture(int pOrnamentId) {
		return null;
	}

	@Override
	public TextureRegion getTextureRegion(int pOrnamentId) {
		return null;
	}

}
