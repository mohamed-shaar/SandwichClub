package com.example.sandwichclub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandwichclub.model.Sandwich;
import com.example.sandwichclub.utils.Jsonutils;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mPlaceOfOriginTextView;
    private TextView mAlsoKnownAsTextView;
    private TextView mIngredientsTextView;
    private TextView mDescriptionTextView;

    private Sandwich mSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView mImageView = findViewById(R.id.image_iv);
        mPlaceOfOriginTextView = findViewById(R.id.origin_tv);
        mAlsoKnownAsTextView = findViewById(R.id.also_known_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = 0;
        if (intent != null) {
            position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        }
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        mSandwich = Jsonutils.parseSandwichJson(json);
        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(mSandwich.getImage())
                .placeholder(R.mipmap.ic_launcher_round)
                .into(mImageView);

        setTitle(mSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        mPlaceOfOriginTextView.setText(mSandwich.getPlaceOfOrigin());
        mDescriptionTextView.setText(mSandwich.getDescription());
        for (int i = 0; i < mSandwich.getAlsoKnownAs().size(); i++) {
            mAlsoKnownAsTextView.append(mSandwich.getAlsoKnownAs().get(i) + "\n");
        }
        for (int i = 0; i < mSandwich.getIngredients().size(); i++) {
            mIngredientsTextView.append(mSandwich.getIngredients().get(i) + "\n");
        }
    }
}
