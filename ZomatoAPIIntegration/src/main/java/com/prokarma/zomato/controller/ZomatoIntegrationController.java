package com.prokarma.zomato.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.prokarma.zomato.dto.Restaurant;
import com.prokarma.zomato.service.RestaurantService;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;


@RestController
public class ZomatoIntegrationController {

	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private Environment environment;
	
	@RequestMapping(value="/api/restaurantDetails", method = RequestMethod.GET)
	public StringBuffer getZomatoAPI() {
		
		String base_url = environment.getProperty("zomato_api-restaurant_url")+"?res_id="+environment.getProperty("zomato_api_restaurant_id")+"&apikey="+environment.getProperty("zomato_api_key");		
		StringBuffer content = new StringBuffer();
		try {
			URL urlForGetRequest = new URL(base_url);
		    String readLine = null;
		    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
		    conection.setRequestMethod("GET");
		    int responseCode = conection.getResponseCode();
		    if (responseCode == HttpURLConnection.HTTP_OK) {
		        BufferedReader in = new BufferedReader(
		            new InputStreamReader(conection.getInputStream()));
		        StringBuffer response = new StringBuffer();
		        while ((readLine = in .readLine()) != null) {
		            response.append(readLine);
		        } in .close();
		        if(response!=null) {
		        	System.out.println("Restaurant JSON String Result " + response.toString());
		        	content=response;
		        }
		    } else {
		        System.out.println("GET NOT WORKED");
		    }
		    if(content!=null) {		    	
		    	URL urlForPostRequest;
		        HttpURLConnection postConnection = null;  
		        try {
		        	  urlForPostRequest = new URL("http://localhost:8080/consumeRestaurantDetails");
		        	  postConnection = (HttpURLConnection)urlForPostRequest.openConnection();
		        	  postConnection.setRequestMethod("POST");
			          postConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			          postConnection.setRequestProperty("Content-Length", "" + Integer.toString(content.toString().getBytes().length));
			          postConnection.setRequestProperty("Content-Language", "en-US");  
			          postConnection.setUseCaches (false);
			          postConnection.setDoInput(true);
			          postConnection.setDoOutput(true);

			          //Sending request
			          DataOutputStream wr = new DataOutputStream (postConnection.getOutputStream());
			          wr.writeBytes(content.toString());
			          wr.flush ();
			          wr.close ();
	
			          //Getting Response	
			          InputStream is = postConnection.getInputStream();
			          BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			          String line;
			          StringBuffer response = new StringBuffer(); 
			          while((line = rd.readLine()) != null) {
			            response.append(line);
			            response.append('\r');
			          }
			          rd.close();
			          return response;

		        } 
		        catch (Exception e) {

		          e.printStackTrace();
		          return null;

		        } finally {

		          if(postConnection != null) {
		            postConnection.disconnect(); 
		          }
		        }
		        
		    }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return content;
	}
	
	@RequestMapping(value="/consumeRestaurantDetails", method = RequestMethod.POST)
	public String consumeRestaurantDetails(@RequestBody String restaurantJsonData) {
		int restaurantId = 0;
		if(restaurantJsonData!=null) {
			String decodedRestaurantJsonData = null;
			try {
				decodedRestaurantJsonData = java.net.URLDecoder.decode(restaurantJsonData, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(decodedRestaurantJsonData!=null && !decodedRestaurantJsonData.trim().isEmpty()) {
				Restaurant restaurant = new Restaurant();
				JSONObject jsonObj = new JSONObject(decodedRestaurantJsonData.toString());
				restaurant.setId(Integer.parseInt(jsonObj.getString("id")));
				restaurant.setName(jsonObj.getString("name"));
				restaurant.setUrl(jsonObj.getString("url"));
				restaurantId = restaurantService.saveRestaurantData(restaurant);
			}
		}
		if(restaurantId!=0)
			return ""+restaurantId+" Saved Succesfully";
		else
			return ""+restaurantId+" Not Saved!!!";
	}
	
	@RequestMapping(value="/getRestaurantDetailsFromDB", method = RequestMethod.GET)
	public Restaurant getRestaurantDetailsFromDB(@RequestParam("restaurantId") Integer restaurantId) {
		Restaurant restaurant = restaurantService.getRestaurantDataById(restaurantId);
		return restaurant;
	}

}
