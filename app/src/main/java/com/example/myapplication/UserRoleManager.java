package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class UserRoleManager {

    private static final String PREF_NAME = "app_user_role_prefs";
    private static final String KEY_IS_PROVIDER_MODE = "key_is_provider_mode";

    public static boolean isProviderMode(Context context) {
        if (context == null) return false;
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_IS_PROVIDER_MODE, false);
    }

    public static void setProviderMode(Context context, boolean isProvider) {
        if (context == null) return;
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_IS_PROVIDER_MODE, isProvider).apply();
    }
}