package com.example.test.finalproject;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;


public class smallgame extends AppCompatActivity implements View.OnClickListener {
    private  int mRow = 3;
    private  int mCol = 3;
    private  int mConn = 3;

    private GridLayout mGridLayout;
    private List<Drawable> mImg;
    private int[][] mBoard;
    private int mPalyer = 0;
    private int mCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smallgame);

        mGridLayout = (GridLayout) findViewById (R.id.gridLayout);



        loadImages();
        newGame();

    }
    private void loadImages() {
        mImg = new ArrayList<Drawable>();
        mImg.add(getResources().getDrawable(R.drawable.not));
        mImg.add(getResources().getDrawable(R.drawable.cross));
    }

    private void newGame() {
        String title = String.format("%dx%d-%d連線遊戲", mRow, mCol, mConn);
        setTitle(title);

        mGridLayout.setColumnCount(mCol);

        mBoard = new int[mRow][mCol];
        for (int r = 0; r < mRow; r++) {
            for (int c = 0; c < mCol; c++) {
                mBoard[r][c] = -1;
            }
        }

        fillButtons();
    }
    private void fillButtons() {
        for (int k = 0; k < mRow * mCol; k++) {
            Button btn = new Button(this);
            btn.setId(k);
            btn.setOnClickListener(this);
            mGridLayout.addView(btn);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        int row = id / mCol;
        int col = id % mCol;

        mBoard[row][col] = mPalyer;

        Button btn = (Button) v;
        btn.setBackgroundDrawable(mImg.get(mPalyer));

        mPalyer = 1 - mPalyer;
        mCount++;


    }
}
