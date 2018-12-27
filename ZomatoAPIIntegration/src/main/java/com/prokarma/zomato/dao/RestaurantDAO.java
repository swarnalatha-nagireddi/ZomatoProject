package com.prokarma.zomato.dao;

import com.prokarma.zomato.dto.Restaurant;

public interface RestaurantDAO {

	public Integer saveRestaurantData(Restaurant restaurant);
	public Restaurant getRestaurantDataById(Integer restaurantId);
	
}
