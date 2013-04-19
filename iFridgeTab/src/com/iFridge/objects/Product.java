package com.iFridge.objects;




public class Product {

	private String expireDate;
	private String name;
	private String barcode;
	private String path;



	/**
	 * @param expireDate
	 * @param name
	 * @param barcode
	 */
	public Product() {
		this.expireDate = "";
		this.name = "";
		this.barcode = "0";
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
	public String getBarcode() {
		return barcode;
	}
	/**
	 * @param barcode the barcode to set
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	/**
	 * @return the barcode
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param barcode the barcode to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	
	
	

}
