package com.example.sandwichclub.utils;

import com.example.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Jsonutils {

    public static final String LOG_TAG = Jsonutils.class.getSimpleName();


    //Json tags
    public static final String NAME = "name";
    public static final String MAIN_NAME = "mainName";
    public static final String ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE_LINK = "image";
    public static final String INGREDIENTS = "ingredients";

    //Error message
    public static final String MISSING_SANDWICH_DETAIL = "Not Listed";

    public static Sandwich parseSandwichJson(String json) {

        if (json.isEmpty()) return null;

        //JSON object
        try {
            JSONObject sandwichObjectRoot = new JSONObject(json);

            // extracting name
            JSONObject nameObject = sandwichObjectRoot.getJSONObject(NAME);
            String mainName = String.valueOf(nameObject.getString(MAIN_NAME));

            //extracting also known as array
            JSONArray akaArrayObject = nameObject.getJSONArray(ALSO_KNOWN_AS);
            List<String> akaArrayList = new ArrayList<>();
            if (akaArrayObject == null){
                akaArrayList.add(MISSING_SANDWICH_DETAIL);
            }
            else{
                for (int i = 0; i < akaArrayObject.length(); i++) {
                    akaArrayList.add(akaArrayObject.getString(i));
                }
            }

            //extracting ingredients
            JSONArray ingredientsObject = sandwichObjectRoot.getJSONArray(INGREDIENTS);
            List<String> ingredientsList = new ArrayList<>();
            if (ingredientsObject == null){
                ingredientsList.add(MISSING_SANDWICH_DETAIL);
            }
            else {
                for (int i = 0; i < ingredientsObject.length(); i++) {
                    ingredientsList.add(ingredientsObject.getString(i));
                }
            }

            //extracting place of origin
            String placeOfOriginString = sandwichObjectRoot.getString(PLACE_OF_ORIGIN);

            //extracting Description
            String description = sandwichObjectRoot.getString(DESCRIPTION);

            //extracting description
            String imageLink = sandwichObjectRoot.getString(IMAGE_LINK);

            return new Sandwich(mainName, akaArrayList, placeOfOriginString, description, imageLink, ingredientsList);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
