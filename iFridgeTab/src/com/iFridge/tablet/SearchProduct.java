package com.iFridge.tablet;

import java.util.List;

import com.iFridge.objects.Product;

public class SearchProduct {
	
	private List<Product> productlist;
	private String barcode;
	private Product product;
	
	public SearchProduct(List<Product> productlist, String barcode) {
		this.productlist = productlist;
		this.barcode = barcode;
		product = searchProductInDatabase();
	}
	
	public Product searchProductInDatabase(){
		for(int i = 0; i<=productlist.size()-1;i++){
			String bar = productlist.get(i).getBarcode();
			if (bar.equals(barcode)){
				return product;
			}
		}
		return null;
		
	}
	
}