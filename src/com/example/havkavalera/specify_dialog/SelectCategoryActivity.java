package com.example.havkavalera.specify_dialog;

import android.app.Activity;
import android.os.Bundle;
import com.example.havkavalera.R;
import com.example.havkavalera.adapters.CategorySelectAdapter;
import com.example.havkavalera.mock.MockCategoryList;
import com.example.havkavalera.model.Category;
import com.markupartist.android.widget.PullToRefreshListView;

import java.util.List;

public class SelectCategoryActivity extends Activity {

    public static final int REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_list_layout);

        PullToRefreshListView listView = (PullToRefreshListView) findViewById(R.id.selected_list);
        List<Category> categoryList = MockCategoryList.getCategories();
        listView.setAdapter(new CategorySelectAdapter(categoryList));
    }
}
