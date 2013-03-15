/**
 * 
 */
package com.fridge.listview;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fridge.application.R;
import com.fridge.objects.Product;




/**
 * @author Marijn and Charlotte
 * The class is an adapter that will be used to fill a listview.
 * This specific adapter is used to fill a listview with Recipe objects
 */
public class ProductListViewAdapter extends android.widget.BaseAdapter {
	
	/**
	 * @param adapterListRecipe: The list of recipeobjects that will be added to the listview
	 * @param context: the context of current state of the application
	 * @param callingActivity: The activity that calls the adapter
	 */
	private List<Product> adapterListProduct;
	@SuppressWarnings("unused")
	private	Context context;
	private Activity callingActivity;
	
	public ProductListViewAdapter(Context context, List<Product> adapterListProduct, Activity callingActivity) {
		this.context = context;
		this.adapterListProduct = adapterListProduct;
		this.callingActivity = callingActivity;
	}
	
	/**
	 * Get the amount of objects in the listview
	 * @return amount of recipes in list
	 */
	@Override
	public int getCount() {
		return adapterListProduct.size();
	}

	/**
	 * Get the position of an object in the listview
	 * @return the position of the object
	 */
	@Override
	public Object getItem(int position) {
		return adapterListProduct.get(position);
	}
	
	/**
	 * Get the position of an object in the listview as a long
	 * @return the position of the object as a long
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/**
	 * This method will setup the objects in the listview, give it the correct layout and it's position 
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Product product = adapterListProduct.get(position);
		
		LayoutInflater lf = callingActivity.getLayoutInflater();
		LinearLayout ll = (LinearLayout) lf.inflate(R.layout.product_fridge_listview, null);
		((TextView) ll.findViewById(R.id.product_listview)).setText(product.getName());
		((TextView) ll.findViewById(R.id.date_listview)).setText(product.getExpireDate());
		return ll;
	}

}
