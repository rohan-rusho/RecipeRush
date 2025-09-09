package com.techiety.mushetoRestaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView recipeText;
    private Button generateBtn, copyBtn, shareBtn;

    private final String[][] recipes = {
            {"Spaghetti Carbonara", "Spaghetti, eggs, pancetta, parmesan, black pepper."},
            {"Grilled Chicken Salad", "Chicken, lettuce, cherry tomatoes, olive oil."},
            {"Pancakes", "Flour, milk, eggs, sugar, butter."},
            {"Guacamole", "Avocado, lime, onion, tomato, salt."},
            {"Chocolate Chip Cookies", "Flour, sugar, butter, chocolate chips, eggs."},
            {"Vegetable Stir Fry", "Broccoli, carrots, bell peppers, soy sauce, garlic."},
            {"Tomato Soup", "Tomatoes, onion, garlic, cream, basil."},
            {"Beef Tacos", "Beef, taco shells, lettuce, cheese, salsa."},
            {"Fruit Smoothie", "Banana, strawberries, yogurt, honey, milk."},
            {"Omelette", "Eggs, cheese, bell peppers, onions, salt."}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeText = findViewById(R.id.recipeText);
        generateBtn = findViewById(R.id.generateBtn);
        copyBtn = findViewById(R.id.copyBtn);
        shareBtn = findViewById(R.id.shareBtn);

        generateBtn.setOnClickListener(v -> {
            Random rand = new Random();
            int index = rand.nextInt(recipes.length);
            String title = recipes[index][0];
            String details = recipes[index][1];

            String formatted = title + "\n\nIngredients:\n" + details;

            // Make title bold & orange
            SpannableString spannable = new SpannableString(formatted);
            spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#FF7043")), 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            recipeText.setText(spannable);
        });

        copyBtn.setOnClickListener(v -> {
            String text = recipeText.getText().toString();
            if (text.isEmpty() || text.equals("Press the button to get a recipe!")) {
                Toast.makeText(MainActivity.this, "Nothing to copy", Toast.LENGTH_SHORT).show();
                return;
            }
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Recipe", text);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(MainActivity.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
        });

        shareBtn.setOnClickListener(v -> {
            String text = recipeText.getText().toString();
            if (text.isEmpty() || text.equals("Press the button to get a recipe!")) {
                Toast.makeText(MainActivity.this, "Nothing to share", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, text + "\n\n— RecipeRush");
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });
    }
}
