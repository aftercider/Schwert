package com.aftercider.schwert;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ImportingActivity extends Activity {
	private Button mButtonImportingToGallery = null;
	public static int REQUEST_GALLERY = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_importing);
		
		createWidgets();
	}
	
	private void createWidgets(){
		mButtonImportingToGallery = (Button)findViewById(R.id.buttonImportingToGallery);
		mButtonImportingToGallery.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType("image/*");
				startActivityForResult(intent, REQUEST_GALLERY);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == REQUEST_GALLERY && resultCode == RESULT_OK){
			// DrawingActivityに送る
			setResult(RESULT_OK, data);
			finish();
		}
	}
}
