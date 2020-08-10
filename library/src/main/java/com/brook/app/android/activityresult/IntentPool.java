package com.brook.app.android.activityresult;

import android.content.Intent;

class IntentPool extends MemoryCachePool<Intent> {

    public static IntentPool getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static IntentPool INSTANCE = new IntentPool();
    }
}
