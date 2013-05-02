package com.aftercider.schwert;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.app.Activity;

public class DrawingActivity extends Activity {

	private boolean mIsShownSettings = false;
	private Button mButtonToImporting = null;
	private ImageButton mImageButtonToSetting = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawing);
		
		createWidgets();
	}
	
	private void createWidgets(){
		mButtonToImporting = null; // TODO: 作る
		mImageButtonToSetting = (ImageButton)findViewById(R.id.imageButtonDrawingToSetting);
		
		/*mButtonToImporting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		*/
		
		mImageButtonToSetting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showSettings(true);
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
		}
	}
}
