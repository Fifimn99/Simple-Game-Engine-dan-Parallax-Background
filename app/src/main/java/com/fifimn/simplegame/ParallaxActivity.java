package com.fifimn.simplegame;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;

public class ParallaxActivity extends AppCompatActivity {

    ParallaxView parallaxView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Demo Parallax");

        Display display = getWindowManager().getDefaultDisplay();

        Point resolution = new Point();
        display.getSize(resolution);

        parallaxView = new ParallaxView(this, resolution.x, resolution.y);
        setContentView(parallaxView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        parallaxView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        parallaxView.pause();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
