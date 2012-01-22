package com.kvadratin.numerscopus.fractal.theme;

import com.kvadratin.numerscopus.font.IFontManager;
import com.kvadratin.numerscopus.ornament.IOrnamentManager;

public class FractalThemeFactory {
	public static BaseFractalTheme createBaseFractalTheme(IFontManager pFontManager, IOrnamentManager pOrnamentManager){
		return new BaseFractalTheme(pFontManager, pOrnamentManager);
	}
	
	public static ClearGreyFractalTheme createClearGreyFractalTheme(IFontManager pFontManager){
		return new ClearGreyFractalTheme(pFontManager);
	}
}
