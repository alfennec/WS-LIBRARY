package com.fennec.e_media.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fennec.e_media.R;
import com.fennec.e_media.entity.user;
import com.fennec.e_media.repository.userRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdminActivity extends AppCompatActivity {

    public Button bt_membre, bt_media, bt_emprunt;

    public static AdminActivity main;

    public static String MY_PREFS_NAME = "first_log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        main = this;

        FloatingActionButton fab = findViewById(R.id.fab3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                quitter();
                onDestroy();
                /*Snackbar.make(view, "Developer par : KHLOUF WALID", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        bt_membre = (Button) findViewById(R.id.bt_membre);
        bt_media= (Button) findViewById(R.id.bt_media);
        bt_emprunt= (Button) findViewById(R.id.bt_emprunt);

        bt_membre.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(main, MembreActivity.class);
                main.startActivity(intent);
            }
        });

        bt_media.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(main, MediaActivity.class);
                main.startActivity(intent);
            }
        });

        bt_emprunt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(main, EmpruntActivity.class);
                main.startActivity(intent);
            }
        });
    }

    public static void quitter()
    {
        //clear var client
        userRepository.main_user = new user();

        SharedPreferences prefs = main.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit= prefs.edit();

        edit.putInt("id", 0);
        edit.putString("nom", "vide");
        edit.putString("prenom", "vide");
        edit.putString("email", "vide");
        edit.putString("tel", "vide");
        edit.putInt("type", 0);

        edit.commit();
        main.finish();
        System.exit(0);
    }

    @Override
    protected void onDestroy()
    {
        quitter();
        super.onDestroy();
    }
}
