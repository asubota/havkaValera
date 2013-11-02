package com.havkavalera.app.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.havkavalera.app.R;
import com.havkavalera.app.VolleySingleton;
import com.havkavalera.app.model.Category;

import java.util.List;

public class CategorySelectAdapter extends BaseAdapter {

    private List<Category> mCategories;

    public CategorySelectAdapter(List<Category> categoryList) {
        mCategories = categoryList;
    }

    @Override
    public int getCount() {
        return mCategories.size();
    }

    @Override
    public Category getItem(int position) {
        return mCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mCategories.get(position).mId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.select_category_item, parent, false);
        NetworkImageView niv = (NetworkImageView) view.findViewById(R.id.category_icon);
        TextView textView = (TextView) view.findViewById(R.id.category_text);

        Category category = getItem(position);
        niv.setImageUrl(category.mImageUrl, VolleySingleton.getInstance(context).getImageLoader());
        textView.setText(category.mName);

        return view;
    }
}
