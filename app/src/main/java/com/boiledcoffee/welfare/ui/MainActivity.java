package com.boiledcoffee.welfare.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.boildcoffee.base.BaseActivity;
import com.boildcoffee.welfare.R;
import com.boildcoffee.welfare.databinding.ActivityMainBinding;
import com.boiledcoffee.welfare.vm.MainVm;

public class MainActivity extends BaseActivity {
    MainVm mainVm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainVm = new MainVm(this);
        mainVm.startGetData(types -> binding.setMainVm(mainVm));
    }
}
