package com.example.test2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {

    EditText et_p1name;
    EditText et_p2name;
    String p1name,p2name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_p1name = findViewById(R.id.et_p1name);
        et_p2name = findViewById(R.id.et_p2name);

        p1name=et_p1name.getText().toString();
        p2name=et_p2name.getText().toString();

    }

    public void play(View view) {
        p1name=et_p1name.getText().toString();
        p2name=et_p2name.getText().toString();
        Intent myintent = new Intent(this, game.class);
        myintent.putExtra("p1name",p1name);
        myintent.putExtra("p2name",p2name);
        startActivity(myintent);
    }
    public void bexit(View view) {
        et_p1name.setText("");
        et_p2name.setText("");
        finishAffinity();
    }
}
