package com.example.ifridgetablet;

import java.util.List;

import com.fridge.objects.*;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.PushService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private Product product;
	private WordCaps wordcaps;
	private TextView productName;
	private TextView expireDate;
	private String contents;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    Parse.initialize(this, "fXIMjogK4OsawEieTVPv4vjQ67xqoOa67henEG27", "plHI5I9bt5RJIgmEoWeOruc203uUOIJHtSrKRsIU"); 
	    
		wordcaps = new WordCaps();
		product = new Product();
		
		//mScan();
	    
	    productName = (TextView) findViewById(R.id.product_content);
	    expireDate = (TextView) findViewById(R.id.expiredate_content);
	    
	    
	}
	
	public void mScan(View view){
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
		startActivityForResult(intent, 123);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
	    if (requestCode == 123) {
	        if (resultCode == RESULT_OK) {
	            contents = intent.getStringExtra("SCAN_RESULT");
	            String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
	            // Handle successful scan
	    	    search(contents);
	        } else if (resultCode == RESULT_CANCELED) {
	            // Handle cancel
	        }
	    }
	}
	
	public void search(String content){
		ParseQuery query = new ParseQuery("BarcodeScan");
		query.whereEqualTo("Barcode", content );
		query.getFirstInBackground(new GetCallback() {
			@Override
			public void done(ParseObject object, ParseException e) {
				if (e == null) {
					Log.d("succes", "succes");
					setProduct(object);
		        } else {
		        	Log.d("error","error");
		        }			
			}
		});
	}
	
	public void setProduct(ParseObject object){
		product.setBarcode(object.getString("Barcode"));
		product.setName(object.getString("ProductName"));
		product.setExpireDate("morgen");
		
	    productName.setText(product.getName()); 
	    expireDate.setText(product.getExpireDate());
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

	
	
}
