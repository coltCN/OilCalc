package com.coltcn.majf.oilcalc;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TabHost;

/**
 * Created by majf on 2014/11/17.
 */
public class DummyTabFactory  implements TabHost.TabContentFactory {
    private final Context mContext;
    public DummyTabFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public View createTabContent(String tag) {
        View view = new View(mContext);
        view.setMinimumHeight(0);
        view.setMinimumWidth(0);
        return view;
    }
}
