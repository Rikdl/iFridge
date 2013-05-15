package com.iFridge.mainlayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.iFridge.mainlayout.util.SystemUiHider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class Settings extends Activity {

	
	private static final int SELECT_PHOTO = 100;
	String TAG = "Settings";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_settings);
		
		//Set welcome text AND FONT
		TextView textTop = (TextView)findViewById(R.id.textView1);
		textTop.setText("Settings Menu");
				
		Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/STENCIL.TTF");
		textTop.setTypeface(tf);
				
		ImageButton takePicture = (ImageButton)findViewById(R.id.cameraButton);
		
		takePicture.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					Intent intent = new Intent(Settings.this, com.marakana.CameraActivity.class);
					String temp = "BACKGROUND";
					intent.putExtra("BG", temp);
					startActivity(intent);
				}
			});
		
		//Select image from user gallery as background
		ImageButton selectImage = (ImageButton)findViewById(R.id.selectImageButton);
		
		selectImage.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					
					Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
					photoPickerIntent.setType("image/*");
					startActivityForResult(photoPickerIntent, SELECT_PHOTO);  
				}
			});
		
		

	}
	
	// Subsample selected image from gallery and save it again, to the location of the app background picture 
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
	    super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 

	    switch(requestCode) { 
	    case SELECT_PHOTO:
	        if(resultCode == RESULT_OK){  
	            Uri selectedImage = imageReturnedIntent.getData();
	            InputStream imageStream = null;
				try {
					imageStream = getContentResolver().openInputStream(selectedImage);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//First save the raw bitmap
				Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream,null,null);
				saveImage(yourSelectedImage, 100);
				
				//Reload the bitmap , and rescale the image to the size of the screen of the tablet/phone
				BitmapFactory.Options options = new BitmapFactory.Options();
				Display display = getWindowManager().getDefaultDisplay();
				Point size = new Point();
				display.getSize(size);
				
				options.inJustDecodeBounds=true;
	            yourSelectedImage = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath()+"/OCR/background.jpg", options);
	            Log.d(TAG,"decoded step 1");
	            if(options.outWidth > size.x && options.outHeight > size.y)
	            {
	            	Log.d(TAG,"inside downsample loop");
	            	//calculate downsample rate
	            	int sampleRate = (int) Math.round((double)options.outWidth/(double)size.x);
	            	Log.d(TAG,"downsample rate: " + sampleRate);
	            	//read in subsampled image
	            	options.inJustDecodeBounds=false;
	            	options.inSampleSize = sampleRate;
	            	yourSelectedImage = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath()+"/OCR/background.jpg", options);
	            }
	            
	            else
	            {
	            	Log.d(TAG,"inside no subsample loop");
	            	options.inJustDecodeBounds=false;
	            	yourSelectedImage = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath()+"/OCR/background.jpg", options);
	            	
	            }
	            
	            saveImage(yourSelectedImage, 80);
	        }
	    }
	}
	
	
	public void saveImage(Bitmap yourSelectedImage,int quality)
	{
		
		Log.d(TAG,"start saving image");
        String file_path = Environment.getExternalStorageDirectory().getPath() + 
                "/OCR";
        File dir = new File(file_path);
        if(!dir.exists())
        	dir.mkdirs();
        File file = new File(dir, "background.jpg");
        FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        yourSelectedImage.compress(Bitmap.CompressFormat.JPEG, quality, fOut);
        
        try {
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Log.d(TAG,"end save data");
        yourSelectedImage.recycle();
	}
	@SuppressWarnings("deprecation")
	public void onResume(){
		super.onResume();
		
		Bitmap bm = null;
		ImageView background = (ImageView)findViewById(R.id.BackGround_Settings);
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


