package com.dierivera.ubp.unidaddos.Helpers;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


/**
 * Created by dierivera on 6/17/17.
 */

public class NavigationHelper {

    public static void removeFragment(FragmentManager pFragmentManager, Fragment pFragment, boolean pAnimated, boolean pRemoveFromStack)
    {
        FragmentTransaction transaction = pFragmentManager.beginTransaction();
        transaction.remove(pFragment).commit();

        if(pRemoveFromStack)
            pFragmentManager.popBackStack();
    }

    public static void replaceFragment(FragmentManager pFragmentManager, Fragment pFragment, boolean pAnimated, boolean pAddToBackStack)
    {
        replaceFragment(pFragmentManager, pFragment, android.R.id.content, pAnimated, pAddToBackStack);
    }

    public static void replaceFragment(FragmentManager pFragmentManager, Fragment pFragment, int pContainerId, boolean pAnimated, boolean pAddToBackStack)
    {
        FragmentTransaction transaction = pFragmentManager.beginTransaction();

        if(pAddToBackStack)
            transaction.replace(pContainerId, pFragment).addToBackStack(null).commit();
        else
            transaction.replace(pContainerId, pFragment).commit();
    }

    public static void addFragment(FragmentManager pFragmentManager, Fragment pFragment, int pContainerId, boolean pAnimated, boolean pAddToBackStack)
    {
        FragmentTransaction transaction = pFragmentManager.beginTransaction();

        if(pAddToBackStack)
            transaction.add(pContainerId, pFragment).addToBackStack(null).commit();
        else
            transaction.add(pContainerId, pFragment).commit();
    }
}