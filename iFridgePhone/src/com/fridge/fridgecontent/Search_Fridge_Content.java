/**
 * 
 */
package com.fridge.fridgecontent;

import java.util.ArrayList;
import java.util.List;

import com.fridge.objects.Product;

/**
 * @author Marijn
 *
 */
public class Search_Fridge_Content {

	private List<Product> productlist;
	private List<Product> filteredproductlist;
	private String word;
	/**
	 * 
	 */
	public Search_Fridge_Content(List<Product> productlist, String word) {
		this.productlist = productlist;
		this.word = word;
		filteredproductlist = new ArrayList<Product>();
	}
	
	public List<Product> searchProducts(){
		for(int i = 0; i<=productlist.size()-1;i++){
			String productname = productlist.get(i).getName().toLowerCase();
			if (productname.contains(word)){
				filteredproductlist.add(productlist.get(i));
			}
		}
		
		return filteredproductlist;
		
	}

}
