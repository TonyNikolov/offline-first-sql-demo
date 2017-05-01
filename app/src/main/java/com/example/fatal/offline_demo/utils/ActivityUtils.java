package com.example.fatal.offline_demo.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by fatal on 4/30/2017.
 */

public class ActivityUtils {
    public static void replaceFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                                  @NonNull Fragment fragment, int frameId) {
        Fragment fragmentToBacktrack  = fragmentManager.findFragmentById(frameId);
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if(fragmentToBacktrack!=null)
            transaction.addToBackStack(fragmentToBacktrack.toString());

        transaction.replace(frameId, fragment);
        transaction.commit();
    }
}