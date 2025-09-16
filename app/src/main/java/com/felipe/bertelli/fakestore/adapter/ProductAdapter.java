package com.felipe.bertelli.fakestore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.felipe.bertelli.fakestore.R;
import com.felipe.bertelli.fakestore.model.Product;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {
    public ProductAdapter(Context context, List<Product> products){
        super(context, 0, products);
    }


    @Override
    public View getView(int position, View itemProductView, ViewGroup parent){
        Product product = getItem(position);

        if(itemProductView == null){
            itemProductView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_product, parent, false);
        }


        TextView textTitle = itemProductView.findViewById(R.id.textTitle);
        TextView textPrice = itemProductView.findViewById(R.id.textPrice);

        if(product != null) {
            textTitle.setText(product.title);
            textPrice.setText(String.format("R$ %.2f", product.price));
        }


        return itemProductView;
    }

}
