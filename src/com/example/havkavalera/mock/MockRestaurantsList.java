package com.example.havkavalera.mock;

import com.example.havkavalera.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class MockRestaurantsList {

    public static List<Restaurant> getRestaurants() {
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        restaurants.add(new Restaurant("0", "Restaurant0", null));
        restaurants.add(new Restaurant("1", "Restaurant1", null));
        restaurants.add(new Restaurant("2", "Restaurant2", null));
        restaurants.add(new Restaurant("3", "Restaurant3", null));
        restaurants.add(new Restaurant("4", "Restaurant4", null));

        return restaurants;
    }
}
