package com.prokarma.zomato.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.prokarma.zomato.dto.Restaurant;

@Repository
public class RestaurantDAOImpl implements RestaurantDAO{
	
	@PersistenceContext
	private EntityManager entityManager;
	 
	@Override
	@Transactional
	public Integer saveRestaurantData(Restaurant restaurant) {
		entityManager.persist(restaurant);
		return restaurant.getId();
	}

	@Override
	public Restaurant getRestaurantDataById(Integer restaurantId) {
		Restaurant restaurant = null;
		restaurant = entityManager.find(Restaurant.class, restaurantId);
		return restaurant;
	}
	
}
