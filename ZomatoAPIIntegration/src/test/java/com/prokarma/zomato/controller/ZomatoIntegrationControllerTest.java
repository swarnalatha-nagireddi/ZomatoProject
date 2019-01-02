package com.prokarma.zomato.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URLEncoder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.prokarma.zomato.dto.Restaurant;
import com.prokarma.zomato.service.RestaurantService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ZomatoIntegrationController.class, secure = false)
public class ZomatoIntegrationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RestaurantService restaurantService;

	@InjectMocks
	ZomatoIntegrationController zomatoIntegrationController;

	Restaurant mockRestaurant = new Restaurant(3029, "Restaurant Test", "www.restauranttest.com");

	String exampleRestaurantJson = "{\"id\":\"98498\",\"name\":\"Simple Restaurant\",\"url\":\"www.simplerestaurant.com\"}";

	Restaurant restaurant;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		restaurant = new Restaurant();
		restaurant.setId(43088);
		restaurant.setName("Test Restaurant");
		restaurant.setUrl("www.testrestaurant.com");
	}

	@Test
	public void testMockGetRestaurantDetailsFromDB() {
		when(restaurantService.getRestaurantDataById(anyInt())).thenReturn(restaurant);
		Restaurant restaurant1 = restaurantService.getRestaurantDataById(10002);
		assertNotNull(restaurant1);
		assertEquals("Test Restaurant", restaurant1.getName().toString());
	}

	@Test
	public void testInvokeZomatoAPI() throws Exception {

		Mockito.when(restaurantService.getRestaurantDataById(Mockito.anyInt())).thenReturn(mockRestaurant);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/restaurantDetails")
				.accept(MediaType.TEXT_HTML);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println("response content" + result.getResponse().getContentAsString());
		String expected = "16774318 Saved Successfully";

		assertEquals(expected.trim().toString(), result.getResponse().getContentAsString().trim().toString());
	}

	@Test
	public void testGetRestaurantDetailsFromDB() throws Exception {
		Integer restaurantId = 10001;
		Mockito.when(restaurantService.getRestaurantDataById(Mockito.anyInt())).thenReturn(mockRestaurant);
		mockMvc.perform(get("/getRestaurantDetailsFromDB").param("restaurantId", restaurantId.toString())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith("application/json")).andDo(print());
	}

	@Test
	public void testConsumeRestaurantDetails() throws Exception {
		String jsonDataToSave = "{\"R\":{\"res_id\":16774388},\"apikey\":\"718f7d88e935e0df4103da96ba7eb81c\",\"id\":\"16774388\",\"name\":\"Otto Enoteca Pizzeria\",\"url\":\"https:\\/\\/www.zomato.com\\/new-york-city\\/otto-enoteca-pizzeria-greenwich-village?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1\",\"location\":{\"address\":\"One Fifth Avenue at 8th Street, Greenwich Village 10003\",\"locality\":\"Greenwich Village\",\"city\":\"New York City\",\"city_id\":280,\"latitude\":\"40.7318200000\",\"longitude\":\"-73.9965400000\",\"zipcode\":\"10003\",\"country_id\":216,\"locality_verbose\":\"Greenwich Village\"},\"switch_to_order_menu\":0,\"cuisines\":\"Pizza, Italian\",\"average_cost_for_two\":60,\"price_range\":4,\"currency\":\"$\",\"offers\":[],\"opentable_support\":0,\"is_zomato_book_res\":0,\"mezzo_provider\":\"OTHER\",\"is_book_form_web_view\":0,\"book_form_web_view_url\":\"\",\"book_again_url\":\"\",\"thumb\":\"https:\\/\\/b.zmtcdn.com\\/data\\/res_imagery\\/16774318_RESTAURANT_fc526e8cfdc1cd8242c50298385d325c.JPG?fit=around%7C200%3A200&crop=200%3A200%3B%2A%2C%2A\",\"user_rating\":{\"aggregate_rating\":\"4.4\",\"rating_text\":\"Very Good\",\"rating_color\":\"5BA829\",\"votes\":\"579\"},\"photos_url\":\"https:\\/\\/www.zomato.com\\/new-york-city\\/otto-enoteca-pizzeria-greenwich-village\\/photos?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1#tabtop\",\"menu_url\":\"https:\\/\\/www.zomato.com\\/new-york-city\\/otto-enoteca-pizzeria-greenwich-village\\/menu?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1&openSwipeBox=menu&showMinimal=1#tabtop\",\"featured_image\":\"https:\\/\\/b.zmtcdn.com\\/data\\/res_imagery\\/16774318_RESTAURANT_fc526e8cfdc1cd8242c50298385d325c.JPG\",\"has_online_delivery\":0,\"is_delivering_now\":0,\"include_bogo_offers\":true,\"deeplink\":\"zomato:\\/\\/restaurant\\/16774318\",\"is_table_reservation_supported\":0,\"has_table_booking\":0,\"events_url\":\"https:\\/\\/www.zomato.com\\/new-york-city\\/otto-enoteca-pizzeria-greenwich-village\\/events#tabtop?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1\"}";
		String encodedString = URLEncoder.encode(jsonDataToSave, "UTF-8");
		mockMvc.perform(
				post("/consumeRestaurantDetails").contentType(MediaType.APPLICATION_JSON).content(encodedString))
				.andDo(print()).andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8")).andExpect(status().isOk());
	}

}