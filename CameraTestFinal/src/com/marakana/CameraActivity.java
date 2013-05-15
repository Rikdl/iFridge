package com.marakana;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class CameraActivity extends Activity {
  private static final String TAG = "CameraDemo";
  Preview preview; // <1>
  Button buttonClick; // <2>
  private Timer timer;
  private boolean isLaunched=false;
  TextView message;
  String intentArg="";
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    setContentView(R.layout.main);
    
    // get arguments from other activity if available
    Bundle extras = null;
	try {
		extras = getIntent().getExtras();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		Log.d(TAG, "getExtras failed");
	}
    if (extras != null) {
    	
    	
       intentArg = extras.getString("BG");
     
        if(intentArg.equals("BACKGROUND"))
        {
        	
        	message =(TextView) findViewById(R.id.Welcome);
        	message.setText("Smile! Taking picture in 10 seconds");
        	
        }
    }
    
    else
    {
    	message =(TextView) findViewById(R.id.Welcome);
    	message.setText("Please hold the product Date in front of the camera");
    }
    
    preview = new Preview(this, intentArg); // <3>
    ((FrameLayout) findViewById(R.id.preview)).addView(preview); // <4>
    
    ImageView sepia = (ImageView) findViewById(R.id.sepia);
    if(intentArg.equals("BACKGROUND"))
    {
    	sepia.setVisibility(View.VISIBLE);
        sepia.setClickable(true);
        sepia.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        preview.changeToSepia();
                    }
                });
    }

    else{
    	
    	sepia.setVisibility(View.INVISIBLE);
    }
    Log.d(TAG, "onCreate'd");
    
    startTimer();
  }
  //timer function
  public void startTimer()
  {
	    timer = new Timer();
	    timer.schedule(new RemindTask(), 10*1000);
  }
  class RemindTask extends TimerTask {
	    public void run() {
	    	preview.camera.takePicture(shutterCallback, rawCallback, jpegCallback);
	        timer.cancel(); //Terminate the timer thread
	    }
	}
  // Called when shutter is opened
  ShutterCallback shutterCallback = new ShutterCallback() { // <6>
    public void onShutter() {
      Log.d(TAG, "onShutter'd");
    }
  };

  // Handles data for raw picture
  PictureCallback rawCallback = new PictureCallback() { // <7>
    public void onPictureTaken(byte[] data, Camera camera) {
      Log.d(TAG, "onPictureTaken - raw");
    }
  };

  // Handles data for jpeg picture
  PictureCallback jpegCallback = new PictureCallback() { // <8>
    public void onPictureTaken(byte[] data, Camera camera) {
      
    isLaunched=true;	
    FileOutputStream outStream = null;
      try {
        // Write to SD Card
    	if(intentArg.equals("BACKGROUND")){
        outStream = new FileOutputStream(String.format(Environment.getExternalStorageDirectory().getPath()+"/OCR/background.jpg",
            System.currentTimeMillis())); // <9>
    	}
    	else{
            outStream = new FileOutputStream(String.format(Environment.getExternalStorageDirectory().getPath()+"/OCR/ocr.jpg",
                System.currentTimeMillis())); // <9>
        	}
        outStream.write(data);
        outStream.close();
        Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length);
      } catch (FileNotFoundException e) { // <10>
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
      }
      
      camera.startPreview();
      Log.d(TAG, "onPictureTaken - jpeg");
      
      //if the background app called this activity, rescale the captured image to the screen size and finish the activity
      if(intentArg.equals("BACKGROUND")){
    	  BitmapFactory.Options options = new BitmapFactory.Options();
    	  Display display = getWindowManager().getDefaultDisplay();
		  Point size = new Point();
		  display.getSize(size);

		  options.inJustDecodeBounds=true;
		  Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath()+"/OCR/background.jpg", options);
		  
          if(options.outWidth > size.x && options.outHeight > size.y)
          {
          	//calculate downsample rate
          	int sampleRate = (int) Math.ceil((double)options.outWidth/size.x);
          	
          	//read in subsampled image
          	options.inJustDecodeBounds=false;
          	options.inSampleSize = sampleRate;
          	bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath()+"/OCR/background.jpg", options);
          }
          
          else
          {
          	options.inJustDecodeBounds=false;
          	bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath()+"/OCR/background.jpg", options);
          	
          }
		  
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

          bm.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
          
          try {
				fOut.flush();
				fOut.close();
				bm.recycle();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
    	  finish();
      }
      else{
		Intent intent = new Intent("com.datumdroid.android.ocr.simple.SimpleAndroidOCRActivity");
		startActivity(intent);
      }
		
    }
  };

  //Handles restart of the timer when program returned to this activity
  public void onResume(){
	  super.onResume();
	   if ( isLaunched == true )
		   startTimer();
	  }
  
  //cleanup: cancel timer when activity exits
  public void onDestroy(){
	  
	  super.onDestroy();
	  timer.cancel();
  }
  
}
