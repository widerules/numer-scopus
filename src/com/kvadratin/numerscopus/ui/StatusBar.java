package com.kvadratin.numerscopus.ui;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.IEntityModifier;
import org.anddev.andengine.entity.modifier.LoopEntityModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.modifier.ColorModifier;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.opengl.font.Font;

import com.kvadratin.numerscopus.utils.ColorHelper;

public class StatusBar {
	private Rectangle mBackground;
	private Rectangle mLightLine;
	private Rectangle mShadowLine;

	private int mBcgColor;
	private int mTextColor;
	private int mAttentionTextColor;
	
	private ChangeableText mText;
	private ChangeableText mTime;
	private ChangeableText mScore;

	private Sprite mTimerIcon;
	private Sprite mScoreIcon;

	public StatusBar(float pWidth, float pHeight, Sprite pTimerIcon,
			Sprite pScoreIcon, int pBcgColor, int pTextColor,
			int pAttentionTextColor, Font pTextFont, Font pScoreFont) {

		mBcgColor = pBcgColor;
		mTextColor = pTextColor;
		mAttentionTextColor = pAttentionTextColor;
		
		float textAlpha = ColorHelper.alpha(mTextColor);
		float textRed = ColorHelper.red(mTextColor);
		float textGreen = ColorHelper.green(mTextColor);
		float textBlue = ColorHelper.blue(mTextColor);
		
		mBackground = new Rectangle(0, 0, pWidth, pHeight);
		mBackground.setAlpha(ColorHelper.alpha(mBcgColor));
		mBackground.setColor(ColorHelper.red(mBcgColor), ColorHelper
				.green(mBcgColor), ColorHelper.blue(mBcgColor));

		mShadowLine = new Rectangle(0, pHeight, pWidth, pHeight * 0.14f);
		mShadowLine.setAlpha(0.3f);
		mShadowLine.setColor(ColorHelper.red(249), ColorHelper.green(255),
				ColorHelper.blue(134));

		mLightLine = new Rectangle(0, pHeight - pHeight * 0.4f, pWidth,
				pHeight * 0.4f);
		mLightLine.setAlpha(0.58f);
		mLightLine.setColor(1, 1, 1);

		mBackground.attachChild(mShadowLine);
		mBackground.attachChild(mLightLine);

		mScoreIcon = pScoreIcon;
		mTimerIcon = pTimerIcon;

		float iconSize = mLightLine.getHeightScaled() * 0.55f;
		mScoreIcon.setScaleCenter(0, 0);
		mScoreIcon.setScale(iconSize / mScoreIcon.getHeightScaled());

		mTimerIcon.setScaleCenter(0, 0);
		mTimerIcon.setScale(iconSize / mTimerIcon.getHeightScaled());

		mScoreIcon.setPosition(mLightLine.getWidthScaled() * 0.03f, mLightLine
				.getY()
				+ (mLightLine.getHeightScaled() - mScoreIcon.getHeightScaled())
				* 0.5f);
		mTimerIcon.setPosition(mLightLine.getWidthScaled()
				- mLightLine.getWidthScaled() * 0.18f, mLightLine.getY()
				+ (mLightLine.getHeightScaled() - mTimerIcon.getHeightScaled())
				* 0.5f);

		mScoreIcon.setAlpha(textAlpha);
		mScoreIcon.setColor(textRed, textGreen, textBlue);
		mTimerIcon.setAlpha(textAlpha);
		mTimerIcon.setColor(textRed, textGreen, textBlue);

		mBackground.attachChild(mScoreIcon);
		mBackground.attachChild(mTimerIcon);

		// TODO: 0123456789()-+*/?
		mText = new ChangeableText(0, 0, pTextFont, "(12 + 2) * (21 / 3) = ?");
		mText.setAlpha(textAlpha);
		mText.setColor(textRed, textGreen, textBlue);
		mText.setScaleCenter(0, 0);
		mText.setScale(((mBackground.getHeightScaled() - mLightLine
				.getHeightScaled()) * 0.73f)
				/ mText.getHeightScaled());

		float x = mBackground.getWidthScaled() - mText.getWidthScaled();
		mText.setPosition((x < 0 ? 0 : x) * 0.5f, ((mBackground
				.getHeightScaled() - mLightLine.getHeightScaled()) - mText
				.getHeightScaled()) * 0.5f);

		mBackground.attachChild(mText);

		// TODO: 0123456789
		mScore = new ChangeableText(0, 0, pScoreFont, "213");
		mScore.setAlpha(textAlpha);
		mScore.setColor(textRed, textGreen, textBlue);
		mScore.setScaleCenter(0, 0);
		mScore
				.setScale(mLightLine.getHeightScaled()
						/ mScore.getHeightScaled());
		mScore.setPosition(mScoreIcon.getX() + mScoreIcon.getWidthScaled()
				+ mLightLine.getWidthScaled() * 0.02f, mLightLine.getY()
				- (mScoreIcon.getY() - mLightLine.getY()) * 0.5f);

		mBackground.attachChild(mScore);

		// TODO: 0123456789:
		mTime = new ChangeableText(0, 0, pScoreFont, "0:12");
		mTime.setAlpha(textAlpha);
		mTime.setColor(textRed, textGreen, textBlue);
		mTime.setScaleCenter(0, 0);
		mTime.setScale(mLightLine.getHeightScaled() / mTime.getHeightScaled());
		mTime.setPosition(mTimerIcon.getX() + mTimerIcon.getWidthScaled()
				+ mLightLine.getWidthScaled() * 0.02f, mLightLine.getY()
				- (mTimerIcon.getY() - mLightLine.getY()) * 0.5f);

		mBackground.attachChild(mTime);
	}

	public void setText(final String pText) {
		mText.setText(pText);
		mText.setScaleCenter(0, 0);
		float x = mBackground.getWidthScaled() - mText.getWidthScaled();
		mText.setPosition((x < 0 ? 0 : x) * 0.5f, ((mBackground
				.getHeightScaled() - mLightLine.getHeightScaled()) - mText
				.getHeightScaled()) * 0.5f);
	}

	public void setScore(final String pScore) {
		mScore.setText(pScore);
		mScore.setScaleCenter(0, 0);
		mScore.setPosition(mScoreIcon.getX() + mScoreIcon.getWidthScaled()
				+ mLightLine.getWidthScaled() * 0.02f, mLightLine.getY()
				- (mScoreIcon.getY() - mLightLine.getY()) * 0.5f);
	}

	public void setTime(final String pTime) {
		mTime.setText(pTime);
		mTime.setScaleCenter(0, 0);
		mTime.setPosition(mTimerIcon.getX() + mTimerIcon.getWidthScaled()
				+ mLightLine.getWidthScaled() * 0.02f, mLightLine.getY()
				- (mTimerIcon.getY() - mLightLine.getY()) * 0.5f);
	}

	public void setPosition(final float x, final float y) {
		mBackground.setPosition(x, y);
	}

	public void attachTo(IEntity to) {
		to.attachChild(mBackground);
	}

	public void detachSelf() {
		mBackground.detachSelf();
	}

	public float getHeight() {
		return mBackground.getHeightScaled() + mShadowLine.getHeightScaled();
	}

	public void setColor(final int color) {
		mBcgColor = color;
		mBackground.setAlpha(ColorHelper.alpha(mBcgColor));
		mBackground.setColor(ColorHelper.red(mBcgColor), ColorHelper
				.green(mBcgColor), ColorHelper.blue(mBcgColor));
	}

	public void setTextColor(final int color) {
		mTextColor = color;

		float a = ColorHelper.alpha(mTextColor);
		float r = ColorHelper.red(mTextColor);
		float g = ColorHelper.green(mTextColor);
		float b = ColorHelper.blue(mTextColor);

		mText.setAlpha(a);
		mText.setColor(r, g, b);

		mScore.setAlpha(a);
		mScore.setColor(r, g, b);
		
		mTime.setAlpha(a);
		mTime.setColor(r, g, b);
		
		mScoreIcon.setAlpha(a);
		mScoreIcon.setColor(r, g, b);
		
		mTimerIcon.setAlpha(a);
		mTimerIcon.setColor(r, g, b);
	}

	/**
	 * Сигнализирует о событии мигая цветом
	 * 
	 * @param what
	 *            Поле (0 - Текст; 1 - Счет; 2 - Время)
	 */
	public void showAttention(final int what) {

		float fromRed = ColorHelper.red(mTextColor);
		float fromGreen = ColorHelper.green(mTextColor);
		float fromBlue = ColorHelper.blue(mTextColor);

		float toRed = ColorHelper.red(mAttentionTextColor);
		float toGreen = ColorHelper.green(mAttentionTextColor);
		float toBlue = ColorHelper.blue(mAttentionTextColor);

		IEntityModifier iconMod;
		IEntityModifier modifier = new LoopEntityModifier(
				new SequenceEntityModifier(new ColorModifier(0.8f, fromRed,
						toRed, fromGreen, toGreen, fromBlue, toBlue),
						new ColorModifier(0.8f, toRed, fromRed, toGreen,
								fromGreen, toBlue, fromBlue)), 3);

		switch (what) {
		case 0:
			mText.registerEntityModifier(modifier);
			break;
		case 1:
			mScore.registerEntityModifier(modifier);
			iconMod = new LoopEntityModifier(new SequenceEntityModifier(
					new ColorModifier(0.8f, fromRed, toRed, fromGreen, toGreen,
							fromBlue, toBlue), new ColorModifier(0.8f, toRed,
							fromRed, toGreen, fromGreen, toBlue, fromBlue)), 3);
			mTimerIcon.registerEntityModifier(iconMod);
			break;
		case 2:
			mTime.registerEntityModifier(modifier);
			iconMod = new LoopEntityModifier(new SequenceEntityModifier(
					new ColorModifier(0.8f, fromRed, toRed, fromGreen, toGreen,
							fromBlue, toBlue), new ColorModifier(0.8f, toRed,
							fromRed, toGreen, fromGreen, toBlue, fromBlue)), 3);
			mTimerIcon.registerEntityModifier(iconMod);
			break;
		}
	}
	
	public void hide(){
		mBackground.setVisible(false);
		mBackground.setIgnoreUpdate(true);
	}
	
	public void show(){
		mBackground.setVisible(true);
		mBackground.setIgnoreUpdate(false);
	}
	
	public boolean isVisible(){
		return mBackground.isVisible();
	}
}
