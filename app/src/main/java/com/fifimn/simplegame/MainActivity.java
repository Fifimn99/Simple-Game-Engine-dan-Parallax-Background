package com.fifimn.simplegame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnActor, btnPara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Simple Game Engine");

        btnActor = findViewById(R.id.btnActor);
        btnPara = findViewById(R.id.btnPara);

        btnActor.setOnClickListener(this);
        btnPara.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnActor:
                startActivity(new Intent(MainActivity.this, ActorActivity.class));
                break;
            case R.id.btnPara:
                startActivity(new Intent(MainActivity.this, ParallaxActivity.class));
                break;
        }
    }
}
