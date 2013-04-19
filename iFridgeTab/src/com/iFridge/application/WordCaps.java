package com.iFridge.application;
/**
 * 
 * @author Marijn and Charlotte
 * This class will take a word and makes the first Character to upper case
 */

public class WordCaps {

	/**
	 * @param rest
	 * @param firstLetter
	 */
	private String rest;
	private String firstLetter;
	
	public WordCaps(){
		
	}
	
	/**
	 * 
	 * @param word
	 * @return
	 */
	public String wordOrganizer(String word) {
		parseWord(word);
		setCaps();
		word = firstLetter + rest;
		return word;
	}
	
	/**
	 * 
	 */
	private void setCaps() {
		this.rest = rest.toLowerCase();
		this.firstLetter = firstLetter.toUpperCase();
	}
	
	/**
	 * 
	 * @param word
	 */
	private void parseWord(String word) {
		this.rest = word.substring(1);
		this.firstLetter = "" + word.charAt(0);
	}
}
