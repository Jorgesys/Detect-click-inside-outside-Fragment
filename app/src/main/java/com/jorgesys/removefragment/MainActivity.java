package com.jorgesys.removefragment;

import android.graphics.Rect;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Fragment currentFragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adding Fragment when application starts!
        currentFragment =  new FragmentTototita();
        //get FragmentManager
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        //Add fragment to container `FrameLayout`.
        ft.add(R.id.frameLayout, currentFragment);
        ft.commit();

    }


    @Override
    public boolean onTouchEvent (MotionEvent event) {
        //Detect Down action.
        if (event.getAction () == MotionEvent.ACTION_DOWN) {

            boolean intersects = false;
            if(currentFragment.isAdded()) {
                Rect r = new Rect(0, 0, 0, 0);
                currentFragment.getView().getHitRect(r);
                //Check if the event position is inside the window rect.
                intersects = r.contains((int) event.getX(), (int) event.getY());
            }

            //If the event is not inside the bounds then close the fragment!!!
            View parentLayout = findViewById(android.R.id.content);
            if (intersects) {
                Snackbar.make(parentLayout, "Pressing INSIDE the Fragment.\nRemoving Fragment", Snackbar.LENGTH_SHORT).show();
                Log.d(TAG, "Pressing INSIDE the Fragment.\nRemoving Fragment!!!");
                FragmentTransaction fragmentTransaction;
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(currentFragment).commit();
                // notify that we consumed this event
                return true;
            }else {
                Log.d(TAG, "Pressing OUTSIDE the Fragment.");
                if(!currentFragment.isAdded()) {
                    Snackbar.make(parentLayout, "Adding Fragment!", Snackbar.LENGTH_SHORT).show();
                    Log.d(TAG, "The Fragment doesn´t exists, adding Fragment.");
                    currentFragment = new FragmentTototita();
                    //get FragmentManager
                    fragmentManager = getSupportFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    //Add fragment to container `FrameLayout`.
                    ft.add(R.id.frameLayout, currentFragment);
                    ft.commit();
                }

            }
        }
        return super.onTouchEvent ( event );
    }

}
