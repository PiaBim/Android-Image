package com.example.a0806;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PingPong_Main extends AppCompatActivity {
    MySurfaceView view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view= new MySurfaceView(this);
        setContentView(view);
    }
    @Override
    protected void onPause(){
        super.onPause();
    }
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }
}
