package com.kvadratin.numerscopus.fractal;

import java.util.Random;
import java.util.Stack;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.opengl.texture.TextureManager;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.kvadratin.numerscopus.font.IFontManager;
import com.kvadratin.numerscopus.fractal.splitter.FractalSplitterManager;
import com.kvadratin.numerscopus.fractal.theme.IFractalTheme;
import com.kvadratin.numerscopus.ornament.IOrnamentManager;
import com.kvadratin.numerscopus.utils.BitmapTextureSource;
import com.kvadratin.numerscopus.utils.TextureHelper;

public class Fractal {

	private FractalPart mFractal;
	private Bitmap mFractalImage;
	private BitmapTextureAtlas mFractalTexture;
	private Sprite mFractalSprite;
	private NumberFractalPart[] mNumbers;

	private FractalSplitterManager mSplitters;
	private IFractalTheme mFractalTheme;
	private IOrnamentManager mOrnaments;
	private IFontManager mFonts;

	private TextureManager mTextures;
	private Scene mScene;

	public Fractal(TextureManager pTextures, Scene pScene,
			FractalSplitterManager pSplitters, IFractalTheme pFractalTheme,
			final int pSubpartCount) {

		mSplitters = pSplitters;
		mFractalTheme = pFractalTheme;

		mOrnaments = mFractalTheme.getOrnamentManager();
		mFonts = mFractalTheme.getFontManager();

		mTextures = pTextures;
		mScene = pScene;

		this.split(pSubpartCount);
	}

	/**
	 * Производит очистку разбиения
	 */
	public void clear() {

		if (mNumbers != null)
			for (int i = 0; i < mNumbers.length; i++) {
				if (mNumbers[i] != null) {
					Sprite ornament = mNumbers[i].getOrnamentSprite();
					if (ornament != null)
						mScene.detachChild(ornament);

					Text number = mNumbers[i].getNumberText();
					if (number != null)
						mScene.detachChild(number);
				}
			}

		if (mFractalSprite != null)
			mScene.detachChild(mFractalSprite);
		if (mFractalTexture != null)
			mTextures.unloadTexture(mFractalTexture);

		if (mFractal != null)
			mFractal.clear();
		if (mFractalImage != null)
			mFractalImage.recycle();

		mNumbers = null;
		mFractal = null;
		mFractalSprite = null;
		mFractalTexture = null;
		mFractalImage = null;

	}

	/**
	 * Производит разбиение области на подобласти. Итерационный алгоритм.
	 * 
	 * @param pSubpartCount
	 *            Требуемое количество подобластей
	 */
	public void split(int pSubpartCount) {

		this.clear();
		pSubpartCount = pSubpartCount > 0 ? pSubpartCount : 1;
		mNumbers = new NumberFractalPart[pSubpartCount];
		mFractal = mSplitters.getFractalPart(pSubpartCount);

		FractalPart currentPart = mFractal;
		FractalPart[] currentChildren;
		int currentSubpartCount = pSubpartCount;

		Stack<FractalPart> parts = new Stack<FractalPart>();
		Stack<Integer> subpartCounts = new Stack<Integer>();

		Random rand = new Random(System.nanoTime());
		boolean needNextSplit;

		do {
			if (currentSubpartCount <= 1) {
				// Получаем терминальный элемент
				currentPart.split(mSplitters
						.getSplitter(FractalSplitterManager.EMPTY_SPLITTER));
				currentChildren = currentPart.getChildren();

				NumberFractalPart part = (NumberFractalPart) currentChildren[0];
				int num = rand.nextInt(pSubpartCount);
				int fontId = rand.nextInt(mFonts.size());				
				int ornamentId = mOrnaments != null ? rand.nextInt(mOrnaments.getOrnamentCount()) : -1;

				if (mNumbers[num] != null) {
					for (int i = 0; i < mNumbers.length; i++) {
						if (mNumbers[i] == null) {
							num = i;
							break;
						}
					}
				}

				Text txt = new Text(0, 0, mFonts.get(fontId), Integer
						.toString(num + 1));
				txt.setColor(mFractalTheme.getTextColorRed(), mFractalTheme
						.getTextColorGreen(), mFractalTheme.getTextColorBlue(),
						mFractalTheme.getTextColorAlpha());

				txt.setScaleCenter(0, 0);
				txt.setScale(Math.min(part.getWidth()
						/ (txt.getWidth() + 10 * txt.getText().length()), part
						.getHeight()
						/ txt.getHeight()));

				txt.setPosition(
						part.getX()
								+ ((part.getWidth() * 0.5f) - (txt
										.getWidthScaled() + (10 * txt.getText()
										.length() * txt.getScaleX())) * 0.5f),
						part.getY() + ((part.getHeight() * 0.5f))
								- (txt.getHeightScaled() * 0.5f));
				// TODO: text rotation

				part.init(mOrnaments != null ? mOrnaments.getSprite(ornamentId, part.getField(),
						(byte) rand.nextInt(mOrnaments.getFillMethodCount())) : null,
						txt, num + 1, fontId, ornamentId);

				mNumbers[num] = part;
				needNextSplit = false;

			} else {
				// Разбиваем текущую область
				if (currentPart.getWidth() == currentPart.getHeight()) {
					currentPart.split(mSplitters.getRandomSplitter());
				} else if (currentPart.getWidth() > currentPart.getHeight()) {
					currentPart.split(mSplitters.getRandomSplitter(false));
				} else {
					currentPart.split(mSplitters.getRandomSplitter(true));
				}

				// Обрабатываем подобласти текущего разбиения
				currentChildren = currentPart.getChildren();
				needNextSplit = false;

				if (currentChildren != null) {
					if (currentChildren.length > 0
							&& currentChildren[0] != null) {

						float subpartFillRatio[] = new float[currentChildren.length];
						int counts[] = new int[currentChildren.length];
						int countSum = 0;

						// Распределяем дальнейшие разбиения
						for (int i = 0; i < currentChildren.length; i++) {
							if (currentChildren[i] != null) {
								subpartFillRatio[i] = currentChildren[i]
										.getFieldArea()
										/ currentPart.getFieldArea();
								counts[i] = Math.round(currentSubpartCount
										* subpartFillRatio[i]);
								countSum += counts[i];
							}
						}
						counts[0] -= countSum - currentSubpartCount;

						// Помещаем в стек необработанные подобласти
						for (int i = currentChildren.length - 1; i > 0; i--) {
							if (currentChildren != null) {
								parts.push(currentChildren[i]);
								subpartCounts.push(counts[i]);
							}
						}

						// Первая подобласть становится текущей областью для
						// разбиения
						currentPart = currentChildren[0];
						currentSubpartCount = counts[0];

						needNextSplit = true;
					}
				}
			}

			if (needNextSplit == false) {
				if (!parts.empty()) {
					currentPart = parts.pop();
					currentSubpartCount = subpartCounts.pop();

					needNextSplit = true;
				}
			}
		} while (needNextSplit);

		// Помещаем числа на сцену
		for (int i = 0; i < mNumbers.length; i++) {
			if (mNumbers[i].getOrnamentSprite() != null)
				mScene.attachChild(mNumbers[i].getOrnamentSprite());
			mScene.attachChild(mNumbers[i].getNumberText());
		}

		if (mFractalTheme.isBorderVisible()) {
			// Готовим рисунок разбиения и помещаем его на сцену
			mFractalImage = Bitmap.createBitmap((int) mFractal.getWidth(),
					(int) mFractal.getHeight(), Bitmap.Config.ARGB_8888);
			
			Canvas c = new Canvas(mFractalImage);
			c.drawARGB(0, 255, 255, 255);
			
			Paint paint = mFractalTheme.getBorderPaint();
			float delta = paint.getStrokeWidth() * 0.5f;
			
			// Рисуем рамку фрактала
			c.drawLine(mFractal.getX(), mFractal.getY() + delta, mFractal.getWidth(), mFractal.getY() + delta, paint);
			c.drawLine(mFractal.getWidth() - delta, mFractal.getY(), mFractal.getWidth() - delta, mFractal.getHeight(), paint);
			c.drawLine(mFractal.getWidth(), mFractal.getHeight() - delta, mFractal.getX(), mFractal.getHeight() - delta, paint);
			c.drawLine(mFractal.getX() + delta, mFractal.getHeight(), mFractal.getX() + delta, mFractal.getY(), paint);
			
			mFractal.draw(c, mFractalTheme.getBorderPaint());

			mFractalTexture = new BitmapTextureAtlas(TextureHelper
					.calcSize(mFractalImage.getWidth()), TextureHelper
					.calcSize(mFractalImage.getHeight()),
					TextureOptions.BILINEAR_PREMULTIPLYALPHA);

			TextureRegion tr = BitmapTextureAtlasTextureRegionFactory
					.createFromSource(mFractalTexture, new BitmapTextureSource(
							mFractalImage), 0, 0);
			mTextures.loadTexture(mFractalTexture);

			mFractalSprite = new Sprite(0, 0, tr);
			mScene.attachChild(mFractalSprite);
		}
	}
	
	public float getWidth(){
		return mFractal.getWidth();
	}
	
	public float getHeight(){
		return mFractal.getHeight();
	}
	
	public float getX(){
		return mFractal.getX();
	}
	
	public float getY(){
		return mFractal.getY();
	}
}
