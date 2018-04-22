package com.boiledcoffee.welfare.vm;

import android.app.ProgressDialog;
import android.widget.Toast;

import com.boildcoffee.base.BaseActivity;
import com.boildcoffee.welfare.R;

/**
 *
 * Created by jason on 2018/4/22.
 */

public class BaseVm {
    BaseActivity mActivity;
    private ProgressDialog mProgressDialog;
    public final String LOADING_MSG;

    public BaseVm(BaseActivity activity){
        mActivity = activity;
        LOADING_MSG = mActivity.getString(R.string.text_loading);
    }


    public void showProgressDialog(String msg){
        if (mProgressDialog == null){
            mProgressDialog = new ProgressDialog(mActivity);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();
    }

    public void dismissProgressDialog(){
        if (mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }

    public BaseActivity getActivity() {
        return mActivity;
    }


    public void showShortToast(String text){
        Toast.makeText(mActivity,text,Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String text){
        Toast.makeText(mActivity,text,Toast.LENGTH_LONG).show();
    }

}
