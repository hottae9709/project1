package com.example.project1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {



    private int REQUSET_TEST = 200;
    Button write_btn;
    PreferenceManager pref;
    RecyclerView recyclerView;
    MemoAdapter memoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("메모장");
        pref = new PreferenceManager();

        write_btn = (Button) findViewById(R.id.write_btn);


        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                startActivityForResult(intent, REQUSET_TEST);
            }
        });

        LinearLayoutManager linearLayoutManager;
        recyclerView = findViewById(R.id.memo_rv);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);

        memoAdapter = new MemoAdapter(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(memoAdapter);

        SharedPreferences prefb = getSharedPreferences("memo_contain", MODE_PRIVATE);

        Collection<?> col_val = prefb.getAll().values();
        Iterator<?> it_val = col_val.iterator();
        Collection<?> col_key = prefb.getAll().keySet();
        Iterator<?> it_key = col_key.iterator();

        while(it_val.hasNext() && it_key.hasNext()) {
            String key = (String) it_key.next();
            String value = (String) it_val.next();
            try {
                JSONObject jsonObject = new JSONObject(value);
                String title = (String) jsonObject.getString("title");
                String content = (String) jsonObject.getString("content");
                memoAdapter.addItem(new MemoItem(key, title, content));
            } catch (JSONException e) {
                Log.d("MainActivity","JSONObject : "+e);
            }

            memoAdapter.notifyDataSetChanged();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUSET_TEST) {
            if (resultCode == RESULT_OK) {

                Intent intent = getIntent();
                String get_date = data.getStringExtra("date");
                String get_title = data.getStringExtra("title");
                String get_content = data.getStringExtra("content");

                memoAdapter.addItem(new MemoItem(get_date,get_title,get_content));
                memoAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "작성 되었습니다", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(MainActivity.this,"취소 되었습니다", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dr_menu:
                Intent intent = new Intent(getApplicationContext(), Draw.class);
                startActivity(intent);
            case R.id.finBtn:
                finish();
            }
            return false;
        }
    }
