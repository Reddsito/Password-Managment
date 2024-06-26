package com.password_managment.helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ActivityHelper {
    private final Context context;

    public ActivityHelper(Context context) {
        this.context = context;
    }

    public void startNewActivity(Class<?> targetActivity) {
        Intent intent = new Intent(context, targetActivity);
        context.startActivity(intent);
    }

    public void startNewActivityWithExtras(Class<?> targetActivity, Bundle extras) {
        Intent intent = new Intent(context, targetActivity);
        intent.putExtras(extras);
        context.startActivity(intent);
    }

}
