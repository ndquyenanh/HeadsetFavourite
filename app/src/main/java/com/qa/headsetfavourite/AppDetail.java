package com.qa.headsetfavourite;

import android.graphics.drawable.Drawable;

/**
 * Created by sev_user on 19-May-15.
 */
public class AppDetail {

    String appName;
    String pkg;
    Drawable appIcon;

    public AppDetail(String appName, Drawable appIcon) {
        this.appName = appName;
        this.appIcon = appIcon;
    }

    public AppDetail(String appName, String pkg, Drawable appIcon) {
        this.appName = appName;
        this.pkg = pkg;
        this.appIcon = appIcon;
    }
}
