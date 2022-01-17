package com.example.project1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteActivity extends AppCompatActivity {

    PreferenceManager pref;
    Button back_btn, save_btn;
    EditText title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        pref = new PreferenceManager();
        back_btn = (Button) findViewById(R.id.back_btn);
        save_btn = (Button) findViewById(R.id.save_btn);
        title = (EditText) findViewById(R.id.memo_title_edit);
        content = (EditText) findViewById(R.id.memo_content_edit);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(WriteActivity.this);
                dlg.setTitle("내용이 저장되지 않습니다");
                dlg.setMessage("종료하시겠습니까?");
                dlg.setNegativeButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                dlg.setPositiveButton("아니요",null);
                dlg.show();

            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edit_title = title.getText().toString();
                String edit_content = content.getText().toString();

                String save_form = "{\"title\":\""+edit_title+"\",\"content\":\""+edit_content+"\"}";


                long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String getTime = simpleDate.format(mDate).toString();
                Log.d("WriteActivity","제목 : "+edit_title+ ", 내용 : "+edit_content+", 현재시간 : "+getTime);
                //key값이 겹치지 않도록 현재 시간으로 부여

                pref.setString(getApplication(),getTime,save_form);

                Intent intent = new Intent();
                intent.putExtra("date",getTime);
                intent.putExtra("title",edit_title);
                intent.putExtra("content",edit_content);
                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }
}