package com.kvadratin.numerscopus.ui;

import org.anddev.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.anddev.andengine.entity.scene.menu.item.TextMenuItem;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.opengl.font.Font;

import com.kvadratin.numerscopus.utils.StringHelper;
import com.kvadratin.numerscopus.utils.ninepatch.NinePatchSprite;
import com.kvadratin.numerscopus.utils.ninepatch.NinePatchTextureRegionBatch;

public class MessageBox extends NinePatchSprite {
	// ===========================================================
	// Fields
	// ===========================================================
	ChangeableText mTitle;
	ChangeableText mMessage;
	
	String mTitleText;
	String mMessageText;
	
	Font mTitleFont;
	Font mMessageFont;
	
	Sprite mIcon;
	TextMenuItem mButton;
	SpriteMenuItem mCloseButton;	

	// ===========================================================
	// Constructors
	// ===========================================================
	public MessageBox(final String pTitle, final String pMessage, Sprite pIcon,
			final TextMenuItem pButton, final SpriteMenuItem pCloseButton,
			final Font pTitleFont, final Font pMessageFont,
			final NinePatchTextureRegionBatch pRegion, final float pX,
			final float pY, final float pWidth, final float pHeight) {
		super(pRegion, pX, pY, pWidth, pHeight);

		mTitleText = pTitle;
		mMessageText = pMessage;
		
		mTitleFont = pTitleFont;
		mMessageFont = pMessageFont;
		
		mTitle = new ChangeableText(0, 0, pTitleFont, pTitle);
		mMessage = new ChangeableText(0, 0, pMessageFont, pMessage);
		
		mIcon = pIcon;
		mButton = pButton;
		mCloseButton = pCloseButton;
		
		this.setHeight(pHeight);
		this.setWidth(pWidth);
		this.setPosition(pX, pY);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public String getTitle() {
		return mTitle.getText();
	}

	public void setTitle(final String pTitle) {
		float width = mTitleFont.getStringWidth(mTitleText);
		float charWidth = width / mTitleText.length();		
		int titleLength = (int)(mPatch[NinePatchTextureRegionBatch.TOP_CENTER].getWidthScaled() / charWidth);
		
		mTitle.setText(StringHelper.cut(mTitleText, titleLength));
	}

	public String getMessage() {
		return mMessage.getText();
	}

	public void setMessage(final String pMessage) {
		mMessage.setText(pMessage);
	}

	@Override
	public void setWidth(float pWidth) {		
		super.setWidth(pWidth);
		
		this.setTitle(mTitleText);
		this.setMessage(mMessageText);
		
		// TODO move close button and icon
	}

	@Override
	public void setHeight(float pHeight) {		
		super.setHeight(pHeight);		
		this.setMessage(mMessageText);
		// TODO move button 
	}

	@Override
	public void setPosition(float pX, float pY) {		
		super.setPosition(pX, pY);
		// TODO move buttons, text and icon
	}
}
