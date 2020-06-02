package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import static android.view.View.GONE;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView nameTextView = (TextView) findViewById(R.id.name_tv);
        TextView akaTextView = (TextView) findViewById(R.id.also_known_tv);
        TextView descTextView = (TextView) findViewById(R.id.description_tv);
        TextView originTextView = (TextView) findViewById(R.id.origin_tv);
        TextView ingredientsTextView = (TextView) findViewById(R.id.ingredients_tv);

        nameTextView.setText(sandwich.getMainName());

        String descString = "<b>" + getText(R.string.detail_description_label) + "</b> " + sandwich.getDescription();
        descTextView.setText(Html.fromHtml(descString));

        String placeOrigin = sandwich.getPlaceOfOrigin();
        if (placeOrigin == null || placeOrigin.isEmpty()) {
            originTextView.setVisibility(GONE);
        } else {
            String originString = "<b>" + getText(R.string.detail_place_of_origin_label) + "</b> " + placeOrigin;
            originTextView.setText(Html.fromHtml(originString));
        }
        // Aka
        List<String> akaList = sandwich.getAlsoKnownAs();
        if (akaList.isEmpty()) {
            akaTextView.setVisibility(GONE);
        } else {
            String akaString = "<b>" + getText(R.string.detail_also_known_as_label) + "</b> ".concat(listToString(akaList));
            akaTextView.setText(Html.fromHtml(akaString));
        }

        // Ingredients
        List<String> ingredientsList = sandwich.getIngredients();
        if (ingredientsList.isEmpty()) {
            ingredientsTextView.setVisibility(GONE);
        } else {
            String ingredientsString = "<b>" + getText(R.string.detail_ingredients_label) + "</b>".concat(listToString(ingredientsList));
            ingredientsTextView.setText(Html.fromHtml(ingredientsString));
        }
    }

    private String listToString(List<String> itemsList) {

        String itemsString = itemsList.get(0);
        for (int i=1; i<itemsList.size()-1; i++) {
            itemsString = itemsString.concat(", " + itemsList.get(i));
        }
        return itemsString;
    }
}
