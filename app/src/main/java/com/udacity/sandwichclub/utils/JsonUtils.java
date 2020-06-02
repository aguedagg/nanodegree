package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        final String SANDWICH_NAME = "name";
        final String SANDWICH_MAIN_NAME = "mainName";
        final String SANDWICH_AKA = "alsoKnownAs";
        final String SANDWICH_ORIGIN = "placeOfOrigin";

        final String SANDWICH_DESCRIPTION = "description";
        final String SANDWICH_IMAGE = "image";
        final String SANDWICH_INGREDIENTS = "ingredients";

        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONObject name = jsonObject.optJSONObject(SANDWICH_NAME);
            if (name != null) {
                String mainName = name.optString(SANDWICH_MAIN_NAME);


                JSONArray akaArray = name.optJSONArray(SANDWICH_AKA);
                List<String> alsoKnownAs = new ArrayList<String>();
                if (akaArray != null) {
                    for(int i=0; i < akaArray.length(); i++) {
                        alsoKnownAs.add(akaArray.optString(i));
                    }
                }
                String placeOfOrigin = jsonObject.optString(SANDWICH_ORIGIN);
                String description = jsonObject.optString(SANDWICH_DESCRIPTION);
                String image = jsonObject.optString(SANDWICH_IMAGE);

                JSONArray ingredientsArray = jsonObject.optJSONArray(SANDWICH_INGREDIENTS);
                List<String> ingredients = new ArrayList<String>();
                if (ingredientsArray != null) {
                    for (int i = 0; i < ingredientsArray.length(); i++) {
                        ingredients.add(ingredientsArray.optString(i));
                    }
                }
                return new Sandwich (mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
