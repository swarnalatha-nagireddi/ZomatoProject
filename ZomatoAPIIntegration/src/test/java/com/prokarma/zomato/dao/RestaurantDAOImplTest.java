package com.prokarma.zomato.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.prokarma.zomato.TestConfiguration;
import com.prokarma.zomato.dto.Restaurant;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class RestaurantDAOImplTest {

	@Autowired
	@InjectMocks
	RestaurantDAOImpl restaurantDAOImpl;

	@Test
	@Transactional
	@Rollback(true)
	public void testGetRestaurantDataById() {
		Restaurant restaurant = restaurantDAOImpl.getRestaurantDataById(10002);
		Assert.assertNotNull(restaurant);
		Assert.assertEquals("Udipi", restaurant.getName());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSaveRestaurant() {
		Restaurant restaurant = new Restaurant();
		restaurant.setId(43088);
		restaurant.setName("Birynis");
		restaurant.setUrl("www.hydbiryanis.com");
		Integer restaurantId = restaurantDAOImpl.saveRestaurantData(restaurant);
		Assert.assertNotNull(restaurantId);
		Assert.assertEquals(restaurant.getId(), restaurantId);
	}
}