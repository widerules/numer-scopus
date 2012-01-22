package com.kvadratin.numerscopus.utils;

public class TextureHelper {
	public static int calcSize(int lenght) {
		return lenght <= 2 ? 2 :
			lenght <= 4 ? 4 :
				lenght <= 8 ? 8 :
					lenght <= 16 ? 16 :
						lenght <= 32 ? 32 :
							lenght <= 64 ? 64 :
								lenght <= 128 ? 128 : 
									lenght <= 256 ? 256 : 
										lenght <= 512 ? 512	: 
											lenght <= 1024 ? 1024 :
												lenght <= 2048 ? 2048 : 
													lenght <= 4096 ? 4096 : 8192; 
	}
}
