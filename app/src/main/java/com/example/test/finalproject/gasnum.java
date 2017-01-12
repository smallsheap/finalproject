package com.example.test.finalproject;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class gasnum extends AppCompatActivity {
    String[] name;
    int[] score;

    TextView textView,txt_name,txt_score,txt_rank;
    SQLiteDatabase dbrw;
    int membernum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gasnum);

        Button btn = (Button) findViewById(R.id.btn);
        Button btn_game = (Button) findViewById(R.id.btn_game);

        MyDBHelper dbHelper = new MyDBHelper(this);
        dbrw = dbHelper.getWritableDatabase();

        score = new int[20];
        name = new String[20];
        updaterank();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accname = "名字\n",accscore = "次數\n",accrank = "名次\n";

                for(int i = 0;i<20;i++)
                {
                    accname += name[i]+"\n";
                    accrank += "第"+(i+1)+"名\n";
                    if (score[i] == 5040)
                        accscore += "---"+"\n";
                    else
                        accscore += score[i]+"次\n";
                }

                LayoutInflater inflater = LayoutInflater.from(gasnum.this);
                View alert_view = inflater.inflate(R.layout.layout_readerboard,null);

                AlertDialog.Builder alert = new AlertDialog.Builder(gasnum.this);
                alert.setTitle("排行榜");
                alert.setView(alert_view);
                txt_name = (TextView) alert_view.findViewById(R.id.txt_name);
                txt_score = (TextView) alert_view.findViewById(R.id.txt_score);
                txt_rank = (TextView) alert_view.findViewById(R.id.txt_rank);
                txt_score.setText(accscore);
                txt_name.setText(accname);
                txt_rank.setText(accrank);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

                alert.show();
            }
        });
        btn_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(gasnum.this,gasnum2.class);
                Bundle bundle = new Bundle();
                bundle.putStringArray("name",name);
                bundle.putIntArray("score",score );
                bundle.putInt("membernum",membernum);
                it.putExtras(bundle);
                run_delete();
                startActivityForResult(it,0);
            }
        });
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        if(requestCode == 0)
        {
            if(resultCode == 101)
            {
                Bundle b = data.getExtras();
                name = b.getStringArray("name");
                score = b.getIntArray("score");
                membernum = b.getInt("membernum");

                runThread();
            }
        }
    }
    public void  updaterank()
    {
        String[] colum = {"title","score"};
        Cursor c;
        c = dbrw.query("myTable",colum,null,null,null,null,null);
        if(c.getCount()>0) {
            c.moveToFirst();
            for (int i = 0; i < 20; i++){
                name[i]= c.getString(0) ;
                score[i]= Integer.parseInt(c.getString(1)) ;
                c.moveToNext();
            }
            membernum = Integer.parseInt(c.getString(1)) ;

        }
        else
        {
            for (int i = 0; i < 20; i++) {
                name[i] = "未登入";
                score[i] = 5040;
            }
            membernum =0;
        }

    }
    private void runThread(){
        new Thread()
        {
            public void run(){
                for(int i = 0;i<20;i++)
                {
                    ContentValues cv = new ContentValues();
                    cv.put("title", name[i]);
                    cv.put("score", score[i]);
                    dbrw.insert("myTable", null, cv);
                }
                ContentValues cv = new ContentValues();
                cv.put("title", "人數");
                cv.put("score",membernum);
                dbrw.insert("myTable", null, cv);


            }

        }.start();
    }
    private void run_delete()
    {
        new Thread()
        {
            public void run()
            {
                for(int i = 0;i<20;i++)
                {
                    dbrw.delete("myTable","title="+"'"+name[i]+"'",null);
                }
                dbrw.delete("myTable","title="+"'"+"人數"+"'",null);
            }
        }.start();
    }
}
