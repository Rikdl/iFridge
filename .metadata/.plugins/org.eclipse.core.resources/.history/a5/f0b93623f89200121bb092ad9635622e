package com.zxingtesting;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zxingtesting.R;

public class BarcodeReading extends Activity {
	private String contents;
	private TextView barcodeT;

	// test voor rik den 2de
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_barcode_reading);
		barcodeT = (TextView) findViewById(R.id.textView1);
	}	

	// working attempt no return
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
	            barcodeT.setText(contents);
	        } else if (resultCode == RESULT_CANCELED) {
	            // Handle cancel
	        	barcodeT.setText("failed scan");
	        }
	    }
	}

}
