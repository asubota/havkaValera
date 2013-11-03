package com.havkavalera.app.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Order implements Parcelable {
    private static final String MENU_LIST = "MENU_LIST";

    private ArrayList<MenuItem> orderedMenu = new ArrayList<MenuItem>();
    private Restaurant restaurant;
    private int total = 0;

    public Order(Restaurant restaurant, ArrayList<MenuItem> orderedMenu) {
        this.orderedMenu = orderedMenu;
        this.restaurant = restaurant;

        for (MenuItem menuItem : orderedMenu) {
            if (menuItem.getOrdered() > 0) {
                total += menuItem.getPrice() * menuItem.getOrdered();
            }
        }
    }

    public Order(Parcel source) {
        source.readList(orderedMenu, MenuItem.class.getClassLoader());
        total = source.readInt();
        restaurant = source.readParcelable(Restaurant.class.getClassLoader());
    }

    public List<MenuItem> getOrderedMenu() {
        List<MenuItem> menuItems = new ArrayList<MenuItem>();
        for (int i = 0; i < orderedMenu.size(); i++) {
            if ((orderedMenu.get(i)).getOrdered() > 0) {
                menuItems.add(orderedMenu.get(i));
            }
        }
        return menuItems;
    }

    public int getTotal() {
        return total;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(orderedMenu);
        dest.writeInt(total);
        dest.writeParcelable(restaurant, flags);
    }

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

}
