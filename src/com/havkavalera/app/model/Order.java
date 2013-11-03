package com.havkavalera.app.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<MenuItem> orderedMenu = new ArrayList<MenuItem>();
    private int total = 0;

    public Order(List<MenuItem> orderedMenu) {
        this.orderedMenu = orderedMenu;

        for (MenuItem menuItem : orderedMenu) {
            if (menuItem.getOrdered() > 0) {
                total += menuItem.getPrice() * menuItem.getOrdered();
            }
        }
    }

    public List<MenuItem> getOrderedMenu() {
        List<MenuItem> menuItems = new ArrayList<MenuItem>();
        for (int i = 0; i < orderedMenu.size(); i++) {
            if (orderedMenu.get(i).getOrdered() > 0) {
                menuItems.add(orderedMenu.get(i));
            }
        }
        return menuItems;
    }

    public int getTotal() {
        return total;
    }
}
