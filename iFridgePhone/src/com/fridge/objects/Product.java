package com.fridge.objects;

import com.parse.ParseFile;

public class Product {

	private String expireDate;
	private String name;
	private int barcode;
	private ParseFile image;
	


	/**
	 * @param expireDate
	 * @param name
	 * @param barcode
	 */
	public Product() {
		this.expireDate = "";
		this.name = "";
		this.barcode = 0;
	}
	
	/**
	 * @return the expireDate
	 */
	public String getExpireDate() {
		return expireDate;
	}
	/**
	 * @param expireDate the expireDate to set
	 */
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the barcode
	 */
	public int getBarcode() {
		return barcode;
	}
	/**
	 * @param barcode the barcode to set
	 */
	public void setBarcode(int barcode) {
		this.barcode = barcode;
	}
	public ParseFile getImage() {
		return image;
	}

	public void setImage(ParseFile image) {
		this.image = image;
	}
	
	
	

}
