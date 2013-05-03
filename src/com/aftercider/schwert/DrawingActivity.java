package com.aftercider.schwert;

import java.io.IOException;
import java.io.InputStream;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * お絵かきできるように、選択した画像を表示する。
 * 左上or右上のボタンを押すと設定パネルが表示され、明るさの調整や、画像の指定アクティビティを起動できる。
 * また、パネルが表示されると画像はスクロール・ズームができるようになる。
 * 
 * @author Kentaro
 *
 */
public class DrawingActivity extends Activity implements AnimationListener {

	// ImportingActivityのリクエストコード
	public static int REQUEST_IMPORT = 1;
	
	// 設定パネルの表示状態
	private boolean mIsShownSettings = false;
	
	// 設定パネル
	private LinearLayout mSettingLayout          = null;
	private Button mButtonToImporting            = null;
	private Button mButtonFinishSetting          = null;
	private ImageButton mImageButtonStartSetting = null;
	private SeekBar mSeekBar                     = null;
	
	// 表示されている画像
	private DraftImageView mImageView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawing);
		
		createWidgets();
		mSettingLayout.setVisibility(LinearLayout.INVISIBLE);
	}
	
	/**
	 * ウィジェットを変数とつないで、ボタンのコールバック設定
	 */
	private void createWidgets(){
		mButtonToImporting       = (Button)findViewById(R.id.buttonDrawingToImporting);
		mImageButtonStartSetting = (ImageButton)findViewById(R.id.imageButtonDrawingStartSetting);
		mButtonFinishSetting     = (Button)findViewById(R.id.buttonDrawingFinishSetting);
		mSettingLayout           = (LinearLayout)findViewById(R.id.linearLayoutDrawingSettings);
		mImageView               = (DraftImageView)findViewById(R.id.imageViewDrawing);
		mSeekBar                 = (SeekBar)findViewById(R.id.seekBarDrawing);
		
		// ImportingActivityに移動
		mButtonToImporting.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClassName("com.aftercider.schwert","com.aftercider.schwert.ImportingActivity");
				startActivityForResult(intent, REQUEST_IMPORT);
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
		
		// シークバーで明るさ調整
		mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// nothing to do.
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// nothing to do.
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.screenBrightness = (float)progress / mSeekBar.getMax();
				getWindow().setAttributes(lp);
			}
		});
		
		// シークバーに現在の明るさを入力
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		mSeekBar.setProgress(Math.round(lp.screenBrightness * mSeekBar.getMax()));
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
			
			mImageView.setClickable(!mIsShownSettings);
			
			if(mIsShownSettings){
				//いったん消して目的の位置に配置する
				//コレをしないとアニメーションの画像が作れなくてアニメが真っ暗
				mSettingLayout.setVisibility(LinearLayout.INVISIBLE);
				mSettingLayout.setPadding(0, 0, 0, 0);
				
				TranslateAnimation anim = new TranslateAnimation(0, 0, mSettingLayout.getHeight(), 0);
														//ヨコ幅めいっぱいの位置から開始
				anim.setDuration(300);					//500msecで移動し切る
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
				anim.setDuration(300);			//500msecで移動し切る
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == REQUEST_IMPORT && resultCode == RESULT_OK){
			// 画像を読み込む
			Bitmap image = loadImage(data.getData());
			if(image != null){
				mImageView.setImageBitmap(image);
				return;
			}else{
				return;
			}
		}
	}
	
	/**
	 * 指定されたパスの画像を読み込み、Bitmapを返す
	 * @param uri 読み込む画像のパス
	 */
	private Bitmap loadImage(Uri uri){
		 try {
             InputStream iStream = getContentResolver().openInputStream(uri);
             Bitmap bm = BitmapFactory.decodeStream(iStream);
             iStream.close();
             return bm;
         }catch (IOException e) {
        	 return null;
         }
	}
}
