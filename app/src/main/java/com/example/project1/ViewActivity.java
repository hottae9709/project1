package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewActivity extends AppCompatActivity {

    PreferenceManager pref;
    TextView view_title, view_content;
    Button back_list_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        pref = new PreferenceManager();
        view_title = findViewById(R.id.view_title);
        view_content = findViewById(R.id.view_content);
        back_list_btn = findViewById(R.id.back_list_btn);

        Intent intent = getIntent();
        String key = intent.getStringExtra("key");

        String value = pref.getString(getApplicationContext(),key);
        try{
            JSONObject jsonObject = new JSONObject(value);
            String content = (String) jsonObject.getString("content");
            String title = (String) jsonObject.getString("title");
            view_title.setText(title);
            view_content.setText(content);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        back_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}