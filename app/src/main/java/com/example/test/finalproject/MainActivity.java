package com.example.test.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_toreaction,btn_togasnum,btn_tosmallgame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_toreaction = (Button) findViewById(R.id.btn_toreaction);
        btn_togasnum = (Button) findViewById(R.id.btn_togasnum);
        btn_tosmallgame = (Button) findViewById(R.id.btn_tosmallgame);

        btn_toreaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it_toreaction = new Intent();
                it_toreaction.setClass(MainActivity.this,Reaction_game.class);
                startActivity(it_toreaction);
            }
        });
        btn_togasnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it_togasnum = new Intent();
                it_togasnum.setClass(MainActivity.this,gasnum.class);
                startActivity(it_togasnum);
            }
        });
        btn_tosmallgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it_tosmallgame = new Intent();
                it_tosmallgame.setClass(MainActivity.this,smallgame.class);
                startActivity(it_tosmallgame);
            }
        });
    }
}
