package com.hkapps.android.experiencia;


import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.LinearLayout;


/**
 * Created by Kamalakar on 21-06-2016.
 */
public class SplashScreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);




    }

public void fab(View view){
    LinearLayout lin = (LinearLayout) findViewById(R.id.lin);

// get the center for the clipping circle
    int centerX = lin.getRight();
    int centerY = lin.getBottom();

    int startRadius = 0;
// get the final radius for the clipping circle
    int endRadius = (int) Math
            .hypot(lin.getWidth(), lin.getHeight());

// create the animator for this view (the start radius is zero)
    Animator anim = ViewAnimationUtils.createCircularReveal(lin, centerX, centerY, startRadius, endRadius);

// make the view visible and start the animation
    lin.setVisibility(View.VISIBLE);
    anim.setDuration(500);

    anim.start();
}
}


