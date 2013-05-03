package com.aftercider.schwert;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 起動時に表示されるActivity。
 * ロゴやバージョン、メイン画面へのボタン、コンフィグへのボタンが表示される。
 * 
 * @author Aftercider
 *
 */
public class HomeActivity extends Activity {
	
	private Button mButtonToDrawing = null;
	private Button mButtonToConfig  = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		createWidgets();
	}
	
	private void createWidgets() {
		mButtonToDrawing = (Button)findViewById(R.id.buttonHomeToDrawing);
		mButtonToConfig  = (Button)findViewById(R.id.buttonHomeToConfig);
		
		mButtonToDrawing.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClassName("com.aftercider.schwert","com.aftercider.schwert.DrawingActivity");
				startActivity(intent);
			}
		});
		mButtonToConfig.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClassName("com.aftercider.schwert","com.aftercider.schwert.ConfigActivity");
				startActivity(intent);
			}
		});
	}
}
