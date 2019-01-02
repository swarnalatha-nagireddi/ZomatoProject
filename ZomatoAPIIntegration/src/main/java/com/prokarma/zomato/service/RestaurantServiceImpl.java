package com.prokarma.zomato.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.prokarma.zomato.dao.RestaurantDAO;
import com.prokarma.zomato.dto.Restaurant;

@Service("restaurantService")
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	private RestaurantDAO restaurantDAO;
	
	@Override
	public Integer saveRestaurantData(Restaurant restaurant) {
		Restaurant restaurantExisted = getRestaurantDataById(restaurant.getId());
		if(restaurantExisted!=null)
			return restaurant.getId();
		return restaurantDAO.saveRestaurantData(restaurant);
	}

	@Override
	public Restaurant getRestaurantDataById(Integer restaurantId) {
		Restaurant restaurant = null;
		restaurant = restaurantDAO.getRestaurantDataById(restaurantId);
		return restaurant;
	}

}
