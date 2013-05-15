package com.marakana;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class Preview extends SurfaceView implements SurfaceHolder.Callback { // <1>
  private static final String TAG = "Preview";

  SurfaceHolder mHolder;  // <2>
  public Camera camera; // <3>
  String intentArg;

  Preview(Context context,String intentArg) {
    super(context);
    this.intentArg = intentArg;
    // Install a SurfaceHolder.Callback so we get notified when the
    // underlying surface is created and destroyed.
    mHolder = getHolder();  // <4>
    mHolder.addCallback(this);  // <5>
    mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); // <6>
  }

  // Called once the holder is ready
  public void surfaceCreated(SurfaceHolder holder) {  // <7>
    // The Surface has been created, acquire the camera and tell it where
    // to draw.
    camera = Camera.open(); // <8>
    try {
		Camera.Parameters par= camera.getParameters();
		if(intentArg.equals("BACKGROUND"))
		{
			par.setColorEffect(Parameters.EFFECT_NONE);
		
		}
		else
		{			
			par.setColorEffect(Parameters.EFFECT_MONO);
		}
		par.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
		
		//For testing, getting supported picture sizes and focus modes
	    ArrayList<String> list = new ArrayList<String>();
	    list = (ArrayList<String>) par.getSupportedFocusModes();
	    System.out.println(list.toString());
	    ArrayList<Camera.Size> list2 = new ArrayList<Camera.Size>();
	    list2 = (ArrayList<Camera.Size>) par.getSupportedPictureSizes();
	    Camera.Size result = null;
	    for (int i=0;i<list2.size();i++){
	        result = (Camera.Size) list2.get(i);
	        Log.d("PictureSize", "Supported Size. Width: " + result.width + "height : " + result.height); 
	    }
	    
	    
	    camera.setParameters(par);
	} catch (Exception e1) {
		Log.d(TAG, "Set Params failed");
		
	}
    
    try {
      camera.setPreviewDisplay(holder);  // <9>

      camera.setPreviewCallback(new PreviewCallback() { // <10>
        // Called for each frame previewed
        public void onPreviewFrame(byte[] data, Camera camera) {  // <11>
          Log.d(TAG, "onPreviewFrame called at: " + System.currentTimeMillis());
          Preview.this.invalidate();  // <12>
        }
      });
    } catch (IOException e) { // <13>
      e.printStackTrace();
    }
  }

  // Called when the holder is destroyed
  public void surfaceDestroyed(SurfaceHolder holder) {  // <14>
    camera.stopPreview();
    camera.release();
    //camera.release();
    camera = null;
  }

  // Called when holder has changed
  public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) { // <15>
	  camera.stopPreview();
	  camera.startPreview();
  }

  public void changeToSepia(){
	  
	  Camera.Parameters par= camera.getParameters();
      par.setColorEffect(Parameters.EFFECT_SEPIA);
      par.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
	  camera.setParameters(par);
	  
  }
}