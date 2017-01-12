package com.example.test.finalproject;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Random;

public class Reaction_game extends AppCompatActivity implements View.OnClickListener{

    ImageButton redImage;
    Button btn_reaction;
    long currentTime;
    long passTime;
    long startTime;
    long fast = 1000000;

    private Handler counterHandler = new Handler();
    Random rnd = new Random();

    void initView() {
        redImage = (ImageButton)findViewById(R.id.imageButton);
    }

    protected ImageButton.OnClickListener finish = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            Long totalTime = (passTime - startTime);
            if (fast > totalTime) {
                fast = totalTime;
            }
            double sec = Double.valueOf(totalTime)/10000;
            double lessSec = Double.valueOf(fast)/10000;
            redImage.setVisibility(View.INVISIBLE);
            btn_reaction.setVisibility(View.VISIBLE);
            AlertDialog.Builder ald = new AlertDialog.Builder(Reaction_game.this);
            ald.setTitle("反應時間");
            ald.setMessage("反應時間:" + String.valueOf(sec) + "秒\n" + "目前最快" + String.valueOf(lessSec) + "\n要再一次?");
            ald.setPositiveButton("好", pos);
            ald.setNegativeButton("不要",neg);
            ald.show();
        }
    };
    AlertDialog.OnClickListener pos = new AlertDialog.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            initState();
        }
    };

    AlertDialog.OnClickListener neg = new AlertDialog.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            //System.exit(1);
            finish();
        }
    };

    void initState() {
        redImage.setOnClickListener(finish);
        rnd.setSeed(System.currentTimeMillis());
        passTime = 0;
        startTime = 0;
        currentTime = System.currentTimeMillis();
        counterHandler.postDelayed(fitstOpen, rnd.nextInt(3000));
        counterHandler.removeCallbacks(newTimer);
        counterHandler.postDelayed(newTimer, 1);
        btn_reaction.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaction_game);
        initView();

        btn_reaction  = (Button) findViewById(R.id.btn_reaction) ;


        btn_reaction.setOnClickListener(this);
    }
    private Runnable newTimer = new Runnable() {
        public void run() {
            passTime = passTime + 1;
            counterHandler.post(this);
        }
    };
    private Runnable fitstOpen = new Runnable() {
        public void run() {
            startTime = passTime;
            redImage.setVisibility(View.VISIBLE);
        }
    };

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_reaction)
        {
            initState();
        }
    }
}
