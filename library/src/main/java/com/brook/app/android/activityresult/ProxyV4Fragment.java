package com.brook.app.android.activityresult;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Brook
 * @time 2018/11/21 18:19
 */
@SuppressLint("ValidFragment")
public class ProxyV4Fragment extends Fragment {

    private ActivityResultUtil.Callback callback;
    private int requestCode;
    private boolean standardMode = true;
    private Intent intent;

    public ProxyV4Fragment() {

    }

    public ProxyV4Fragment(Intent intent, int requestCode, boolean standardMode, ActivityResultUtil.Callback callback) {
        this.intent = intent;
        this.requestCode = requestCode;
        this.standardMode = standardMode;
        this.callback = callback;
    }

    public static ProxyV4Fragment newInstance(Intent intent, int requestCode, boolean standardMode, ActivityResultUtil.Callback callback) {
        return new ProxyV4Fragment(intent, requestCode, standardMode, callback);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (intent != null) {
            startActivityForResult(intent, requestCode);
        }
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == this.requestCode) {
            if (resultCode == Activity.RESULT_OK || !standardMode) {
                if (callback != null) {
                    callback.onActivityResult(requestCode, resultCode, data);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (callback != null) {
                    callback.onCancel();
                }
            }
            if (getActivity() != null) {
                FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                fragmentTransaction.remove(this);
                fragmentTransaction.commit();
            }
        }
    }
}
