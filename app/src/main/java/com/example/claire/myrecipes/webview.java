package com.example.claire.myrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import dao.RecipeDAO;
import dao.SessionDAO;
import model.Recipe;
import model.User;

public class webview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Intent intent = getIntent();
        final String url = intent.getStringExtra("source");
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl(url);

        Button addToFav = (Button) findViewById(R.id.buttonAddToFav);
        Button buttonCook = (Button) findViewById(R.id.buttonCook);
        Button buttonMod = (Button) findViewById(R.id.buttonModRec);

        final RecipeDAO recipeDAO = new RecipeDAO(getBaseContext());
        SessionDAO sessionDAO = new SessionDAO(getBaseContext());
        final User user = sessionDAO.getUserConnected(getBaseContext());


        buttonCook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeDAO.setOnlineCooked(url, user.getId());
                Intent intentLogIn = new Intent(getBaseContext(), ContinueCooking.class);
                getBaseContext().startActivity(intentLogIn);
            }
        });

        addToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeDAO.setOnlineFavorite(url, user.getId(), true);
                Toast.makeText(getBaseContext(), "Recipe added to favorites", Toast.LENGTH_SHORT).show();
            }
        });

        buttonMod.setVisibility(View.GONE);
    }
}
