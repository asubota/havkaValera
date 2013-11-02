package com.havkavalera.app.specify_dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import com.havkavalera.app.R;
import com.havkavalera.app.adapters.CategorySelectAdapter;
import com.havkavalera.app.mock.MockCategoryList;
import com.havkavalera.app.model.Category;
import com.markupartist.android.widget.PullToRefreshListView;

import java.util.List;

public class SelectCategoryActivity extends Activity {

    public static final int REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_list_layout);

        ListView listView = (ListView) findViewById(R.id.selected_list);
        List<Category> categoryList = MockCategoryList.getCategories();
        listView.setAdapter(new CategorySelectAdapter(categoryList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.category_selected);
                checkBox.toggle();
            }
        });

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
