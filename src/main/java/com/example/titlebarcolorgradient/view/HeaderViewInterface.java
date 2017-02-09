package com.example.titlebarcolorgradient.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.ListView;

import java.util.List;

public abstract class HeaderViewInterface<T> {

    protected Activity mActivity;
    protected LayoutInflater mInflate;
    protected T mEntity;

    public HeaderViewInterface(Activity activity) {
        this.mActivity = activity;
        mInflate = LayoutInflater.from(activity);
    }

    public boolean fillView(T t, ListView listView) {
        if (t == null) {
            return false;
        }
        if ((t instanceof List) && ((List) t).size() == 0) {
            return false;
        }
        this.mEntity = t;
        getView(t, listView);
        return true;
    }

    protected abstract void getView(T t, ListView listView);

}
