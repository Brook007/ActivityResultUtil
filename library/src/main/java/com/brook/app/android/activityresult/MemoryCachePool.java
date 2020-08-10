package com.brook.app.android.activityresult;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MemoryCachePool<T> {

    private Map<String, T> allCallback;
    private long currentKey = Long.MAX_VALUE;

    public MemoryCachePool() {
        allCallback = new HashMap<>();
    }

    @Nullable
    public T getCallback(String token) {
        return getCallback(token, true);
    }

    @Nullable
    public T getCallback(String token, boolean autoRemove) {
        T callback = allCallback.get(token);
        if (callback != null && autoRemove) {
            allCallback.remove(token);
        }
        return callback;
    }

    public void remove(String key) {
        allCallback.remove(key);
    }

    @NonNull
    public synchronized String putCallback(@NonNull T callback) {
        Map.Entry<String, T> callbackEntry = getEntryByValue(callback);
        if (callbackEntry == null) {
            String uniqueKey = generateUniqueKey();
            allCallback.put(uniqueKey, callback);
            return uniqueKey;
        }
        return callbackEntry.getKey();
    }

    private String generateUniqueKey() {
        String key;
        do {
            if (currentKey <= 0) {
                currentKey = Long.MAX_VALUE;
            }
            key = String.valueOf(currentKey--);
        } while (allCallback.containsKey(key));
        return key;
    }

    @Nullable
    private Map.Entry<String, T> getEntryByValue(T callback) {
        Set<Map.Entry<String, T>> entries = allCallback.entrySet();
        Iterator<Map.Entry<String, T>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, T> next = iterator.next();
            if (next.getValue() == callback) {
                return next;
            }
        }
        return null;
    }


}
