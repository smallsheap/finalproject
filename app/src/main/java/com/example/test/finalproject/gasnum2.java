package com.example.test.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class gasnum2 extends AppCompatActivity implements View.OnClickListener {
    int[] subject = new int[4];
    int enternum,membernum;
    Button btn_return,btn_enter,btn_start;
    EditText edt_ans;
    TextView txt_input,txt_result,txt_num;
    TextView textView2;
    boolean isgametime = false;
    String ansrec="輸入\n",result="結果\n",innum="次數\n";
    String[] name;
    int[] score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gasnum2);

        btn_enter = (Button) findViewById(R.id.btn_enter);
        btn_return = (Button) findViewById(R.id.btn_return);
        btn_start = (Button) findViewById(R.id.btn_start);

        txt_input = (TextView) findViewById(R.id.txt_input);
        txt_num = (TextView) findViewById(R.id.txt_num);
        txt_result = (TextView)findViewById(R.id.txt_result);
        edt_ans = (EditText) findViewById(R.id.edt_ans);

        btn_enter.setOnClickListener(this);
        btn_enter.setEnabled(false);
        btn_return.setOnClickListener(this);
        btn_start.setOnClickListener(this);

        isgametime = false;

        Intent intent = this.getIntent();
        Bundle bundle2 = intent.getExtras();;

        name = bundle2.getStringArray("name");
        score = bundle2.getIntArray("score");
        membernum = bundle2.getInt("membernum");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_enter:
                enter();
                break;
            case R.id.btn_return:
                if(isgametime == false)
                {
                    gamereturn();
                    finish();
                }
                else
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(gasnum2.this);
                    dialog.setTitle("遊戲尚未結束!!!!!!"+"是否要離開");
                    dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            gamereturn();
                            finish();
                        }
                    });
                    dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });
                    dialog.show();
                }
                break;
            case R.id.btn_start:
                if (isgametime == false) {
                    start();
                }
                else
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(gasnum2.this);
                    dialog.setTitle("遊戲尚未結束!!!!!!"+"是否要重新出題");
                    dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            start();
                        }
                    });
                    dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    });
                    dialog.show();
                }
                break;
        }
    }
    public void enter(){

        int i,j,A = 0,B = 0;


        String ans =edt_ans.getText().toString();
        if(ans.length() == 4) {
            ansrec += ans+"\n";
            enternum++;
            innum += String.valueOf(enternum)+"\n";
            for (i = 0;i<4;i++)
            {
                if(String.valueOf(ans.charAt(i)).equals(String.valueOf(subject[i])))  {
                    A++;
                }
                else
                {
                    for (j = 0;j<4;j++)
                    {
                        if(String.valueOf(ans.charAt(i)).equals(String.valueOf(subject[j])))
                        {
                            B++;
                        }
                    }
                }
            }
            if(A == 4)
            {

                if(enternum<score[19])
                    btn_enter.setEnabled(false);
                isgametime = false;
                Toast.makeText(this,"遊戲結束",Toast.LENGTH_SHORT).show();
                if (membernum == 20)
                {
                    if(enternum<score[19])
                    {
                        entertank();
                    }

                }
                else
                {
                    entertank();
                }


            }

            result+=A+"A"+B+"B"+"\n";
            txt_input.setText(ansrec);
            edt_ans.setText("");
            txt_num.setText(innum);
            txt_result.setText(result);

        }
        else if(ans.equals("") )
        {
            Toast.makeText(this,"請輸入數字",Toast.LENGTH_SHORT).show();
        }
        else if(ans.length() != 4){
            Toast.makeText(this,"請輸入4個不重複的數字",Toast.LENGTH_SHORT).show();
        }

    }
    public void gamereturn()
    {
        Intent intent = new Intent();
        Bundle b  = new Bundle();
        b.putStringArray("name",name);
        b.putIntArray("score",score );
        b.putInt("membernum",membernum);
        intent.putExtras(b);
        setResult(101,intent);
    }
    public void start()
    {
        btn_enter.setEnabled(true);
        ansrec = "輸入\n";
        result = "結果\n";
        innum = "次數\n";
        enternum = 0;
        isgametime = true;
        Toast.makeText(this, "遊戲開始", Toast.LENGTH_SHORT).show();
        int i;
        for (i = 0; i < 4; i++) {
            subject[i] = (int) (Math.random() * 10);
        }
        while (subject[0] == subject[1] || subject[0] == subject[2] || subject[0] == subject[3]) {
            subject[0] = (int) (Math.random() * 10);
        }
        while (subject[1] == subject[0] || subject[1] == subject[2] || subject[1] == subject[3]) {
            subject[1] = (int) (Math.random() * 10);
        }
        while (subject[2] == subject[0]||subject[2] == subject[1]||subject[2] == subject[3]) {
            subject[2] = (int) (Math.random() * 10);
        }
        // textView2 = (TextView) findViewById(R.id.textView2);
        //textView2.setText(String.valueOf(subject[0]) + String.valueOf(subject[1]) + String.valueOf(subject[2]) + String.valueOf(subject[3]));
        txt_num.setText("");
        txt_result.setText("");
        txt_input.setText("");

    }
    public void entertank()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(gasnum2.this);
        dialog.setTitle("恭喜你過關!!!!"+"是否登入排行榜");
        dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                AlertDialog.Builder editDialog = new AlertDialog.Builder(gasnum2.this);
                editDialog.setTitle("登入排行榜");
                final EditText editText = new EditText(gasnum2.this);
                editText.setHint("請輸入名稱");
                editDialog.setView(editText);
                editDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        membernum++;
                        if (membernum>20) membernum =20;


                        for (int i = 0; i < membernum ; i++)
                        {
                            if (enternum < score[i]) {
                                for (int j = membernum - 2; j >= i; j--) {

                                    name[j+1] = name[j];
                                    score[j + 1] = score[j];

                                }
                                name[i] = editText.getText().toString();
                                score[i] = enternum;
                                break;
                            }
                            if(membernum<20) {
                                if (i == membernum - 1) {
                                    name[membernum - 1] = editText.getText().toString();
                                    score[membernum - 1] = enternum;
                                }
                            }
                        }
                    }
                });
                editDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                editDialog.show();
            }
        });
        dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        dialog.show();


    }


}
