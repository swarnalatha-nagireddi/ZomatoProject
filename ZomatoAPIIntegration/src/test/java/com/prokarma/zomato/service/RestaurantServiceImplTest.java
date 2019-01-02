package com.prokarma.zomato.service;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

import org.h2.engine.Database;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.prokarma.zomato.TestConfiguration;
import com.prokarma.zomato.dto.Restaurant;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class RestaurantServiceImplTest {

	@Mock
	Restaurant restaurant;

	@Autowired
	@InjectMocks
	RestaurantServiceImpl restaurantServiceImpl;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSaveRestaurant() {
		System.out.println("testing saveRestaurantData");

		// Mocking restaurant methods
		when(restaurant.getName()).thenReturn("Sample Restaurant");
		when(restaurant.getUrl()).thenReturn("www.samplerestaurant.com");
		when(restaurant.getId()).thenReturn(new Integer(4388));

		// Create sample restaurant
		Restaurant restaurant = new Restaurant();
		restaurant.setId(43088);
		restaurant.setName("Birynis");
		restaurant.setUrl("www.hydbiryanis.com");

		// Call save restaurant method
		Integer restaurantId = restaurantServiceImpl.saveRestaurantData(restaurant);

		// Assert expected results
		Assert.assertNotNull(restaurantId);
		Assert.assertEquals(restaurant.getId(), restaurantId);
	}

	@Test
	public void testGetRestaurantDataById() {
		System.out.println("testing getRestaurantDataById");
		Restaurant restaurant = restaurantServiceImpl.getRestaurantDataById(10002);
		Assert.assertNotNull(restaurant);
		Assert.assertEquals("Udipi", restaurant.getName());
	}

}