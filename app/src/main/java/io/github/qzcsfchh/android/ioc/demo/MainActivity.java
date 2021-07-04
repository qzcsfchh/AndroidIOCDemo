package io.github.qzcsfchh.android.ioc.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import me.hao.annotation.Router;


@Router("hahha")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}