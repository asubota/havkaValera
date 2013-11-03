package com.havkavalera.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.havkavalera.app.R;
import com.havkavalera.app.VolleySingleton;
import com.havkavalera.app.model.MenuItem;

import java.util.List;

public class MenuSelectAdapter extends BaseAdapter {

    private List<MenuItem> menuItems;

    public MenuSelectAdapter(List<MenuItem> menuItems) {
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
        View view = layoutInflater.inflate(R.layout.menu_item_view, parent, false);

        NetworkImageView networkImageView = (NetworkImageView) view.findViewById(R.id.menu_icon);
        TextView menuName = (TextView) view.findViewById(R.id.menu_name);
        TextView menuDescription = (TextView) view.findViewById(R.id.menu_description);
        TextView menuPrice = (TextView) view.findViewById(R.id.menu_price);

        final MenuItem menuItem = getItem(position);
        networkImageView.setImageUrl(menuItem.getImageUrl(), VolleySingleton.getInstance(context).getImageLoader());
        menuName.setText(menuItem.getName());
//        menuDescription.setText(menuItem.getDescription());
        menuPrice.setText(String.valueOf(menuItem.getPrice()) + " " + menuItem.getCurrency());

        final TextView itemsOrdered = (TextView) view.findViewById(R.id.items_ordered);
        itemsOrdered.setText(String.valueOf(menuItem.getOrdered()));

        Button orderItem = (Button) view.findViewById(R.id.order);
        orderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int order = menuItem.getOrdered();
                menuItem.setOrdered(order + 1);
                itemsOrdered.setText(String.valueOf(menuItem.getOrdered()));
            }
        });
        Button cancelOrder = (Button) view.findViewById(R.id.cancel_order);
        cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int order = menuItem.getOrdered();
                if (order > 0) {
                    menuItem.setOrdered(order - 1);
                    itemsOrdered.setText(String.valueOf(menuItem.getOrdered()));
                }

            }
        });
        Button cancelAll = (Button) view.findViewById(R.id.cancel_all);
        cancelAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuItem.setOrdered(0);
                itemsOrdered.setText(String.valueOf(menuItem.getOrdered()));
            }
        });

        return view;
    }
}
