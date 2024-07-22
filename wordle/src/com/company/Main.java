package com.company;

import WordList.Loader;

public class Main {

    public static void main(String[] args) {
        Loader loader;

	    try {
            loader = new Loader(5);
        } catch (Exception e) {
	        return;
        }
        System.out.println(loader.getWordList());
    }
}
