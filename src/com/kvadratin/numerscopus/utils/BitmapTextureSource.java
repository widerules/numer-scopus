package com.kvadratin.numerscopus.utils;

import org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

public class BitmapTextureSource implements IBitmapTextureAtlasSource {

    private Bitmap mBitmap = null;
    protected int mTexturePositionX;
	protected int mTexturePositionY;
	
	public BitmapTextureSource(Bitmap pBitmap, int pTexturePositionX, int pTexturePositionY) {
        mBitmap = pBitmap;
        mTexturePositionX = pTexturePositionX;
        mTexturePositionY = pTexturePositionY;
    }
	
    public BitmapTextureSource(Bitmap pBitmap) {
    	mBitmap = pBitmap;
        mTexturePositionX = 0;
        mTexturePositionY = 0;
    }

   
    public int getWidth() {
        return mBitmap.getWidth();
    }

    
    public int getHeight() {
        return mBitmap.getHeight();
    }

   
    public Bitmap onLoadBitmap() {
        return mBitmap.copy(mBitmap.getConfig(), false);
    }

	@Override
	public IBitmapTextureAtlasSource deepCopy() {
		return new BitmapTextureSource(mBitmap, mTexturePositionX, mTexturePositionY);
	}


	@Override
	public Bitmap onLoadBitmap(Config pConfig) {
		return mBitmap.copy(pConfig, false);
	}


	@Override
	public int getTexturePositionX() {
		return mTexturePositionX;
	}


	@Override
	public int getTexturePositionY() {
		return mTexturePositionY;
	}


	@Override
	public void setTexturePositionX(int pTexturePositionX) {
		mTexturePositionX = pTexturePositionX;		
	}


	@Override
	public void setTexturePositionY(int pTexturePositionY) {
		mTexturePositionY = pTexturePositionY;
	}

}