package com.prokarma.zomato.custom;

import org.junit.Test;

public class RestaurantNotFoundExceptionTest {
	
	@Test
	public void testExceptionHandler() {
		new RestaurantNotFoundException("Test Exception");
	}

}
