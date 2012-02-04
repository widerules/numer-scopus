package com.kvadratin.numerscopus.fractal.event;

public interface IFractalClickListener {
	void onClick(FractalTouchEvent e);
	boolean isCanceled(FractalTouchEvent e);
}
