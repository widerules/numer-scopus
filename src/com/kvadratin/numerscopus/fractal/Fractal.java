package com.kvadratin.numerscopus.fractal;

import java.util.Random;
import java.util.Stack;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;

import com.kvadratin.numerscopus.fractal.splitter.FractalSplitterManager;
import com.kvadratin.numerscopus.fractal.theme.IFractalTheme;
import com.kvadratin.numerscopus.fractal.theme.font.IFontManager;
import com.kvadratin.numerscopus.fractal.theme.ornament.IOrnamentManager;
import com.kvadratin.numerscopus.utils.BitmapTextureSource;
import com.kvadratin.numerscopus.utils.TextureHelper;

public class Fractal {
	
	private FractalPart mFractal;
	private Bitmap mFractalImage;
	private BitmapTextureAtlas mFractalTexture;
	private Sprite mFractalSprite;
	private NumberFractalPart[] mNumbers;
	private float mFractalPadding;

	private FractalSplitterManager mSplitters;
	private IFractalTheme mFractalTheme;
	
	protected DisplayMetrics mMetrics;
	protected Engine mEngine;	
	protected Scene mScene;
	
	private float mBeginTouchPositionX;
	private float mBeginTouchPositionY;

	public Fractal(DisplayMetrics pMetrics, Engine pEngine, IFractalTheme pFractalTheme,
			final int pSubpartCount) {
		
		mMetrics = pMetrics;
		mSplitters = new FractalSplitterManager(mMetrics);
		mFractalTheme = pFractalTheme;
		mEngine = pEngine;
		mFractalPadding = 10;
		
		// Создаем сцену для фрактала
		mScene = new Scene() {

			@Override
			public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
				super.onSceneTouchEvent(pSceneTouchEvent);

				if (pSceneTouchEvent.isActionDown()) {
					mBeginTouchPositionX = pSceneTouchEvent.getX();
					mBeginTouchPositionY = pSceneTouchEvent.getY();
				}

				if (pSceneTouchEvent.isActionMove()) {
					
					Camera camera = mEngine.getCamera();
					float x = camera.getCenterX();
					float y = camera.getCenterY();
					float dx = mBeginTouchPositionX - pSceneTouchEvent.getX();
					float dy = mBeginTouchPositionY - pSceneTouchEvent.getY();

					float left = mFractal.getX() + camera.getWidth() * 0.5f
							- mFractalPadding;
					float right = mFractal.getWidth() - camera.getWidth()
							* 0.5f + mFractalPadding;
					
					float top = mFractal.getY() + camera.getHeight() * 0.5f - mFractalPadding;
					float bottom = mFractal.getHeight()	- camera.getHeight() * 0.5f + mFractalPadding;
					
					if (x + dx < left)
						x = left;
					else if (x + dx > right)
						x = right;
					else
						x += dx;

					if (y + dy < top)
						y = top;
					else if (y + dy > bottom)
						y = bottom;
					else
						y += dy;

					camera.setCenter(x, y);
				}
				
				return true;
			}
		};
		
		mScene.setBackground(mFractalTheme.getBackground());
		
		this.split(pSubpartCount);
	}

	/**
	 * Производит очистку разбиения
	 */
	public void clear() {

		if (mNumbers != null)
			for (int i = 0; i < mNumbers.length; i++) {
				if (mNumbers[i] != null) {
					IEntity ornament = mNumbers[i].getOrnamentEntity();
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
			mEngine.getTextureManager().unloadTexture(mFractalTexture);

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
		
		IOrnamentManager ornaments = mFractalTheme.getOrnamentManager();
		IFontManager fonts = mFractalTheme.getFontManager();
		
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
				int fontId = rand.nextInt(fonts.size());				
				int ornamentId = ornaments != null ? rand.nextInt(ornaments.getOrnamentCount()) : -1;

				if (mNumbers[num] != null) {
					for (int i = 0; i < mNumbers.length; i++) {
						if (mNumbers[i] == null) {
							num = i;
							break;
						}
					}
				}
				
				// Создаем текст числа
				Text txt = new Text(0, 0, fonts.get(fontId), Integer
						.toString(num + 1));
				
				txt.setIgnoreUpdate(true);
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
				
				IEntity entity = ornaments != null ? ornaments.getEntity(ornamentId, part.getField(),
						(byte) rand.nextInt(ornaments.getFillMethodCount())) : null;
				
				if(entity != null)
					entity.setIgnoreUpdate(true);
				
				part.init(entity, txt, num + 1, fontId, ornamentId);

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
			if (mNumbers[i].getOrnamentEntity() != null)
				mScene.attachChild(mNumbers[i].getOrnamentEntity());
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
			mEngine.getTextureManager().loadTexture(mFractalTexture);

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
	
	public float getPadding(){
		return mFractalPadding;
	}
	
	public Scene getScene(){
		return mScene;
	}	
}
