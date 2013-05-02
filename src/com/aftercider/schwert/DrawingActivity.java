package com.aftercider.schwert;

import java.util.MissingResourceException;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.app.Activity;
import android.content.Intent;

public class DrawingActivity extends Activity implements AnimationListener {

	private boolean mIsShownSettings = false;
	private LinearLayout mSettingLayout = null;
	
	private Button mButtonToImporting = null;
	private ImageButton mImageButtonStartSetting = null;
	private Button mButtonFinishSetting = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawing);
		
		createWidgets();
		mSettingLayout.setVisibility(LinearLayout.INVISIBLE);
	}
	
	private void createWidgets(){
		mButtonToImporting = (Button)findViewById(R.id.buttonDrawingToImporting);
		mImageButtonStartSetting = (ImageButton)findViewById(R.id.imageButtonDrawingStartSetting);
		mButtonFinishSetting = (Button)findViewById(R.id.buttonDrawingFinishSetting);
		mSettingLayout = (LinearLayout)findViewById(R.id.linearLayoutDrawingSettings);
		
		// ImportingActivityに移動
		mButtonToImporting.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClassName("com.aftercider.schwert","com.aftercider.schwert.ImportingActivity");
				startActivity(intent);
			}
		});
		
		// 設定用パネルを表示/非表示
		mImageButtonStartSetting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showSettings(!mIsShownSettings);
			}
		});
		
		mButtonFinishSetting.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				showSettings(false);
			}
		});
	}
	
	/**
	 * 画面下部に設定用パネルを表示して、画像をズーム・移動可能にする。
	 * 
	 * @param isShow trueなら表示して、falseなら隠す
	 */
	public void showSettings(boolean isShow){
		if(isShow == mIsShownSettings){
			return;
		}else{
			mIsShownSettings = isShow;
			
			if(mIsShownSettings){
				//いったん消して目的の位置に配置する
				//コレをしないとアニメーションの画像が作れなくてアニメが真っ暗
				mSettingLayout.setVisibility(LinearLayout.INVISIBLE);
				mSettingLayout.setPadding(0, 0, 0, 0);
				
				TranslateAnimation anim = new TranslateAnimation(0, 0, mSettingLayout.getHeight(), 0);
														//ヨコ幅めいっぱいの位置から開始
				anim.setDuration(500);					//500msecで移動し切る
				anim.setFillAfter(false);				//終了後に消す
				anim.setFillBefore(true);				//開始前に表示する
				anim.setAnimationListener(this);		//リスター登録
				mSettingLayout.startAnimation(anim);	//開始
			}else{
				//いったん消す
				mSettingLayout.setVisibility(LinearLayout.INVISIBLE);

				//アニメーション
				TranslateAnimation anim = new TranslateAnimation(0, 0, 0, mSettingLayout.getHeight());
									//ヨコ幅めいっぱいの位置まで移動
				anim.setDuration(500);			//500msecで移動し切る
				anim.setFillAfter(false);		//終了後に消す
				anim.setFillBefore(true);		//開始前に表示する
				anim.setAnimationListener(this);	//リスター登録
				mSettingLayout.startAnimation(anim);		//開始
			}
		}
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if(mIsShownSettings){
			//入ってきた
			mSettingLayout.setVisibility(LinearLayout.VISIBLE);
		}else{
			//外にでた
			int width = mSettingLayout.getWidth();
			mSettingLayout.setPadding(width, 0, -1 * width, 0);
			mSettingLayout.setVisibility(LinearLayout.VISIBLE);
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// Nothing to do.
		
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// Nothing to do.
		
	}
}
