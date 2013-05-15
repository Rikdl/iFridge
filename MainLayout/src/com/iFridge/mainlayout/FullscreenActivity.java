package com.iFridge.mainlayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class FullscreenActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_fullscreen);
				
		ImageButton settings = (ImageButton)findViewById(R.id.settingsButton);
		settings.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent(FullscreenActivity.this, Settings.class);
				startActivity(intent);
			}
		});
		
		//Set welcome text AND FONT
		TextView textTop = (TextView)findViewById(R.id.textView1);
		textTop.setText("Welcome, Smith's family!");
		
		Typeface tf = Typeface.createFromAsset(getAssets(),
		        "fonts/STENCIL.TTF");
		textTop.setTypeface(tf);
	}
	
	public void onResume(){
		super.onResume();
		
		Bitmap bm = null;
		ImageView background = (ImageView)findViewById(R.id.BackGround_Main);
		try {
			bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath()+"/OCR/background.jpg", null);
			background.setImageBitmap(bm);
		} catch (Exception e) {
			
		}
		if(bm==null)
		{
			Drawable bg = getResources().getDrawable( R.drawable.bgllayout );
			background.setBackgroundDrawable(bg);
		}
	}
	
}
