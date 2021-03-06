package com.fridge.fridgecontent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fridge.application.Decompress;
import com.fridge.application.R;
import com.fridge.application.WordCaps;
import com.fridge.application.download;
import com.fridge.listview.ProductListViewAdapter;
import com.fridge.objects.Product;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;





public class Fridge_Content extends Activity {
	
	private ListView product_listview;
	private ArrayList<Product> productList;
	private ArrayList<Product> filteredProductList;
	private ProductListViewAdapter lvcAdapter;
	private Button searchProducts;
	private WordCaps wordcaps;
	private EditText textSelectedProducts;
	private boolean searched;
	private ProgressDialog progressDialog;
	private ImageView imageDisplay;
	private TextView tekstDisplay;
	
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		wordcaps = new WordCaps();
		
		searchProducts = (Button) findViewById(R.id.search_search);
		textSelectedProducts = (EditText) findViewById(R.id.searchfield_search);
		textSelectedProducts.setVisibility(View.GONE);
		
		
		product_listview = (ListView) findViewById(R.id.listview_search);
		productList = new ArrayList<Product>();
		filteredProductList = new ArrayList<Product>();
		
		imageDisplay = (ImageView) findViewById(R.id.imageView_display);
		tekstDisplay = (TextView) findViewById(R.id.textView_display);
		
		searched = false;
		textSelectedProducts.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){
	        	searched = true;
	        	Search_Fridge_Content fridgeContent = new Search_Fridge_Content(productList, textSelectedProducts.getText().toString().toLowerCase());
	    		filteredProductList = (ArrayList<Product>) fridgeContent.searchProducts();
	    		PopulateListView();
	        }
	    }); 
		
		/**
		 * The onItemClickListener to check on what object there has been clicked in the listview and how to continue with that
		 */
		product_listview.setOnItemClickListener(new OnItemClickListener() 
		{
			public void onItemClick (AdapterView<?> arg0, View arg1, int position, long positionlong) {
				textSelectedProducts.setVisibility(View.GONE);
				product_listview.setVisibility(View.GONE);
				
				//setContentView(R.layout.display);
				createFolder();
			}
		});
		
	}
	
	public void Search(View view){
		searchProducts.setVisibility(View.GONE);
		progressDialog = ProgressDialog.show(this, "Searching content", "Loading");
		ParseQuery query = new ParseQuery("Fridge");
		query.orderByAscending("Product");
		query.findInBackground(new FindCallback() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if(e == null){
					Log.d("succes", Integer.toString(objects.size()));
					setListview(objects);
					
				}else{
					Log.d("error","error");
				}
				
			}
			
		});
	}
	
	
	private void setListview(List<ParseObject> objects) {
		for(int i = 0; i<=objects.size()-1;i++){
			Product product = new Product();
			product.setName(wordcaps.wordOrganizer(objects.get(i).getString("Product")));
			product.setExpireDate(objects.get(i).getString("ExpireDate"));
			productList.add(product);
		}
		PopulateListView();
		progressDialog.dismiss();
		
	}
	
	/**
	 * This method will populate the listview with either cooks or recipes
	 */
	private void PopulateListView() {
		// clear previous results in the LV
		product_listview.setAdapter(null);
		
		if(productList.size()>= 1){
			textSelectedProducts.setVisibility(View.VISIBLE);
			product_listview.setVisibility(View.VISIBLE);
			//populate
			if (searched){
				lvcAdapter = new ProductListViewAdapter(this.getBaseContext(), filteredProductList, this);
			}else{
				lvcAdapter = new ProductListViewAdapter(this.getBaseContext(), productList, this);
			}
			product_listview.setAdapter(lvcAdapter);
		}
		
	}
	
	public void createFolder(){
		File direct = new File(Environment.getExternalStorageDirectory() + "/ProductImages_iFridge");

		if(!direct.exists())
		{
			if(direct.mkdir()) 
			{

			}

		}
		Intent intent = new Intent(this, download.class);
		startActivity(intent);
	}
	
}