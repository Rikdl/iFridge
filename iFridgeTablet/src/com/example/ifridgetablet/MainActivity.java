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
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private Product product;
	private WordCaps wordcaps;
	private TextView productName;
	private TextView expireDate;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    Parse.initialize(this, "fXIMjogK4OsawEieTVPv4vjQ67xqoOa67henEG27", "plHI5I9bt5RJIgmEoWeOruc203uUOIJHtSrKRsIU"); 
	    
		wordcaps = new WordCaps();
		product = new Product();
		
	    search();
	    
	    productName = (TextView) findViewById(R.id.product_content);
	    expireDate = (TextView) findViewById(R.id.expiredate_content);
	    
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public Product createDummyProduct(){
		product.setBarcode("5449000000996");
		return product;
	}
	
	public void search(){
		ParseQuery query = new ParseQuery("BarcodeScan");
		query.whereEqualTo("Barcode", "5449000000996");
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
		product.setBarcode("5449000000996");
		product.setName(object.getString("ProductName"));
		product.setExpireDate("morgen");
		
	    productName.setText(product.getName()); 
	    expireDate.setText(product.getExpireDate());
	}
	
	
	
}
