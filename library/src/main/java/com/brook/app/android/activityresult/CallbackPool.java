package com.brook.app.android.activityresult;

import com.brook.app.android.activityresult.ActivityResultUtil.Callback;

class CallbackPool extends MemoryCachePool<Callback> {

    public static CallbackPool getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static CallbackPool INSTANCE = new CallbackPool();
    }
}
