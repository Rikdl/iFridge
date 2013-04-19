package com.example.cameratester;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

class Preview extends ViewGroup implements SurfaceHolder.Callback {

    SurfaceView mSurfaceView;
    SurfaceHolder mHolder;

    Preview(Context context) {
        super(context);

        mSurfaceView = new SurfaceView(context);
        addView(mSurfaceView);

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
	    // Now that the size is known, set up the camera parameters and begin
	    // the preview.
	    Camera.Parameters parameters = mCamera.getParameters();
	    parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
	    requestLayout();
	    mCamera.setParameters(parameters);

	    /*
	      Important: Call startPreview() to start updating the preview surface. Preview must be
	      started before you can take a picture.
	    */
	    mCamera.startPreview();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		
	}
}
