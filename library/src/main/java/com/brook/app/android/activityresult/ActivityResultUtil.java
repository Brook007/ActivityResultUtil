package com.brook.app.android.activityresult;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * @author Brook
 * @time 2018/11/21 18:20
 */
public class ActivityResultUtil {

    private FragmentActivity fragmentActivity;
    private Activity activity;
    private int requestCode;
    private boolean standardMode = true;


    private ActivityResultUtil(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    private ActivityResultUtil(Activity activity) {
        this.activity = activity;
    }

    public static ActivityResultUtil with(FragmentActivity fragmentActivity) {
        return new ActivityResultUtil(fragmentActivity);
    }

    public static ActivityResultUtil with(Activity activity) {
        return new ActivityResultUtil(activity);
    }


    public static ActivityResultUtil with(Fragment v4Fragment) {
        return new ActivityResultUtil(v4Fragment.getActivity());
    }

    public static ActivityResultUtil with(android.app.Fragment v4Fragment) {
        return new ActivityResultUtil(v4Fragment.getActivity());
    }

    public ActivityResultUtil requestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public ActivityResultUtil standardMode(boolean standardMode) {
        this.standardMode = standardMode;
        return this;
    }

    public void startActivityForResult(Intent intent, Callback callback) {
        if (fragmentActivity != null) {
            FragmentManager supportFragmentManager = fragmentActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
            fragmentTransaction.replace(android.R.id.content, ProxyV4Fragment.newInstance(intent, requestCode, standardMode, callback), "ProxyV4Fragment" + requestCode);
            fragmentTransaction.commit();
        } else if (activity != null) {
            android.app.FragmentManager fragmentManager = activity.getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(android.R.id.content, ProxyFragment.newInstance(intent, requestCode, standardMode, callback), "ProxyV4Fragment" + requestCode);
            fragmentTransaction.commit();
        }
    }

    public static abstract class Callback {

        public abstract void onActivityResult(int requestCode, int resultCode, Intent data);

        public void onCancel() {
        }
    }
}
