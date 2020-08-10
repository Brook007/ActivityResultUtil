package com.brook.app.android.activityresult;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Brook
 * @time 2018/11/21 18:19
 */
public class ProxyFragment extends Fragment {

    private static final String INTENT = "intent";
    private static final String REQUEST_CODE = "request_code";
    private static final String MODE = "mode";
    private static final String CALLBACK = "callback";
    private static final String RESTORE_KEY = "restore";

    private ActivityResultUtil.Callback mCallback;
    private int mRequestCode;
    private boolean isStandardMode = true;
    private Intent mIntent;
    private String mIntentKey;
    private String mCallbackKey;

    private boolean isDone = false;

    public static ProxyFragment newInstance(Intent intent, int requestCode, boolean standardMode, ActivityResultUtil.Callback callback) {
        ProxyFragment proxyFragment = new ProxyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(INTENT, IntentPool.getInstance().putCallback(intent));
        bundle.putString(CALLBACK, CallbackPool.getInstance().putCallback(callback));
        bundle.putBoolean(MODE, standardMode);
        bundle.putInt(REQUEST_CODE, requestCode);
        proxyFragment.setArguments(bundle);
        return proxyFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        boolean isRestore = false;
        if (arguments != null) {
            mIntentKey = arguments.getString(INTENT);
            mCallbackKey = arguments.getString(CALLBACK);
            isStandardMode = arguments.getBoolean(MODE);
            mRequestCode = arguments.getInt(REQUEST_CODE);

            mIntent = IntentPool.getInstance().getCallback(mIntentKey, false);
            mCallback = CallbackPool.getInstance().getCallback(mCallbackKey, false);


            if (savedInstanceState != null) {
                isRestore = savedInstanceState.getBoolean(RESTORE_KEY);
            }
        }

        if (mIntent != null && !isRestore) {
            startActivityForResult(mIntent, mRequestCode);
        }
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == this.mRequestCode) {
            if (resultCode == Activity.RESULT_OK || !isStandardMode) {
                if (mCallback != null) {
                    mCallback.onActivityResult(requestCode, resultCode, data);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (mCallback != null) {
                    mCallback.onCancel();
                }
            }
            isDone = true;
            if (getActivity() != null) {
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(this);
                fragmentTransaction.commit();
            }
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(RESTORE_KEY, true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isDone) {
            IntentPool.getInstance().remove(mIntentKey);
            CallbackPool.getInstance().remove(mCallbackKey);
            mCallback = null;
            mIntent = null;

            mIntentKey = null;
            mCallbackKey = null;
        }
    }
}
