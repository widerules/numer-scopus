package com.kvadratin.numerscopus.fractal;

import java.util.Random;
import java.util.Stack;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.TextureManager;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.SparseArray;

import com.kvadratin.numerscopus.fractal.splitter.FractalSplitterManager;
import com.kvadratin.numerscopus.ornament.OrnamentManager;
import com.kvadratin.numerscopus.utils.BitmapTextureSource;

public class Fractal {

	private FractalPart mFractal;
	private Bitmap mFractalImage;
	private BitmapTextureAtlas mFractalTexture;
	private Sprite mFractalSprite;
	private NumberFractalPart[] mNumbers;

	private FractalSplitterManager mSplitters;
	private OrnamentManager mOrnaments;
	private SparseArray<Font> mFonts;

	private TextureManager mTextures;
	private Scene mScene;

	public Fractal(TextureManager pTextures, Scene pScene,
			FractalSplitterManager pSplitters, OrnamentManager pOrnaments,
			SparseArray<Font> pFonts, final int pSubpartCount) {

		mSplitters = pSplitters;
		mOrnaments = pOrnaments;
		mFonts = pFonts;

		mTextures = pTextures;
		mScene = pScene;

		this.split(pSubpartCount);
	}

	/**
	 * Производит очистку разбиения
	 */
	public void clear() {

		if (mFractal != null) {

			for (int i = 0; i < mNumbers.length; i++) {
				mScene.detachChild(mNumbers[i].getOrnamentSprite());
				mScene.detachChild(mNumbers[i].getNumberText());
			}

			mScene.detachChild(mFractalSprite);
			mTextures.unloadTexture(mFractalTexture);

			mFractal.clear();
			mFractalImage.recycle();

			mNumbers = null;
			mFractal = null;
			mFractalSprite = null;
			mFractalTexture = null;
			mFractalImage = null;
		}

	}

	/**
	 * Производит разбиение области на подобласти. Итерационный алгоритм.
	 * 
	 * @param pSubpartCount
	 *            Требуемое количество подобластей
	 */
	public void split(final int pSubpartCount) {

		this.clear();
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
				int ornamentId = rand.nextInt(mOrnaments.getOrnamentCount());

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
						part.getY() + ((part.getHeight() * 0.5f)) - (txt.getHeightScaled() * 0.5f));
				// TODO: text rotation

				part.init(mOrnaments.getSprite(ornamentId, part.getField()),
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
			mScene.attachChild(mNumbers[i].getOrnamentSprite());
			mScene.attachChild(mNumbers[i].getNumberText());
		}
		
		// Готовим рисунок разбиения и помещаем его на сцену
		mFractalImage = Bitmap.createBitmap((int) mFractal.getWidth(),
				(int) mFractal.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(mFractalImage);
		c.drawARGB(0, 255, 255, 255);
		mFractal.draw(c);

		int textureSize = mFractalImage.getWidth() <= 128
				&& mFractalImage.getHeight() <= 128 ? 128 : mFractalImage
				.getWidth() <= 256
				&& mFractalImage.getHeight() <= 256 ? 256 : mFractalImage
				.getWidth() <= 512
				&& mFractalImage.getHeight() <= 512 ? 512 : mFractalImage
				.getWidth() <= 1024
				&& mFractalImage.getHeight() <= 1024 ? 1024 : mFractalImage
				.getWidth() <= 2048
				&& mFractalImage.getHeight() <= 2048 ? 2048 : 4096;

		mFractalTexture = new BitmapTextureAtlas(textureSize, textureSize,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		TextureRegion tr = BitmapTextureAtlasTextureRegionFactory
				.createFromSource(mFractalTexture, new BitmapTextureSource(
						mFractalImage), 0, 0);
		mTextures.loadTexture(mFractalTexture);

		mFractalSprite = new Sprite(0, 0, tr);
		mScene.attachChild(mFractalSprite);

		
	}
}
