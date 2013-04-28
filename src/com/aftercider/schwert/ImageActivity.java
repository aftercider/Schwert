package com.aftercider.schwert;

import java.io.InputStream;

import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.widget.ImageView;

public class ImageActivity extends Activity {

	private static final int REQUEST_GALLERY = 0;
	private ImageView imageView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);

		imageView = (ImageView) findViewById(R.id.imageView);

		pickFilenameFromGallery();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.brower, menu);
		return true;
	}

	private void pickFilenameFromGallery() {
		Intent i = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, REQUEST_GALLERY);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
			try {
				InputStream in = getContentResolver().openInputStream(
						data.getData());
				Bitmap img = BitmapFactory.decodeStream(in);
				in.close();
				// 選択した画像を表示
				imageView.setImageBitmap(img);
			} catch (Exception e) {

			}
		}
	}
}
