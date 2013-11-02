package com.example.havkavalera.mock;

import com.example.havkavalera.model.Category;

import java.util.ArrayList;
import java.util.List;

public class MockCategoryList {

    public static List<Category> getCategory() {
        List<Category> categories = new ArrayList<Category>();
        categories.add(new Category("Name0", null));
        categories.add(new Category("Name1", null));
        categories.add(new Category("Name2", null));

        return categories;
    }
}
