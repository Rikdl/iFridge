package com.fridge.application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Application;
import android.os.Environment;

import com.parse.Parse;


public class IFridgeApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		//try {
		//	createFolder();
		//} catch (Exception e) {
		//	// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		

		// Add your initialization code here
		Parse.initialize(this, "fXIMjogK4OsawEieTVPv4vjQ67xqoOa67henEG27", "plHI5I9bt5RJIgmEoWeOruc203uUOIJHtSrKRsIU");
		
	}
	/**
	
	public void createFolder() throws Exception{
		//File direct = new File(Environment.getExternalStorageDirectory() + "/ProductImages_iFridge");

		 // if(!direct.exists())
		   // {
		   //     if(direct.mkdir()) 
		        //  {
		        	String urldownload = new URL("https://dl.dropbox.com/s/ni27amabnooysqt/Product_Pictures.zip?token_hash=AAHaMHM4zCR7efzjcWkhCAy9BCS3pHgkQgHbVzQry5rNtg&dl=1").toString();
		    		String urlToPut = Environment.getExternalStorageDirectory() + "/ProductImages_iFridge/"; 
		        	downloadFile(urldownload,urlToPut);
		
		        //  }

		 //   }
	}
	
	/**
	 * Downloads a remote file and stores it locally
	 * @param from Remote URL of the file to download
	 * @param to Local path where to store the file
	 * @throws Exception Read/write exception
	 */
	/**static private void downloadFile(String from, String to) throws Exception {
	    HttpURLConnection conn = (HttpURLConnection)new URL(from).openConnection();
	    conn.setDoInput(true);
	    conn.setConnectTimeout(10000); // timeout 10 secs
	    conn.connect();
	    InputStream input = conn.getInputStream();
	    FileOutputStream fOut = new FileOutputStream(to);
	    int byteCount = 0;
	    byte[] buffer = new byte[4096];
	    int bytesRead = -1;
	    while ((bytesRead = input.read(buffer)) != -1) {
	        fOut.write(buffer, 0, bytesRead);
	        byteCount += bytesRead;
	    }
	    fOut.flush();
	    fOut.close();
	    
	    unzip();
	}
	
	public static void unzip(){
		String zipFile = Environment.getExternalStorageDirectory() + "/ProductImages_iFridge/Products_Pictures.zip"; 
		String unzipLocation = Environment.getExternalStorageDirectory() + "/ProductImages_iFridge/"; 
		 
		Decompress d = new Decompress(zipFile, unzipLocation); 
		d.unzip(); 
	}*/
	
	

}


