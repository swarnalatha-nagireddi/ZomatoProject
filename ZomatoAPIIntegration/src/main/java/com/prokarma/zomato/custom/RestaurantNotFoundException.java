package com.prokarma.zomato.custom;

public class RestaurantNotFoundException extends RuntimeException{

	  public RestaurantNotFoundException(String exception) {
	    super(exception);
	  }

}
