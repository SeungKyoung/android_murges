package com.example.totproject.common.statics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ChangeView {
    /* ===================== changeActivity ====================== */
    public static void changeActivity(Context context, Class nextClass) {
        Intent intent = new Intent(context, nextClass);
        context.startActivity(intent);



    }

    public static void changeActivity(Context context, Class nextClass, String name, String fromWhere) {
        Intent intent = new Intent(context, nextClass);
        intent.putExtra(name, fromWhere);
        context.startActivity(intent);

    }
    public static void changeActivity(Context context, Class nextClass, int tabCode) {
        Intent intent = new Intent(context, nextClass);
        intent.putExtra("tabCode", tabCode);
        context.startActivity(intent);
    }
    /* ================================================================= */

    /* ========================= 새로고침 ======================== */
/*    public static void Reload(Context context) {
        Intent getIntent = new Intent(context.getIntent());
        context.startActivity(getIntent);
        context.finish();
    }*/
    /* ================================================================= */

    /* ===================== ChangeFragment ====================== */
    public static void viewFrament(int container, Fragment fragment, FragmentManager manager) {
        manager.beginTransaction().replace(container, fragment).commit();
    }
    public static void changeFrament(int container, Fragment fragment, FragmentManager manager) {
        manager.beginTransaction().replace(container, fragment).addToBackStack(null).commit();
    }
    /* ================================================================= */
}
