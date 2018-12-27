package com.prokarma.zomato.service;

import com.prokarma.zomato.dto.Restaurant;

public interface RestaurantService {

	public Integer saveRestaurantData(Restaurant restaurant);
	public Restaurant getRestaurantDataById(Integer restaurantId);
	
}
