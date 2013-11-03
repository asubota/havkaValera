package com.havkavalera.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.havkavalera.app.R;
import com.havkavalera.app.VolleySingleton;
import com.havkavalera.app.model.MenuItem;

import java.util.List;

public class OrderedMenuAdapter extends BaseAdapter {

    private List<MenuItem> menuItems;

    public OrderedMenuAdapter(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public MenuItem getItem(int position) {
        return menuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.summary_menu_item, parent, false);

        NetworkImageView networkImageView = (NetworkImageView) view.findViewById(R.id.menu_icon);
        TextView menuTitle = (TextView) view.findViewById(R.id.menu_title);
        TextView price = (TextView) view.findViewById(R.id.price);
        TextView count = (TextView) view.findViewById(R.id.count);

        MenuItem menuItem = getItem(position);

        networkImageView.setImageUrl(menuItem.getImageUrl(), VolleySingleton.getInstance(context).getImageLoader());
        menuTitle.setText(menuItem.getName());
        price.setText(String.valueOf(menuItem.getPrice()) + " " + menuItem.getCurrency());
        count.setText(String.valueOf(menuItem.getOrdered()));

        return view;
    }
}
