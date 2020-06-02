package com.udacity.sandwichclub.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class SandwichArrayAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] sandwiches;

    public SandwichArrayAdapter(Activity context, String[] sandwiches) {
        super(context, R.layout.item_sandwich, sandwiches);
        this.context=context;
        this.sandwiches = sandwiches;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.item_sandwich, null,true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.item_thumbnail_iv);
        TextView titleText = (TextView) rowView.findViewById(R.id.item_title_tv);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.item_origin_tv);

        Sandwich sandwich = JsonUtils.parseSandwichJson(sandwiches[position]);
        final Transformation transformation = new CropCircleTransformation();

        Picasso.with(context)
                .load(sandwich.getImage())
                .transform(transformation)
                .into(imageView);

        titleText.setText(sandwich.getMainName());
        subtitleText.setText(sandwich.getPlaceOfOrigin());

        return rowView;
    };
}
