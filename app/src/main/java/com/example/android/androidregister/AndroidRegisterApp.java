package com.example.android.androidregister;

import android.app.Application;

/**
 * Created by HP on 07-10-2017.
 */

public class AndroidRegisterApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/" + FontsOverride.FONT_PROXIMA_NOVA);
    }
}
