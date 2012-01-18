package com.kvadratin.numerscopus.fractal.theme;

import org.anddev.andengine.entity.scene.background.IBackground;

import android.graphics.Color;

import com.kvadratin.numerscopus.font.IFontManager;
import com.kvadratin.numerscopus.ornament.IOrnamentManager;

public interface IFractalTheme {
	IBackground getBackground();
	IOrnamentManager getOrnamentManager();
	IFontManager getFontManager();
	
	Color getBorderColor();
	Color getTextColor();
	Color getActiveTextColor();
	Color getDisabledTextColor();	
}
