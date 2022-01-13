package com.fic.practica1.capa_aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.fic.practica1.R;

public class MainActivity extends AppCompatActivity {

    //variables
    Animation topAnim,bottomAnim;
    ImageView image;
    TextView texto;

    private static int SPLASH_SCREEN=2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Animation
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //relacion
        image=findViewById(R.id.logo);
        texto=findViewById(R.id.textView);

        image.setAnimation(topAnim);
        texto.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this, activity_ingreso.class);
               startActivity(intent);
               finish();
            }
        },SPLASH_SCREEN);


    }

}