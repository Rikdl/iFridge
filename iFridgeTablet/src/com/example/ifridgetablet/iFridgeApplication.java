package com.example.ifridgetablet;

import com.parse.Parse;

import android.app.Application;

public class iFridgeApplication extends Application {
	
	public void onCreate(){
		super.onCreate();
		
		Parse.initialize(this, "fXIMjogK4OsawEieTVPv4vjQ67xqoOa67henEG27", "plHI5I9bt5RJIgmEoWeOruc203uUOIJHtSrKRsIU"); 
	
	}

}
