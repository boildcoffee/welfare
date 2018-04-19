package com.boiledcoffee.welfare.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.boildcoffee.welfare.R;
import com.boiledcoffee.welfare.vm.MainVm;

public class MainActivity extends AppCompatActivity {
    MainVm mainVm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainVm.startGetData();
    }
}
