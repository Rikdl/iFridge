package com.marakana;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import java.util.Timer;
import java.util.TimerTask;


public class CameraActivity extends Activity {
  private static final String TAG = "CameraDemo";
  Preview preview; // <1>
  Button buttonClick; // <2>
  private Timer timer;
  private boolean isLaunched=false;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    preview = new Preview(this); // <3>
    ((FrameLayout) findViewById(R.id.preview)).addView(preview); // <4>

    Log.d(TAG, "onCreate'd");
    
    startTimer();
  }
  //timer function
  public void startTimer()
  {
	    timer = new Timer();
	    timer.schedule(new RemindTask(), 12*1000);
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
        outStream = new FileOutputStream(String.format("/sdcard/OCR/ocr.jpg",
            System.currentTimeMillis())); // <9>
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
		Intent intent = new Intent("com.datumdroid.android.ocr.simple.SimpleAndroidOCRActivity");
		startActivity(intent);
		
    }
  };

  //Handles restart of the timer when program returned to this activity
  public void onResume(){
	  super.onResume();
	   if ( isLaunched == true )
		   startTimer();
	  }

}
