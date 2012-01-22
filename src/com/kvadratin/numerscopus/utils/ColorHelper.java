package com.kvadratin.numerscopus.utils;

import android.graphics.Color;

public class ColorHelper {
	
	public static float alpha(int color){
		return Color.alpha(color) / 255.0f;
	}
	
	public static float red(int color){
		return Color.red(color) / 255.0f;
	}
	
	public static float green(int color){
		return Color.green(color) / 255.0f;
	}
	
	public static float blue(int color){
		return Color.blue(color) / 255.0f;
	}
}
