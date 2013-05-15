package com.deleeuw.rik.stickies;


import wei.mark.standout.StandOutWindow;
import android.app.Activity;
import android.os.Bundle;

public class Sticky_Launcher extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		StandOutWindow.closeAll(this, Sticky.class);
		Sticky.showFolders(this);
		
		finish();
	}
}