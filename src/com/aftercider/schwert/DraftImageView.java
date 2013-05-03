package com.aftercider.schwert;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import com.matabii.dev.scaleimageview.ScaleImageView;

public class DraftImageView extends ScaleImageView {
	public DraftImageView(Context context) {
		super(context);
	}
	
	public DraftImageView(Context context, AttributeSet attr) {
		super(context, attr);
	}

	protected boolean isSetImage = true;
	
	@Override
	public void setImageBitmap(Bitmap bm){
		isSetImage = true;
		super.setImageBitmap(bm);
	}
	
	@Override
	protected boolean setFrame(int l, int t, int r, int b) {
		if(isSetImage){
			isSetImage = false;
			return super.setFrame(l, t, r, b);
		}else{
			return true;
		}
	}
}
